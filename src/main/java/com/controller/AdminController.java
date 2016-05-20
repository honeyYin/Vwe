package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.service.ChannelService;
import com.service.PaperService;

@Controller
@RequestMapping("/admin/")
public class AdminController extends BaseController{
private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private PaperService paperService;
	
	@Autowired
	private ChannelService cateService;
	
	/**
	 * 后台首页
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="index")
	public String listPeople(Model model) {
		return "admin/index";
		
	}
	@RequestMapping(method=RequestMethod.GET,value="top")
	public String top(){
		return "admin/top";
	}
	@RequestMapping(method=RequestMethod.GET,value="main")
	public String main(){
		return "admin/main";
	}
	@RequestMapping(method=RequestMethod.GET,value="left")
	public String left(){
		return "admin/left";
	}
	@RequestMapping(method=RequestMethod.GET,value="right")
	public String right(){
		return "admin/right";
	}
}
