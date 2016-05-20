package com.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.UserDao;
import com.entity.User;
import com.util.RequestUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user/")
public class UserController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDao userDao;
	
	@RequestMapping(method=RequestMethod.GET,value="list")
	public ModelAndView listPeople(HttpServletRequest request) {
		logger.debug("Received request to list persons");
		ModelAndView mav = new ModelAndView();
		List<User> users = userDao.getAllUser();
		logger.debug("Person Listing count = "+users.size());
		mav.addObject("users",users);
		mav.addObject("userId", getUserId(request));
		mav.setViewName("/admin/user/userList");
		return mav;
		
	}
	@RequestMapping(method=RequestMethod.GET,value="toAdd") 
	public ModelAndView toAdd(Long channelId,
			 				 Integer pageNo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/user/addUser");
		return mav;
		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="add") 
	public String save(HttpServletRequest request) {
		User user =  paserUser(null,request);
		userDao.save(user);
		return "redirect:/user/list";
	}
	
	@RequestMapping(method=RequestMethod.GET,value="toEdit") 
	public ModelAndView toEdit(@RequestParam("userId") Long userId) {
		ModelAndView mav = new ModelAndView();
		User user = userDao.find(userId);
		mav.addObject("user",user);
		mav.setViewName("admin/user/updateUser");
		return mav;
		
	}
	@RequestMapping(method=RequestMethod.POST,value="edit")
	public String edit(HttpServletRequest request,
					   @RequestParam(value="userId") Long userId) {		
		User user =  paserUser(userId,request);
		userDao.save(user);
		return "redirect:/user/list";
		
	}
	@RequestMapping(method=RequestMethod.GET,value="toResetPwd") 
	public ModelAndView toResetPwd(@RequestParam(value="userId") Long userId) {
		ModelAndView mav = new ModelAndView();
		User user = userDao.find(userId);
		mav.addObject("loginName", user.getLoginName());
		mav.addObject("userId", userId);
		mav.setViewName("admin/user/resetPwd");
		return mav;
	}
	@RequestMapping(method=RequestMethod.POST,value="checkPwd") 
	public String checkPwd(@RequestParam(value="userId") Long userId,
							 @RequestParam(value="oldPwd") String oldPwd,
							 HttpServletResponse response) {
		User user = userDao.find(userId);
		if(user == null){
			return "userId illegal";
		}
		if(!oldPwd.equals(user.getPassword())){
			return render("false","text/html",response);
		}else {
			return render("true","text/html",response);
		}
	}
	@RequestMapping(method=RequestMethod.POST,value="resetPwd") 
	public String resetPwd(@RequestParam(value="newPwd") String newPwd,
							 @RequestParam(value="userId") Long userId) {
		User user = userDao.find(userId);
		if(user == null){
			return "userId illegal";
		}
		userDao.updatePwd(userId,newPwd);
		return "redirect:/user/list";
	}
	@RequestMapping(method=RequestMethod.GET,value="delete") 
	public String delete(@RequestParam("userId") Long userId) {
		userDao.updateDisabled(userId,1);
		return "redirect:/user/list";
	}
	@RequestMapping(method=RequestMethod.POST,value="checkLoginName") 
	public String checkUserName(@RequestParam("loginName") String loginName,
								Long userId,
								HttpServletResponse response) {
		boolean ex = false;
		try {
			List<User> users = userDao.findByLoginName(loginName);
			if(users != null && users.size()>0 ){
				if(users.get(0).getId() == userId){
					ex = false;
				}else{
					ex= true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(ex){
			return render("true","text/html",response);
		}else {
			return render("false","text/html",response);
		}
	}
	@RequestMapping(method=RequestMethod.GET,value="active") 
	public String active(@RequestParam("userId") Long userId) {
		userDao.updateDisabled(userId,0);
		return "redirect:/user/list";
	}
	@RequestMapping(method=RequestMethod.GET,value="left")
	public String left(Model model,HttpServletRequest request){
		//查询是否为管理员
		
		model.addAttribute("isAdmin",getUser(request).getIsAdmin());
		model.addAttribute("userId", getUserId(request));
		return "admin/user/left";
	}
	@RequestMapping(method=RequestMethod.GET,value="main")
	public String main(Model model,HttpServletRequest request){
		model.addAttribute("isAdmin",getUser(request).getIsAdmin());
		model.addAttribute("userId", getUserId(request));
		return "admin/user/main";
	}
	private User paserUser(Long userId, HttpServletRequest request) {
		User user;
		if(userId != null){
			user = userDao.find(userId);
			user.setEmail(RequestUtil.stringvalue(request, "email"));
			user.setIsAdmin(RequestUtil.intvalue(request, "isAdmin"));
			user.setLoginName(RequestUtil.stringvalue(request, "loginName"));
			user.setRealName(RequestUtil.stringvalue(request, "realName"));
		}else{
			user = new User();
			user.setCreateTime(new Date());
			user.setDisabled(0);
			user.setEmail(RequestUtil.stringvalue(request, "email"));
			user.setIsAdmin(RequestUtil.intvalue(request, "isAdmin"));
			user.setLoginName(RequestUtil.stringvalue(request, "loginName"));
			user.setRealName(RequestUtil.stringvalue(request, "realName"));
			user.setPassword(RequestUtil.stringvalue(request, "password"));
		}
		
		return user;
	}
	
}
