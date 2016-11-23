package com.fymod.ftf.util;

import java.io.IOException;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpJson {

	public static String postJson(String url,String jsonParams) throws IOException{
		HttpPost post = new HttpPost(url);
		StringEntity se = new StringEntity(jsonParams, "utf-8");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, Consts.UTF_8.toString()));
	    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
	    post.setEntity(se);
	    DefaultHttpClient client = new DefaultHttpClient();
	    HttpResponse response = client.execute(post);
        byte[] bResultXml = EntityUtils.toByteArray(response.getEntity());
		String content=new String(bResultXml,"utf-8");
		post.abort();
		return content;
	}
	
}
