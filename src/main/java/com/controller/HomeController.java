package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.UserDao;
import com.entity.User;
import com.util.RequestUtil;

/**
 * Sample controller for going to the home page with a message
 */
@Controller
public class HomeController extends BaseController{

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		return "redirect:login";
	}
	/**
	 * 网站首页：跳转至public首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String test(Model model) {
		return "redirect:login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		
		return "login";
	}
	/**
	 * 登录接口
	 * @param password
	 * @param loginName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="toLogin") 
	public String login(@RequestParam(value="password") String password,
							 @RequestParam(value="loginName") String loginName,
							 HttpServletRequest request,
							 HttpServletResponse response) {
		List<User> users = userDao.findByLoginName(loginName);
		if(users != null && users.size()>0){
			if(password.equals(users.get(0).getPassword())){
				//用户放入session
				RequestUtil.buildSeesion(users.get(0),1,request);
				return render("succ","text/html",response);
				
			}else{
				return render("password","text/html",response);
			}
		}else{
			return render("loginName","text/html",response);
		}
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model) {
		
		return "logout";
	}
}
