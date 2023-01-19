package com.secret.model.vo;

import com.secret.constant.RS;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * 统一响应体
 *
 * @author Myles Yang
 */
@Data
@ApiModel("统一响应结果")
public class R<T> implements Serializable {

	private static final long serialVersionUID = -637415113996231595L;

	@ApiModelProperty("响应状态值")
	private int code;

	@ApiModelProperty("响应消息")
	private String message;

	@ApiModelProperty("域名前缀")
	private String baseUrl;

	@ApiModelProperty("响应数据")
	private T data;

	public static String redirect_url;

	public R (int code, String message, T data) {
		this.baseUrl=R.redirect_url;
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public R() {
		this.baseUrl=R.redirect_url;
	}

	public R(int status, String message) {
		this(status, message, null);
	}

	public static R fail(RS rs) {
		R r=new R();
		r.message= rs.message();
		r.code= rs.status();
		return r;
	}
	public static R fail() {
		R r=new R();
		r.message=RS.SYSTEM_ERROR.message();
		r.code= RS.SYSTEM_ERROR.status();
		return r;
	}
	public static R fail(int code ,String message) {
		R r=new R();
		r.message= message;
		r.code=code;
		return r;
	}
	public static R success(Object data){
		return success(data,RS.SUCCESS);
	}
	public static R success(Object data,RS rs){
		R r=new R();
		r.data=data;
		r.message=rs.message();
		r.code=rs.status();
		return r;
	}
	public static  R success(){
		R r=new R();
		r.message= RS.SUCCESS.message();
		r.code=RS.SUCCESS.status();
		return r;
	}

	@Configuration
	public static class LifeCallInit implements InitializingBean {
		@Value("${spring.redirect_uri}")
		private String redirect_uri;
		@Override
		public void afterPropertiesSet() throws Exception {
			redirect_url=redirect_uri;
			System.out.println("执行方法：afterPropertiesSet:"+redirect_uri);
		}
	}
}
