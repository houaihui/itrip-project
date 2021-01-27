package com.bdqn.controller;

import cn.itrip.common.*;
import cn.itrip.dao.itripUser.ItripUserMapper;
import cn.itrip.pojo.ItripUser;
import cn.itrip.pojo.ItripUserVO;
import com.alibaba.fastjson.JSONArray;
import cz.mallat.uasparser.UserAgentInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Controller
public class UserController {

    @Resource
    ItripUserMapper dao;

    @Resource
    JredisApi jredisApi;

    @Resource
    SMS_Sent sent;

    @Resource
    Email emails;
    //手机注册 激活
    @RequestMapping(value = "/api/validatephone",method = RequestMethod.PUT,produces = "application/json;charset=utf-8")
    public @ResponseBody Dto Register(String user,String code,HttpServletRequest requst) throws Exception {
        //判断redis中是否有数据                 手机号      激活码
        if(jredisApi.getRedis(user)!=null && jredisApi.getRedis(user).equals(code)){
            //如果有数据那么把刚才插入到数据库中的数据激活
            dao.updatejh(user);
            return DtoUtil.returnDataSuccess("激活成功！");
        }

        return DtoUtil.returnFail("激活失败！","1000");
    }
    //手机注册
    @RequestMapping(value = "/api/registerbyphone",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody Dto Register(@RequestBody ItripUserVO vo, HttpServletRequest requst) throws Exception {

        try{

            //判断 如果有该号码 表明已经注册
            if(dao.getUser(vo.getUserCode())==null){

                //第一步 插入数据库
                ItripUser iu=new ItripUser();
                iu.setUserCode(vo.getUserCode());
                iu.setUserPassword(MD5.getMd5(vo.getUserPassword(),32));
                iu.setUserName(vo.getUserName());
                iu.setActivated(0);

                dao.insertItripUser(iu);

                //第二步 发送验证码
                //随机生成4位验证码
                Random random = new Random();
                //nextInt(n) 返回一个大于等于0小于n的随机数
                int mess=random.nextInt(9999);
                //把手机号和验证码存入到redis中
                jredisApi.SetRedis(vo.getUserCode(),""+mess,120);
                //给手机号发送短信
                sent.SentSms(vo.getUserCode(),""+mess);

                return DtoUtil.returnSuccess("注册成功！");
            }
            else
            {
                return DtoUtil.returnFail("注册失败！该用户已存在！","0");
            }



        }catch (Exception ex){
            //错误码
            return DtoUtil.returnFail("注册失败！","10000");
        }



    }

   //邮箱注册
    @RequestMapping(value = "/api/ckusr",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody Dto Register2(@RequestBody ItripUserVO vo, HttpServletRequest requst) throws Exception {

        try{
            //判断 如果有该号码 表明已经注册
            if(dao.getUser(vo.getUserCode())==null){

                //第一步 插入数据库
                ItripUser iu=new ItripUser();
                iu.setUserCode(vo.getUserCode());
                iu.setUserPassword(MD5.getMd5(vo.getUserPassword(),32));
                iu.setUserName(vo.getUserName());
                iu.setActivated(0);

                dao.insertItripUser(iu);


                 //第二步 发送验证码
                //随机生成4位验证码
                Random random = new Random();
                int mess=random.nextInt(9999);
                //把邮箱号和验证码存入到redis中
                jredisApi.SetRedis(vo.getUserCode(),""+mess,180);
                //给邮箱发送验证码
                String toEMAIL = requst.getParameter(vo.getUserCode());         //对方邮箱
                String TITLE = "来自java的验证码！";       //标题
                String CONTENT ="您的验证码是："+mess+",有效期3分钟！";        //内容
                emails.sendEmail(toEMAIL, TITLE, CONTENT);

                return DtoUtil.returnSuccess("注册成功！");
            }
            else
            {
                return DtoUtil.returnFail("注册失败！该用户已存在！","0");
            }

        }catch (Exception ex){
            //错误码
            return DtoUtil.returnFail("注册失败！","10000");
        }

    }

    //邮箱注册 激活
    @RequestMapping(value = "/api/activate",method = RequestMethod.PUT,produces = "application/json;charset=utf-8")
    public @ResponseBody Dto eamil(String user,String code,HttpServletRequest requst) throws Exception {
        //判断redis中是否有数据                 注册号      激活码
        if(jredisApi.getRedis(user)!=null && jredisApi.getRedis(user).equals(code)){
            //如果有数据那么把刚才插入到数据库中的数据（号码）激活
            dao.updatejh(user);
            return DtoUtil.returnDataSuccess("激活成功！");
        }

        return DtoUtil.returnFail("激活失败！","1000");
    }


    @RequestMapping(value = "/api/dologin",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto Dologin(String name,String password,HttpServletRequest requst) throws Exception {

         ItripUser user =  dao.getlogin(name, MD5.getMd5(password,32)); //32位加密

        //存入redis  中key  value  过期时间
        if(user != null ){
            //获取Agent
            String agent = requst.getHeader("User-Agent");
             //token = md5加密 + userID + userCode + 时间错 （generateToken里）-->key   用户实体类-->value
            String token = generateToken(agent,user);

            //存入redis                     2小时 60*60*2
            jredisApi.SetRedis(token,JSONArray.toJSONString(user),7200);
                                                                        //过期时间                              秒
            ItripTokenVO tokenVO = new ItripTokenVO(token, Calendar.getInstance().getTimeInMillis()+7200,Calendar.getInstance().getTimeInMillis());
            return DtoUtil.returnDataSuccess(tokenVO);
        }                                                  //错误码
        return DtoUtil.returnFail("登录失败！","10000");

    }

    /***
     *
     * @param agent 指的是浏览器上的agent 通过agent来判断是什么方式访问 pc/移动端等
     * @param user
     * @return
     */
    public String generateToken(String agent, ItripUser user) {
        // TODO Auto-generated method stub
        try {
            UserAgentInfo userAgentInfo = UserAgentUtil.getUasParser().parse(
                    agent);
            StringBuilder sb = new StringBuilder();
            sb.append("token:");//统一前缀
            if (userAgentInfo.getDeviceType().equals(UserAgentInfo.UNKNOWN)) {
                if (UserAgentUtil.CheckAgent(agent)) {
                    sb.append("MOBILE-");  //手机端
                } else {
                    sb.append("PC-");      //浏览器端
                }
            } else if (userAgentInfo.getDeviceType()
                    .equals("Personal computer")) {
                sb.append("PC-");
            } else
                sb.append("MOBILE-");
//			sb.append(user.getUserCode() + "-");
            sb.append(MD5.getMd5(user.getUserCode(),32) + "-");//加密用户名称
            sb.append(user.getId() + "-");
            sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                    + "-");
            sb.append(MD5.getMd5(agent, 6));// 识别客户端的简化实现——6位MD5码

            return sb.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
