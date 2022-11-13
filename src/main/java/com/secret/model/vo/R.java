package com.secret.model.vo;

import com.secret.constant.RS;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

	@ApiModelProperty("响应数据")
	private T data;


	public R (int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
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
	public R() {
	}

	public static R success(Object data){
		R r=new R();
		r.data=data;
		r.message= RS.SUCCESS.message();
		r.code=RS.SUCCESS.status();
		return r;
	}
	public static  R success(){
		R r=new R();
		r.message= RS.SUCCESS.message();
		r.code=RS.SUCCESS.status();
		return r;
	}
}
