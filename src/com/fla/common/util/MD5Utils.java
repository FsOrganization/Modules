package com.fla.common.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class MD5Utils {
	
	/**
	 * Md5加密
	 * @param str
	 * @param slat
	 * @return
	 */
	 public static String encodeMd5(String str, String slat) {
	 Md5PasswordEncoder md5 = new Md5PasswordEncoder();
	 return md5.encodePassword(str, slat);
	 }
	 
	 /**
		 * Md5加密
		 * @param str
		 * @param slat
		 * @return
		 */
		 public static String encodeMd5(String str) {
		 Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		 return md5.encodePassword(str,null);
		 }
	 
	
		/**
		 * SHA加密
		 * @param str
		 * @param slat
		 * @return
		 */
	 public static String encodeSHA(String str, String slat) {
	 ShaPasswordEncoder sha = new ShaPasswordEncoder();
	 return sha.encodePassword(str, slat);
	 }
	 
	 public static void main(String args[]) {
		 System.out.println(encodeMd5("123456", "admin"));
	 }
	
}
