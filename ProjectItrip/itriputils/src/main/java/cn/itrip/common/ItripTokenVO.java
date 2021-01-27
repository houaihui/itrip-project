package cn.itrip.common;



import java.io.Serializable;

/**
 * 返回前端-Token相关VO
 */
public class ItripTokenVO implements Serializable {
	/**
	 * 用户认证凭据
	 */

	private String token;
	/**
	 * 过期时间
	 */

	private long expTime;
	/**
	 * 生成时间
	 */

	private long genTime;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getExpTime() {
		return expTime;
	}
	public void setExpTime(long expTime) {
		this.expTime = expTime;
	}
	public long getGenTime() {
		return genTime;
	}
	public void setGenTime(long genTime) {
		this.genTime = genTime;
	}
	
	public ItripTokenVO() {
		super();
	}
	public ItripTokenVO(String token, long expTime, long genTime) {
		super();
		this.token = token;
		this.expTime = expTime;
		this.genTime = genTime;
	}
	
	
}
