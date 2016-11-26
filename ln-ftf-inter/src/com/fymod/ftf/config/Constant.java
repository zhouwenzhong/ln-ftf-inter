package com.fymod.ftf.config;

public class Constant {
	
	//用户信息 session key
	public static String USER_SESSION_KEY = "h5User";
	
	public static String KEY_SMS_TZ = "1"; //通知相关人员的key 参数需要自行对应上
	public static String KEY_SMS_MM = "1"; //找回密码的key 参数需要自行对应上
	/** 短信发送间隔 */
    public final static Long SMS_INTERVAL = 1000 * 90l;
    /** 短信验证码有效时间 */
    public final static Long SMS_CODE_VALID = 1000 * 60 * 5l;

    /** 短信验证码类型 */
    public final static String SMS_CODE_TYPE_REGISTER = "register";
    /** 短信验证码类型 */
    public final static String SMS_CODE_TYPE_FORGET = "forget";
    /** 上传图片文件保存路径 */
    public final static String UPLOAD_IMAGE_PATH = "/usr/local/tomcat8/webapps/ROOT/image/";
//    public final static String UPLOAD_IMAGE_PATH = "/Users/zhouwz/Downloads/";
    /** 下载图片文件的路径 */
    public final static String DOWNLOAD_IMAGE_PATH = "http://call.muxeyes.com:8080/image/";
    
}
