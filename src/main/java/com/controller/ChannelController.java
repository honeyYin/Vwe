package com.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.ChannelDao;
import com.entity.Channel;
import com.model.ChannelModel;
import com.service.ChannelService;
import com.util.RequestUtil;

@Controller
@RequestMapping("/channel/")
public class ChannelController extends BaseController{

	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ChannelDao channelDao;
	
	private String toJson;
	
	private static final Logger logger = LoggerFactory.getLogger(ChannelController.class);
	
	@RequestMapping(method=RequestMethod.GET,value="index")
	public String queryAllChannel(Model model){
		List<Channel> cates = channelService.getAllCategories();
		model.addAttribute("channels", cates);
		return "admin/channel/show";
	}
	@RequestMapping(method=RequestMethod.GET,value="queryForTree")
	public String queryForTree(Model model){
		try {
			ChannelModel temp = channelService.queryByRoot();
			if(null == temp){
				this.toJson = "[]";
			}else {
				this.toJson = "[" + temp.toJson() + "]";
			}
			model.addAttribute("toJson", toJson);
		} catch (Exception e) {
			logger.warn("fail to get channel for tree");
		}
		return "succ";
	}
	
	//去添加页
	@RequestMapping(method=RequestMethod.GET,value="toAdd")
	public String toAdd(Model model){
		return "admin/channel/addchannel";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="add") 
	public String add(HttpServletRequest request) {
		Channel channel =  paserChannel(null,request);
		channelDao.save(channel);
		return "redirect:/channel/index";
		
	}
	@RequestMapping(method=RequestMethod.GET,value="toEdit") 
	public String toEdit(@RequestParam("channelId") long channelId,
						Model model) {
		Channel channel = channelDao.find(channelId);
		model.addAttribute("channel",channel);
		return "admin/channel/modifychannel";
		
	}
	@RequestMapping(method=RequestMethod.POST,value="edit") 
	public String edit(HttpServletRequest request) {
		Long channelId = RequestUtil.longvalue(request, "channelId");
		Channel channel =  paserChannel(channelId,request);
		channelDao.save(channel);
		return "redirect:/channel/index";
		
	}
	@RequestMapping(method=RequestMethod.GET,value="deploy") 
	public String deploy(@RequestParam("channelId") long channelId) {
		channelDao.deploy(channelId);
		return "redirect:/channel/index";
		
	}
	@RequestMapping(method=RequestMethod.GET,value="unDeploy") 
	public String active(@RequestParam("channelId") long channelId) {
		channelDao.unDeploy(channelId);
		return "redirect:/channel/index";
	}
	@RequestMapping(method=RequestMethod.GET,value="delete") 
	public String delete(@RequestParam("channelId") long channelId) {
		channelDao.delete(channelId);
		return "redirect:/channel/index";
		
	}
	@RequestMapping(method=RequestMethod.POST,value="saveOrder") 
	public String saveOrder(HttpServletRequest request) {
		int[] priority = RequestUtil.intArrayValue(request, "priority");
		long[] wids = RequestUtil.longArrayValue(request, "wids");
		
		channelService.updateBatch(wids, priority);
		return "redirect:/channel/index";
	}
	@RequestMapping(method=RequestMethod.GET,value="left")
	public String left(){
		return "admin/channel/left";
	}
	@RequestMapping(method=RequestMethod.GET,value="main")
	public String main(){
		return "admin/channel/main";
	}
	private Channel paserChannel(Long channelId,HttpServletRequest request){
		Channel channel;
		if(channelId != null){
			channel = channelDao.find(channelId);
		}else{
			channel = new Channel();
			channel.setParentId(0l);
			channel.setCreateTime(new Date());
		}
		if(channel == null){
			channel = new Channel();
			channel.setParentId(0l);
			channel.setCreateTime(new Date());
			channel.setDisabled(0);
		}
		channel.setUpdateTime(new Date());
		int pageSize = RequestUtil.intvalue(request, "pageSize");
		channel.setPageSize(pageSize<=0?10:pageSize);//默认大小10条记录每页
		channel.setPriority(RequestUtil.intvalue(request, "priority"));
		channel.setIsDeploy(RequestUtil.intvalue(request, "isDeploy"));
		channel.setName(RequestUtil.stringvalue(request, "name"));
		channel.setDescription(RequestUtil.stringvalue(request, "description"));
		channel.setTitleImg(RequestUtil.stringvalue(request, "titleImg"));
		
		return channel;
	}
}
