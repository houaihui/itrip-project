package cn.itrip.dao.itripUser;
import cn.itrip.common.Dto;
import cn.itrip.pojo.ItripUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItripUserMapper {

	public ItripUser getUser(@Param(value = "userCode") String userCode);

	public  int updatejh(@Param(value = "phone") String phone);

	public ItripUser getlogin(@Param(value = "name") String name,@Param(value = "ps") String password)throws Exception;

	public ItripUser getItripUserById(@Param(value = "id") Long id)throws Exception;

	public List<ItripUser>	getItripUserListByMap(Map<String, Object> param)throws Exception;

	public Integer getItripUserCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertItripUser(ItripUser itripUser)throws Exception;

	public Integer updateItripUser(ItripUser itripUser)throws Exception;

	public Integer deleteItripUserById(@Param(value = "id") Long id)throws Exception;

}
