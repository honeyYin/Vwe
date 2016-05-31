package com.util;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
		
		public static String getRandomStringWithNum(int length) { 
		    StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"); 
		    StringBuffer sb = new StringBuffer(); 
		    Random random = new Random(); 
		    int range = buffer.length(); 
		    for (int i = 0; i < length; i ++) { 
		        sb.append(buffer.charAt(random.nextInt(range))); 
		    } 
		    return sb.toString(); 
		}
		public static String encode(String str) {
			if(StringUtils.isEmpty(str)){
				return "";
			}
			return str.replace("\"", "&#34;").replace("'", "&#39;");
		}
}
