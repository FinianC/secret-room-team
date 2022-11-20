package com.secret.constant;

import org.springframework.http.HttpStatus;

/**
 * 自定义状态码
 * <p>
 * 200 成功
 * 1001 - 1999 参数
 * 2001 - 2999 用户
 * 3001 - 3999 权限
 * 4001 - 4999 数据
 * 5001 - 5999 业务
 * 6001 - 6999 接口
 * 7001 - 7999 系统
 */
public enum RS {

	SUCCESS(HttpStatus.OK.value(), "成功"),

	/**
	 * 用户未登录 LOGIN_FAIL
	 */
	LOGIN_FAIL(1002, "用户未登录。"),
	/**
	 * 用户未登录 LOGIN_FAIL
	 */
	TOKEN_NOT_FOUNT(1003, "当前token已过期。"),
	/**
	 * 密码错误，请重新登陆。 PASS_WORD_ERROR
	 */
	PASS_WORD_ERROR(1004, "密码错误，请重新登陆。"),
	/**
	 * 用户名错误，请重新登陆。 PASS_WORD_ERROR
	 */
	USER_NAME_ERROR(1005, "用户名错误，请重新登陆。"),

	UPLOAD_FOUNT(1006, "文件上传错误。"),

	FILE_NOT_FOUNT(1007, "文件未找到。"),

	/**
	 * 系统错误 SYSTEM_ERROR
	 */
	SYSTEM_ERROR(2001, "系统错误。"),

	MENU_NOT_FOUNT(2002,"无权限访问。"),

	NO_PUBLISHING_PERMISSION(2003,"无发布车队权限，有需要可找群主开通。"),

	DO_NOT_JOIN_AGAIN(2004,"请勿重复加入。")
	;

	private final int status;

	private final String message;

	RS(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int status() {
		return status;
	}

	public String message() {
		return message;
	}

	public static RS resolve(int statusCode) {
		for (RS rc : values()) {
			if (rc.status == statusCode) {
				return rc;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "{\"status\":" + status + ",\"message\":\"" + message + "\"}";
	}
}
