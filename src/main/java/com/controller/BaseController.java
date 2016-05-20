package com.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.entity.User;

public abstract class BaseController {
	/**
	 * 绕过Template,直接输出内容的简便函数.
	 */
	protected String render(String text, String contentType,HttpServletResponse response) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	protected User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session == null){
        	return null;
        }
        return (User)session.getAttribute("user");
    }

    /**
     * 获得用户id
     *
     * @return
     */
    protected long getUserId(HttpServletRequest request) {
    	if(getUser(request) !=null){
    		return getUser(request).getId();
    	}
       return -1; 
    }
}
