package com.fymod.ftf.util;

import java.util.HashMap;
import java.util.Set;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.fymod.ftf.config.ResultBase;

public class TemplateSMS {

	// mobile用逗号分隔，可一次发送多个
	// modelId模板ID，测试的时候用1
	// params模板中对应的参数
	public static ResultBase send(String mobile, String modelId, String[] params) {
		HashMap<String, Object> result = null;
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
		ResultBase resultBase = new ResultBase();
		
		//生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");
		restAPI.init("app.cloopen.com", "8883");
		
		//******************************注释*********************************************
		//*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
		//*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
		//*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
		//*******************************************************************************
//		restAPI.setAccount("8aaf0708588b1d23015890d7c75404e8", "1631e877f74f43619b5ea93a0fd07031");
		restAPI.setAccount("8a216da8570874940157144979ff07ef", "1631e877f74f43619b5ea93a0fd07031");
		
		//******************************注释*********************************************
		//*初始化应用ID                                                                 *
		//*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
		//*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
		//*******************************************************************************
//		restAPI.setAppId("8a216da857087494015714497b6907f6");
		restAPI.setAppId("8a216da857087494015714497b6907f6");
		
		result = restAPI.sendTemplateSMS(mobile,modelId ,params);
		
		System.out.println("result=" + result);
		
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				System.out.println(key +" = "+object);
			}
			resultBase.setResult(ResultBase.RESULT_SUCC);
		}else{
			//异常返回输出错误码和错误信息
            resultBase.setResult(ResultBase.RESULT_FAIL);
            resultBase.setMessage(result.get("statusMsg").toString());
			System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
		}
		return resultBase;
	}

}
