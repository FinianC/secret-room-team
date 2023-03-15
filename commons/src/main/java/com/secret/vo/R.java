package com.secret.vo;

import com.secret.constant.RS;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * 统一响应体
 *
 * @author Myles Yang
 */
@ApiModel("统一响应结果")
@Slf4j
@Data
public class R<T> implements Serializable {

	private static final long serialVersionUID = 1905122041950251207L;


	@ApiModelProperty("响应状态值")
	private int code;

	@ApiModelProperty("响应消息")
	private String message;

	@ApiModelProperty("域名前缀")
	private String baseUrl;

	@ApiModelProperty("响应数据")
	private T data;

	public static String redirectUrl;

	public R(int code, String message, T data) {
		this.baseUrl=R.redirectUrl;
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public R() {
		this.baseUrl=R.redirectUrl;
	}

	public R(int status, String message) {
		this(status, message, null);
	}

	public static <T> R<T> fail(RS rs) {
		R<T> r=new R<>();
		r.message= rs.message();
		r.code= rs.status();
		return r;
	}
	public static <T> R<T> fail() {
		R<T> r=new R<>();
		r.message=RS.SYSTEM_ERROR.message();
		r.code= RS.SYSTEM_ERROR.status();
		return r;
	}
	public static <T> R<T> fail(int code ,String message) {
		R<T> r=new R<>();
		r.message= message;
		r.code=code;
		return r;
	}
	public static <T> R<T> success(T data){
		return success(data,RS.SUCCESS);
	}
	public static <T> R<T> success(T data,RS rs){
		R<T> r=new R<>();
		r.data=data;
		r.message=rs.message();
		r.code=rs.status();
		return r;
	}
	public static  <T> R<T> success(){
		R<T> r=new R<>();
		r.message= RS.SUCCESS.message();
		r.code=RS.SUCCESS.status();
		return r;
	}

	@Configuration
	public static class LifeCallInit implements InitializingBean {
		@Value("${spring.redirect_uri}")
		private String redirectUri;
		@Override
		public void afterPropertiesSet() {
			redirectUrl=redirectUri;
			log.info("Implementation method：afterPropertiesSet:"+redirectUri);
		}
	}

}
