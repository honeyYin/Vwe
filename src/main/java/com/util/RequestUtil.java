package com.util;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import com.entity.User;


public class RequestUtil {
	public static long longvalue(HttpServletRequest request, String name){
		String temp = request.getParameter(name);
		
		long result = paseLong(temp) ;
		
		return result;
		
	}
	
	public static long paseLong(String value){
		long result = 0 ;
		
		if (StringUtils.isEmpty(value)) {
			return result;
		}
		
		try {
			result = Long.parseLong(value);
		} catch (Exception e) {
		}
		
		return result;
	}
	public static int paseInteger(String value){
		int result = 0 ;
		
		if (StringUtils.isEmpty(value)) {
			return result;
		}
		
		try {
			result = Integer.parseInt(value);
		} catch (Exception e) {
		}
		
		return result;
	}
	public static long[] longArrayValue(HttpServletRequest request, String name){
		String[] ss = request.getParameterValues(name);
		
		if (ss == null || ss.length < 1) {
			return null;
		}
		int length = ss.length;
		long[] result = new long[ss.length];
		
		for (int i = 0; i< length ; i++) {
			
			result[i] = paseLong(ss[i]) ;
		}
		
		return result;
	}
	public static int[] intArrayValue(HttpServletRequest request, String name){
		String[] ss = request.getParameterValues(name);
		
		if (ss == null || ss.length < 1) {
			return null;
		}
		int length = ss.length;
		int[] result = new int[ss.length];
		
		for (int i = 0; i< length ; i++) {
			
			result[i] = paseInteger(ss[i]) ;
		}
		
		return result;
	}
	public static int intvalue(HttpServletRequest request, String name){
		String temp = request.getParameter(name);
		
		int result = 0 ;
		
		if (StringUtils.isEmpty(temp)) {
			return result;
		}
		
		try {
			result = Integer.parseInt(temp);
		} catch (Exception e) {
		}
		
		return result;
		
	}
	
	public static String stringvalue(HttpServletRequest request, String name) {
		try {
			String trString = request.getCharacterEncoding();
			if(StringUtils.isNotEmpty(trString) && trString.equals("UTF-8")){
				return request.getParameter(name);
			}else{
				return new String(request.getParameter(name).getBytes("ISO8859-1"),"UTF-8");
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 public static void buildSeesion(User user,int loginFailFlag,HttpServletRequest req) {
	        HttpSession session = req.getSession();
	        user.setPassword(null);
	        session.setAttribute("user", user);
	        session.setMaxInactiveInterval(20*60);  
	}
}
