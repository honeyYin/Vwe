package com.auth;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import com.entity.User;
import com.google.common.collect.Lists;


//微信菜单会话控制，控制那些直接重微信菜单进来的请求
public class LoanAccessSecurityFilter extends GenericFilterBean {
	
	private RequestMatcher requiresAuthenticationRequestMatcher = null;
	
	private List noFilterURLs = Lists.newArrayList("/login","/toLogin","/logout","/paper/viewPaper","/paper/viewPaperList");
    
	private List noFilterPrefix = Lists.newArrayList("/res");
    
	private List noFilterSuffix = Lists.newArrayList("/left","/top","/right","/main");
    
    public static final int CODE_EXPRIE_TIMEOUT = 7200;
    
	
	public void setNoFilterURLs(List noFilterURLs) {
		this.noFilterURLs = noFilterURLs;
	}

    
    public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String requri = requestURI.substring(contextPath.length());
        HttpSession session = request.getSession();
        if (session != null ) {
        	 User user = (User) session.getAttribute("user");
        	//如果用户已经存在则直接往后走
        	if (user == null || user.getId() <= 0) {
        		session.invalidate(); 
        		if (!noFilterURLs.contains(requri) && !matchPrefix(requri)
        				&& !matchSuffix(requri)) {
        			response.sendRedirect(request.getContextPath() + "/login");   
        	        return ;   	
        		}
        	}
        }else{
        	if(!noFilterURLs.contains(requri) && !matchPrefix(requri)
    				&& !matchSuffix(requri)){
    			response.sendRedirect(request.getContextPath() + "/login");   
    	        return ; 
    		}
        }
        chain.doFilter(req, res);
        return ; 
	}
    private boolean matchPrefix(String requri){
    	for (int i = 0; i < noFilterPrefix.size(); i++) {
			String item = noFilterPrefix.get(i).toString();
			if(requri.startsWith(item)){
				 return true;
			}
		}
    	return false;
    }
    private boolean matchSuffix(String requri){
    	for (int i = 0; i < noFilterSuffix.size(); i++) {
			String item = noFilterSuffix.get(i).toString();
			if(requri.endsWith(item)){
				 return true;
			}
		}
    	return false;
    }
}
