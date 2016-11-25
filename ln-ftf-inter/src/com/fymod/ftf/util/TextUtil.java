package com.fymod.ftf.util;

import java.security.MessageDigest;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author zhouwenzhong
 *
 */
public class TextUtil {

	/**
	 * 判断 ""/null
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isNullOrEmpty(String text) {
		return text == null || text.isEmpty();
	}

	/**
	 * md5 32位
	 * 
	 * @param s
	 * @return
	 */
	public static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * md5 16位
	 * 
	 * @param s
	 * @return
	 */
	public static String MD5bit16(String s) {
		String md5bit32 = MD5(s);
		return md5bit32.substring(8, 24);
	}

	public static String getNonceNum(int count) {
		StringBuffer str = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < count; i++) {
			str.append(random.nextInt(10));
		}
		return str.toString();
	}

	public static String arrayToString(String... strings) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strings.length; i++) {
			sb.append(strings[i]);
			if (i < strings.length - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	public static String arrayToString(List<String> strings) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strings.size(); i++) {
			sb.append(strings.get(i));
			if (i < strings.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 随机字符串
	 * 
	 * @param count
	 *            长度
	 * @return
	 */
	public static String getNonceStr(int count) {
		char[] radixDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
				'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		int length = radixDigits.length;
		StringBuffer nonceStr = new StringBuffer();
		for (int i = 0; i < count; i++) {
			Random random = new Random();
			nonceStr.append(radixDigits[random.nextInt(length)]);
		}
		return nonceStr.toString();
	}

	/**
	 * 字符串是否是整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
