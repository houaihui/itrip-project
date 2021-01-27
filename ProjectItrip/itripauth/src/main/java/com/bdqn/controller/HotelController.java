package com.bdqn.controller;

import cn.itrip.dao.itripHotel.ItripHotelMapper;
import cn.itrip.pojo.ItripHotel;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

@Controller
public class HotelController {
    @Resource
    ItripHotelMapper dao;

    @RequestMapping(value = "/list",method = RequestMethod.GET,produces="application/json;charset=utf-8")
    @ResponseBody
    public ItripHotel getlist() throws Exception {

        Jedis jedis=new Jedis("192.168.7.134");
        jedis.auth("123456");
        jedis.setex("Hou",60*60*2,"houaihui");

        ItripHotel cn=dao.getItripHotelById(new Long(1));


        return cn;
    }
}
