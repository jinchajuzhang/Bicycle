package com.example.juzhang.bicycle.Utils;
import com.example.juzhang.bicycle.ContentValues.ContentValues;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 此类用于加密字符串
 * @author Administrator
 *
 */
public class PSDUtils {
	/**
	 * MD5加密字符串
	 * @param str 字符串
	 * @return MD5加密后的字符串
	 */
	public static String encryption2MD5(String str){
		str = str + ContentValues.MD5PSDAFTER;
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			byte[] bs = digest.digest(str.getBytes());
			StringBuilder sb  = new StringBuilder();
			for(byte b:bs){
				int i = b&0xff;
				String hexString = Integer.toHexString(i);
				if(hexString.length()<2){
					hexString = "0" + hexString;
				}
				sb.append(hexString);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
