package com.fymod.ftf.config;

/**
 * 状态码+信息
 * 
 * @author guochao
 *
 */
public enum ErrorCode {
    SYS_ERROR_CODE_1006(1006, "该手机号已经注册"), //
	SYS_ERROR_CODE_1005(1005, "用户名不存在"), //
	SYS_ERROR_CODE_1004(1004, "该手机号无法注册"), //
	SYS_ERROR_CODE_1003(1003, "登录过期"), //
	SYS_ERROR_CODE_1002(1002, "验证码错误"), //
	SYS_ERROR_CODE_1001(1001, "验证码操作频繁"), //
	SYS_ERROR_CODE_1000(1000, "用户名或密码错误"), //

	SYS_ERROR_CODE_400(400, "不正确的请求"), //
	SYS_ERROR_CODE_404(404, "找不到页面"), //
	SYS_ERROR_CODE_405(405, "服务器错误"), //
	SYS_ERROR_CODE_500(500, "内部服务器错误"), //

	SYS_ERROR_CODE_501(501, "无操作权限"), //
	SYS_ERROR_CODE_502(502, "参数错误"), //

	ERROR_CODE_SUCCESS(200, "ok");//

	public int value;
	public String message;

	ErrorCode(int value, String message) {
		this.value = value;
		this.message = message;
	}

}
