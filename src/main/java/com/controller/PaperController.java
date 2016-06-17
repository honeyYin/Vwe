package com.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.constant.OperationTypeEnum;
import com.constant.PaperElementEnum;
import com.dao.ChannelDao;
import com.dao.PaperDao;
import com.dao.PaperOutLinkDao;
import com.dao.PaperParaDao;
import com.dao.PaperSectionDao;
import com.entity.Channel;
import com.entity.Paper;
import com.entity.PaperOutLink;
import com.entity.PaperParagraph;
import com.entity.PaperSection;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.model.OperationResult;
import com.model.PaperModel;
import com.service.ChannelService;
import com.service.PaperService;
import com.util.RequestUtil;

@Controller
@RequestMapping("/paper/")
public class PaperController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(PaperController.class);
	
	private static final int CURRENT_PAGE_NO = 1;
	
	
	@Autowired
	private PaperDao paperDao;
	
	@Autowired
	private PaperParaDao paraDao;
	
	@Autowired
	private PaperSectionDao sectionDao;
	
	@Autowired
	private PaperOutLinkDao outLinkDao;
	
	@Autowired
	private PaperService paperService;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private ChannelService channelService;
	
	/**
	 * 分页获取文章列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="list")
	public String list(Long channelId,
							 Integer pageNo,
							 Integer type,
							 Integer isDraft,
							 Model model,
							 HttpServletRequest request) {
		if(pageNo == null || pageNo <=0){
			pageNo = CURRENT_PAGE_NO;
		}
		String queryTitle = RequestUtil.stringvalue(request, "queryTitle") ;
		if(!StringUtils.isEmpty(queryTitle) || type !=null || isDraft!=null){
			return "redirect:/paper/queryByCondition?pageNo="+pageNo+"&channelId="+channelId+"&queryTitle="+queryTitle+"&type="+type+"&isDraft="+isDraft;
		}
		
		//是否还有下一页，返回总条数
		OperationResult<Long> mrResult = paperService.hasNext(channelId,pageNo,0,false);
		int maxpage = Integer.valueOf(mrResult.getData().toString());
		logger.debug("Received request to list papers");
		
		if(pageNo >maxpage){
			pageNo = maxpage;
		}
		pageNo = pageNo -1;
		List<Paper> papers = paperService.getPapersByPage(channelId,pageNo);
		logger.debug("papers Listing count = "+papers.size());
		OperationResult<Long> result = paperService.hasNext(channelId,pageNo,papers.size(),false);
		List<Channel> channels = channelDao.getRootCategory();
		model.addAttribute("channels",channels);
		model.addAttribute("channelId",channelId==null?0:channelId);
		model.addAttribute("pageNo",pageNo==null?0:pageNo+1);
		model.addAttribute("maxPageNo",result.getData());
		model.addAttribute("hasNext",result.getCode()==0?true:false);
		model.addAttribute("papers",papers);
		return "admin/paper/list";
		
	}
	@RequestMapping(method=RequestMethod.GET,value="queryByCondition")
	public String queryByCondition(Long channelId,
							 Integer pageNo,
							 Integer type,
							 Integer isDraft,
							 HttpServletRequest request,
							 Model model) {
		if(pageNo == null || pageNo <=0){
			pageNo = CURRENT_PAGE_NO;
		}
		String queryTitle = RequestUtil.stringvalue(request, "queryTitle") ;
		if(StringUtils.isEmpty(queryTitle) && type == null && isDraft == null){
			return "redirect:/paper/list?pageNo="+pageNo+"&channelId="+channelId;
		}
		OperationResult<Long> mrResult = paperService.hasNextByCondition(channelId,type,isDraft,queryTitle,pageNo,0);
		int maxpage = Integer.valueOf(mrResult.getData().toString());
		logger.debug("Received request to list papers");
		
		if(pageNo >maxpage){
			pageNo = maxpage;
		}
		pageNo = pageNo -1;
		List<Paper> papers = paperService.getPapersByCondition(channelId,type,isDraft,pageNo,queryTitle);
		logger.debug("papers Listing count = "+papers.size());
		//是否还有下一页，返回总条数
		OperationResult<Long> result = paperService.hasNextByCondition(channelId,type,isDraft,queryTitle,pageNo,papers.size());
		
		List<Channel> channels = channelDao.getRootCategory();
		model.addAttribute("channels",channels);
		model.addAttribute("queryTitle",queryTitle);
		model.addAttribute("type",type==null?-1:type);
		model.addAttribute("isDraft",isDraft==null?0:isDraft);
		model.addAttribute("channelId",channelId==null?0:channelId);
		model.addAttribute("pageNo",pageNo==null?0:pageNo+1);
		model.addAttribute("maxPageNo",result.getData());
		model.addAttribute("hasNext",result.getCode()==0?true:false);
		model.addAttribute("papers",papers);
		return "admin/paper/queryList";
	}
	@RequestMapping(method=RequestMethod.GET,value="detail") 
	public ModelAndView paperDetail(@RequestParam("paperId") Long paperId,
									Long channelId,
									Integer pageNo,
									HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String queryTitle = RequestUtil.stringvalue(request, "queryTitle") ;
		Integer type = RequestUtil.intvalue(request, "type") ;
		Integer isDraft = RequestUtil.intvalue(request, "isDraft");
		mav.addObject("queryTitle",queryTitle);
		mav.addObject("type",type);
		mav.addObject("isDraft",isDraft);
		
		Paper paper = paperDao.find(paperId);
		mav.addObject("paper",paperService.getPaperModel(paper));
		mav.addObject("channelId",channelId);
		mav.addObject("pageNo",pageNo==null?0:pageNo);
		mav.setViewName("admin/paper/paper");
		return mav;
	}
	@RequestMapping(method=RequestMethod.GET,value="toAdd") 
	public ModelAndView toAdd(Long channelId,
			 				 Integer pageNo) {
		ModelAndView mav = new ModelAndView();
		//查询父级栏目
		List<Channel> channels =channelDao.getRootCategory();
				
		mav.addObject("channels",channels);
		mav.addObject("channelId",channelId);
		mav.addObject("pageNo",pageNo==null?0:pageNo);
		mav.setViewName("admin/paper/addPaper");
		return mav;
		
	}
	@RequestMapping(method=RequestMethod.POST,value="add") 
	public String add(HttpServletRequest request,
					  HttpServletResponse response) {
		Paper paper =  paserPaper(null,request);
		String msg = validateAuditPaper(paper);
		if(StringUtils.isEmpty(msg)){
			paper =paperService.savePaper(paper,getUser(request));
			if(paper.getType()!= null && paper.getType() == 0){
				savePaperSection(paper.getId(),request);
			}
		}else{
			return render(msg, "text/html",response);
		}
		
		return render("succ", "text/html",response);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="addCrawlerPaper") 
	public String addCrawlerPaper(HttpServletRequest request,
					   			  HttpServletResponse response) {
		Paper paper =  paserPaper(null,request);
		if(paper != null){
			if(!StringUtils.isEmpty(paper.getUrl()) && paperService.existByUrl(paper.getUrl())){
				return render("exist", "text/html", response);
			}
			paper.setIsDraft(1);//设置为草稿
			paper =paperService.savePaper(paper,getUser(request));
		}
		return render("succ", "text/html", response);
	}
	@RequestMapping(method=RequestMethod.GET,value="toEdit") 
	public ModelAndView toEdit(@RequestParam("paperId") Long paperId,
							   Long channelId,
							   Integer pageNo,
							   HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		//查询父级栏目
		List<Channel> channels =channelDao.getRootCategory();
		Paper paper = paperDao.find(paperId);
		
		String queryTitle = RequestUtil.stringvalue(request, "queryTitle") ;
		mav.addObject("queryTitle",queryTitle);
		mav.addObject("type",paper.getType());
		mav.addObject("isDraft",paper.getIsDraft());
		mav.addObject("channels",channels);
		mav.addObject("paper",paperService.getPaperModel(paper));
		mav.addObject("channelId",channelId);
		mav.addObject("pageNo",pageNo==null?0:pageNo);
		mav.setViewName("admin/paper/editPaper");
		return mav;
		
	}
	@RequestMapping(method=RequestMethod.POST,value="edit") 
	public String edit(HttpServletRequest request,
					  HttpServletResponse response) {
		Long paperId = RequestUtil.longvalue(request,"paperId");
		Paper paper =  paserPaper(paperId,request);
		String msg = validateAuditPaper(paper);
		if(StringUtils.isEmpty(msg)){
			paper = paperService.savePaper(paper,getUser(request));
			if(paper.getType()!= null && paper.getType() == 0){
				savePaperSection(paper.getId(),request);
			}
		}else{
			return render(msg, "text/html",response);
		}
		
		return render("succ", "text/html",response);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="deleteSection") 
	public String deleteSection(@RequestParam("sectionId") Long sectionId,HttpServletResponse response) {
		sectionDao.delete(sectionId);
		return render("succ","text/html",response);		
		
	}
	@RequestMapping(method=RequestMethod.POST,value="deletePara") 
	public String deletePara(@RequestParam("paraId") Long paraId,HttpServletResponse response) {
		paraDao.delete(paraId);
		return render("succ","text/html",response);		
		
	}
	@RequestMapping(method=RequestMethod.POST,value="deleteOutLink") 
	public String deleteOutLink(@RequestParam("outLinkId") Long outLinkId,HttpServletResponse response) {
		outLinkDao.delete(outLinkId);
		return render("succ","text/html",response);		
	}
	@RequestMapping(method=RequestMethod.GET,value="delete") 
	public String delete(@RequestParam("paperId") Long paperId,
						   Long channelId,
						   Integer pageNo,
						   HttpServletRequest request) {
		paperDao.delete(paperId);
		//记录操作信息
		paperService.saveOperationRecord(paperId,OperationTypeEnum.DELETE,getUser(request));
		String queryTitle = RequestUtil.stringvalue(request, "queryTitle") ;
		Integer type = RequestUtil.intvalue(request, "type") ;
		Integer isDraft = RequestUtil.intvalue(request, "isDraft");
		if(!StringUtils.isEmpty(queryTitle) || type !=null || isDraft != null){
			String url= "redirect:/paper/queryByCondition?pageNo="+pageNo+"&channelId="+channelId;
			if(!StringUtils.isEmpty(queryTitle)){
				url += "&queryTitle="+queryTitle;
			}
			if(type !=null){
				url +="&type="+type;
			}
			if(isDraft !=null){
				url +="&isDraft="+isDraft;
			}
			return url;
		}
		return "redirect:/paper/list?pageNo="+pageNo+"&channelId="+channelId;
	}
	@RequestMapping(method=RequestMethod.POST,value="deleteImg") 
	public String deleteImg(@RequestParam("id") Long id,
							@RequestParam("type") String type,
							HttpServletResponse response) {
		int result = 0;
		try{
			switch (PaperElementEnum.valueOfName(type)) {
			case PAPER:
				result = paperDao.deleteImg(id);
				break;
			case PARA:
				result = paraDao.deleteImg(id);
				break;
			default:
				break;
			}
		}catch (Exception e) {
			System.out.println(e);
			return render("fail","text/html",response);	
		}
		if(result<=0){
			return render("fail","text/html",response);	
		}
		return render("succ","text/html",response);	
		
	}
	@RequestMapping(method=RequestMethod.POST,value="batchDelete") 
	public String batchDelete(HttpServletRequest request,
							  Long channelId,
							  Integer pageNo) {
		long[] wids = RequestUtil.longArrayValue(request, "ids");
		if(wids != null && wids.length >0){
			String string = arrToStr(wids);
			if(!StringUtils.isEmpty(string)){
				paperDao.batchDelete(string);
			}
			for(int i=0;i<wids.length;i++){
				//记录操作信息
				paperService.saveOperationRecord(wids[i],OperationTypeEnum.DELETE,getUser(request));
			}
		}
		String queryTitle = RequestUtil.stringvalue(request, "queryTitle") ;
		Integer type = RequestUtil.intvalue(request, "type") ;
		Integer isDraft = RequestUtil.intvalue(request, "isDraft");
		if(!StringUtils.isEmpty(queryTitle) || type !=null || isDraft != null){
			String url= "redirect:/paper/queryByCondition?pageNo="+pageNo+"&channelId="+channelId;
			if(!StringUtils.isEmpty(queryTitle)){
				url += "&queryTitle="+queryTitle;
			}
			if(type !=null){
				url +="&type="+type;
			}
			if(isDraft !=null){
				url +="&isDraft="+isDraft;
			}
			return url;
		}
		return "redirect:/paper/list?pageNo="+pageNo+"&channelId="+channelId;
		
	}
	@RequestMapping(method=RequestMethod.GET,value="updateAudit") 
	public String updateAudit(@RequestParam("paperId") Long paperId,
						      HttpServletRequest request,
						      HttpServletResponse response) {
		Paper paper = paperDao.find(paperId);
		String msg = validateAuditPaper(paper);
		if(StringUtils.isEmpty(msg)){
			paperDao.auditPaper(paperId,true);
			//记录操作信息
			paperService.saveOperationRecord(paperId,OperationTypeEnum.AUDIT,getUser(request));
			return render("succ", "text/html",response);
		}else{
			return render(msg, "text/html",response);
		}
	}
	@RequestMapping(method=RequestMethod.GET,value="cancleAudit") 
	public String cancleAudit(@RequestParam("paperId") Long paperId,
						      HttpServletRequest request,
							    Long channelId,
							    Integer pageNo,
							    String redirect) {
			paperDao.auditPaper(paperId,false);
			//记录操作信息
			paperService.saveOperationRecord(paperId,OperationTypeEnum.AUDIT,getUser(request));
			String queryTitle = RequestUtil.stringvalue(request, "queryTitle") ;
			Integer type = RequestUtil.intvalue(request, "type") ;
			Integer isDraft = RequestUtil.intvalue(request, "isDraft");
			if(StringUtils.isEmpty(redirect)){
				if(!StringUtils.isEmpty(queryTitle) || type !=null || isDraft != null){
					String url= "redirect:/paper/queryByCondition?pageNo="+pageNo+"&channelId="+channelId;
					if(!StringUtils.isEmpty(queryTitle)){
						url += "&queryTitle="+queryTitle;
					}
					if(type !=null){
						url +="&type="+type;
					}
					if(isDraft !=null){
						url +="&isDraft="+isDraft;
					}
					return url;
				}
				return "redirect:/paper/list?pageNo="+pageNo+"&channelId="+channelId;
			}else{
				String redString = "redirect:"+redirect+"?paperId="+paperId+"&pageNo="+pageNo+"&channelId="+channelId;
				if(!StringUtils.isEmpty(queryTitle) || type !=null || isDraft != null){
					if(!StringUtils.isEmpty(queryTitle)){
						redString += "&queryTitle="+queryTitle;
					}
					if(type !=null){
						redString +="&type="+type;
					}
					if(isDraft !=null){
						redString +="&isDraft="+isDraft;
					}
				}
				return redString;
			}
	}
	private String validateAuditPaper(Paper paper){
		if(paper == null){
			return "null";
		}
		if(paper.getIsDraft() == 1){ //草稿
			return "draft";
		}
		if(paper.getType() == 1){ //外链
			if((paper.getChannelId() == null || paper.getChannelId()<=0 ) 
					|| StringUtils.isEmpty(paper.getTitle()) 
					|| StringUtils.isEmpty(paper.getTitleImg())
					|| StringUtils.isEmpty(paper.getUrl())){
				return "illegal";
			}
		}else{
			if((paper.getChannelId() == null || paper.getChannelId()<=0 ) 
					|| StringUtils.isEmpty(paper.getTitle()) 
					|| StringUtils.isEmpty(paper.getTitleImg())){
				return "illegal";
			}
			if(paper.getId() != null){
				List<PaperSection> sections = sectionDao.getSecitonByPaper(paper.getId());
				if(!CollectionUtils.isEmpty(sections)){
					for (PaperSection item:sections) {
						if(StringUtils.isEmpty(item.getTitle())){ //板块标题不能为空
							return "section";						}
					}
				}
			}
		}	
		return "";
	}
	@RequestMapping(method=RequestMethod.POST,value="batchAudit") 
	public String batchAudit(HttpServletRequest request,
							HttpServletResponse response) {
		String ids = RequestUtil.stringvalue(request, "ids");
		if(!StringUtils.isEmpty(ids)){
			List<Long> wids = stringToArr(ids);
			if(!CollectionUtils.isEmpty(wids)){
				for(long id:wids){
					Paper paper = paperDao.find(id);
					String msg = validateAuditPaper(paper);
					if(!StringUtils.isEmpty(msg)){
						return render(msg+"{"+id+"}", "text/html",response);
					}
				}
				paperDao.batchAudit(ids);
				for(long id:wids){
					//记录操作信息
					paperService.saveOperationRecord(id,OperationTypeEnum.AUDIT,getUser(request));
				}
			}
			
		}
		return render("succ", "text/html",response);
		
	}
	@RequestMapping(method=RequestMethod.GET,value="updateTop") 
	public String updateTop(@RequestParam("paperId") Long paperId,
						    @RequestParam("isTop") int isTop,
						    HttpServletRequest request,
						    HttpServletResponse response) {
		if(isTop == 1){
			Long count = paperDao.getTopCount();
			if(count!= null && count>=8){
				return render("size", "text/html",response);
			}
		}
		
		paperDao.updateTop(paperId, isTop);
		//记录操作信息
		paperService.saveOperationRecord(paperId,OperationTypeEnum.TO_TOP,getUser(request));
		return render("succ", "text/html",response);
	}
	@RequestMapping(method=RequestMethod.GET,value="updateRecom") 
	public String updateRecom(@RequestParam("paperId") Long paperId,
						    @RequestParam("isRecom") int isRecom,
						    Long channelId,
						    Integer pageNo,
						    HttpServletRequest request) {
		paperDao.updateRecom(paperId, isRecom);
		//记录操作信息
		paperService.saveOperationRecord(paperId,OperationTypeEnum.TO_RECOM,getUser(request));
		String queryTitle = RequestUtil.stringvalue(request, "queryTitle") ;
		Integer type = RequestUtil.intvalue(request, "type") ;
		Integer isDraft = RequestUtil.intvalue(request, "isDraft");
		if(!StringUtils.isEmpty(queryTitle) || type !=null || isDraft != null){
			String url= "redirect:/paper/queryByCondition?pageNo="+pageNo+"&channelId="+channelId;
			if(!StringUtils.isEmpty(queryTitle)){
				url += "&queryTitle="+queryTitle;
			}
			if(type !=null){
				url +="&type="+type;
			}
			if(isDraft !=null){
				url +="&isDraft="+isDraft;
			}
			return url;
		}
		return "redirect:/paper/list?pageNo="+pageNo+"&channelId="+channelId;
		
	}
	@RequestMapping(method=RequestMethod.GET,value="updatePrior") 
	public String updatePrior(@RequestParam("paperId") Long paperId,
						    @RequestParam("type") Integer type,
						    HttpServletRequest request,
						    HttpServletResponse response) {
		int result = 0;
		//上移
		if(type == 1){
			result = paperService.higherPriority(paperId);
			//记录操作信息
			paperService.saveOperationRecord(paperId,OperationTypeEnum.UP_LEVEL,getUser(request));
		}
		//下移
		if(type == -1){
			result = paperService.lowerPriority(paperId);
			//记录操作信息
			paperService.saveOperationRecord(paperId,OperationTypeEnum.DOWN_LEVEL,getUser(request));
		}
		if(result >0){
			return render("succ","text/html",response);
		}else{
			String msg = "已是该类别第一条记录，无法上移。";
			if(type == -1){
				msg = "已是该类别最后一条记录，无法下移";
			}
			response.setCharacterEncoding("utf-8");
			return render(msg,"text/html",response);	
		}
	}
	@RequestMapping(method=RequestMethod.GET,value="left")
	public ModelAndView left(Model model){
		ModelAndView mav = new ModelAndView();
		List<Channel> channels = channelDao.getRootCategory();
		mav.addObject("channels",channels);
		mav.setViewName("admin/paper/left");
		return mav;
	}
	@RequestMapping(method=RequestMethod.GET,value="main")
	public String main(){
		return "admin/paper/main";
	}
//===================前端===============================================	
	/**
	 *文章预览
	 * @param paperId
	 * @param request
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="viewPaper")
	public ModelAndView fviewPaper(HttpServletRequest request,
									@RequestParam("paperId") Long paperId){
		//修改点击量
		paperService.addViewCount(paperId);
		//向数据库查询对应新闻的内容
		Paper paper = paperDao.find(paperId);
		List<Paper> rePapers = Lists.newArrayList();
		if(paper !=null){
			rePapers = paperDao.fgetRecPaperByCategory(paper.getChannelId(),paper.getId(),5, 0);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("paper", paperService.getPaperModel(paper));
		mav.addObject("rePapers", rePapers);
		mav.setViewName("forecontent");
		return mav;
	}
	/**
	 * 前端查看栏目列表
	 * @param channelId
	 * @param pageNo
	 * @return
	 */
	@Deprecated
	@RequestMapping(method=RequestMethod.GET,value="viewPaperList")
	public ModelAndView fviewPaperList(Long channelId,
							 Integer pageNo) {
		logger.debug("Received request to list papers");
		if(pageNo == null || pageNo <=0){
			pageNo = CURRENT_PAGE_NO;
		}
		pageNo = pageNo -1;
		ModelAndView mav = new ModelAndView();
		List<Paper> papers = paperService.getfPapersByPage(channelId,pageNo);
		logger.debug("papers Listing count = "+papers.size());
		//是否还有下一页，返回总条数
		OperationResult<Long> result = paperService.hasNext(channelId,pageNo,papers.size(),true);
		Channel channel = channelDao.find(channelId);
		
		mav.addObject("channelName",channel.getName());
		mav.addObject("channelId",channelId==null?0:channelId);
		mav.addObject("pageNo",pageNo==null?0:pageNo+1);
		mav.addObject("maxPageNo",result.getData());
		mav.addObject("hasNext",result.getCode()==0?true:false);
		mav.addObject("papers",PaperModel.getPaperModels(papers));
		
		mav.setViewName("forelist");
		return mav;
		
	}
	//=====================接口调用===========================
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="paperTitleImgs")
	public String fpaperTitleImgs(HttpServletRequest request,
								  Model model,
								  HttpServletResponse response){
		Map<String, Object> result = Maps.newHashMap();

		try{
			result.put("status", 0);
			result.put("msg", "succ");
			result.put("papers",paperService.getPaperTitleImgs(request));
		}catch(Exception ex){
			result.put("status", -1);//0标识成功，-1标识失败
			result.put("msg", "fail");
			result.put("papers", Lists.newArrayList());
		}
		return render(JSONObject.fromObject(result).toString(),"application/json; charset=utf-8",response);
	}
	/**
	 * 
	 * @param request
	 * @param paperId
	 * @param channelId
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="paperDetail")
	public String fpaperDetail(@RequestParam("paperId") Long paperId,
							   Model model,
							   HttpServletRequest request,
							   HttpServletResponse response){
		Map<String, Object> result = Maps.newHashMap();

		try{
			//修改点击量
			paperService.addViewCount(paperId);

			//向数据库查询对应新闻的内容
			List<Paper> papers = paperDao.ffind(paperId);
			Paper paper = null;
			List<Paper> recList = Lists.newArrayList();
			if(!CollectionUtils.isEmpty(papers)){
				paper = papers.get(0);
				recList = paperDao.fgetRecPaperByCategory(paper.getChannelId(),paper.getId(),5, 0);
			}
			result.put("status", 0);//0标识成功，-1标识失败
			result.put("msg", "succ");
			result.put("paper", paperService.getPaperModel(paper));
			result.put("recList", paperService.getPaperModels(recList));
		}catch(Exception ex){
			logger.warn("fail to get paper"+ex.getMessage(),ex);
			result.put("status", -1);//0标识成功，-1标识失败
			result.put("msg", "fail");
			result.put("paper", null);
			result.put("recList", Lists.newArrayList());
		}
		return render(JSONObject.fromObject(result).toString(),"application/json; charset=utf-8",response);
	}

	//=====================私有方法====================================
	//从request中获取paper对象
	private Paper paserPaper(Long paperId,HttpServletRequest request) {
		Paper paper;
		if(paperId != null && paperId>0){
			paper = paperDao.find(paperId);
			if(paper == null){
				paper = new Paper();
				paper.setCreateTime(new Date());
				paper.setDisabled(0);
			}
		}else{
			paper = new Paper();
			paper.setCreateTime(new Date());
		}
		Channel channel = channelDao.find(RequestUtil.longvalue(request, "channelId"));
		Integer type = RequestUtil.intvalue(request, "type");
		if(type == null){type=0;}
		
		if(channel != null){
			paper.setIsDraft(0);
			paper.setChannelId(channel.getId());
			paper.setChannelName(channel.getName());
		}else{
			paper.setIsDraft(1);
		}
		paper.setUpdateTime(new Date());
		paper.setTitle(RequestUtil.stringvalue(request, "title"));
		
		
		paper.setType(type);
		if(type==1){
			paper.setUrl(RequestUtil.stringvalue(request, "paperurl"));
			String titleImg = RequestUtil.stringvalue(request, "otitleImg");
			int index = titleImg.indexOf("res");
			if(index != -1){
				paper.setTitleImg(titleImg.substring(index));
			}
		}else{
			String titleImg = RequestUtil.stringvalue(request, "titleImg");
			int index = titleImg.indexOf("res");
			if(index != -1){
				paper.setTitleImg(titleImg.substring(index));
			}
			paper.setAuthor(RequestUtil.stringvalue(request, "author"));
			if(StringUtils.isEmpty(paper.getAuthor())){
				paper.setAuthor(getUser(request).getLoginName());
			}
			paper.setDescription(RequestUtil.stringvalue(request, "description"));
			paper.setIsTop(RequestUtil.intvalue(request, "isTop"));
			paper.setPregStage(RequestUtil.intvalue(request, "pregStage"));
			paper.setRecPregWeeks(RequestUtil.intvalue(request, "recPregWeeks"));
			paper.setHospital(RequestUtil.stringvalue(request, "hospital"));
		}
		return paper;
	}			
	/**
	 * 保存版块信息
	 * @param paperId
	 * @param request
	 */
	private void savePaperSection(Long paperId,HttpServletRequest request){
		int orderNum =1;
		for (int i = 1; i <= 50; i++) {
			Long sectionId = RequestUtil.longvalue(request, "sectionId"+i);
			String sectionTitle = RequestUtil.stringvalue(request, "sectionTitle"+i);
			if(StringUtils.isNotEmpty(sectionTitle)){
				PaperSection section =null;
				if(sectionId != null && sectionId >0){
					section = sectionDao.find(sectionId);
				}
				section = section==null?new PaperSection():section;
				section.setTitle(sectionTitle);
				section.setPaperId(paperId);
				section.setOrderNum(orderNum++);
				section = sectionDao.save(section);
				savePaperPara(paperId,section.getId(),i,request);
				savePaperOutLink(paperId,section.getId(),i,request);
			}
			
		}
	}
	
	/**
	 * 保存小节
	 * @param paperId
	 * @param sectionId
	 * @param request
	 */
	private void savePaperPara(Long paperId,Long sectionId,int orderId,HttpServletRequest request){
		int orderNum =1;
		for(int i=1;i<=40;i++){
			Long paraId = RequestUtil.longvalue(request, "paraId"+orderId+"-"+i);
			String paraTitle = RequestUtil.stringvalue(request, "paraTitle"+orderId+"-"+i);
			PaperParagraph para =null;
			if(paraId != null && paraId >0){
				para = paraDao.find(paraId);
			}
			para = para==null?new PaperParagraph():para;
			para.setCreateTime(new Date());
			para.setUpdateTime(new Date());
			para.setTitle(paraTitle);
			para.setContent(RequestUtil.stringvalue(request, "paraContent"+orderId+"-"+i));

			String titleImg = RequestUtil.stringvalue(request, "paraTitleImg"+orderId+"-"+i);
			int index = titleImg.indexOf("res");
			if(index !=-1){
				para.setImgUrl(titleImg.substring(index));
			}
			if(StringUtils.isEmpty(para.getTitle())
					&& StringUtils.isEmpty(para.getContent())
					&& StringUtils.isEmpty(para.getImgUrl())
					){
				if(para.getId() !=null && para.getId()>0){
					paraDao.delete(para.getId());
				}
				continue;
			}
			para.setPaperId(paperId);
			para.setSectionId(sectionId);
			para.setOrderNum(orderNum++);
			paraDao.save(para);
		}
	}
	/**
	 * 保存区域跳转块
	 * @param paperId
	 * @param sectionId
	 * @param request
	 */
	private void savePaperOutLink(Long paperId,Long sectionId,int orderId,HttpServletRequest request){
		int orderNum =1;
		for(int i=1;i<=40;i++){
			Long outLinkId = RequestUtil.longvalue(request, "outLinkId"+orderId+"-"+i);

			String outTitle = RequestUtil.stringvalue(request, "outTitle"+orderId+"-"+i);
			if(StringUtils.isEmpty(outTitle)){
				continue;
			}
			PaperOutLink link = null;
			if(outLinkId != null && outLinkId >0){
				link = outLinkDao.find(outLinkId); 
			}
			link =(link==null)?new PaperOutLink():link;
			link.setTitle(outTitle);
			link.setSecTitle(RequestUtil.stringvalue(request, "outSecTitle"+orderId+"-"+i));
			link.setPrize(RequestUtil.doublevalue(request, "outPrize"+orderId+"-"+i));
			link.setOuterUrl(RequestUtil.stringvalue(request, "outUrl"+orderId+"-"+i));
			if(StringUtils.isEmpty(link.getTitle())
					&& StringUtils.isEmpty(link.getSecTitle())
					&& StringUtils.isEmpty(link.getOuterUrl())
					&& (link.getPrize() == null || link.getPrize() == 0)){
				if(link.getId()!=null && link.getId()>0){
					outLinkDao.delete(link.getId());
				}
				continue;
			}
			link.setPaperId(paperId);
			link.setSectionId(sectionId);
			link.setOrderNum(orderNum++);
			outLinkDao.save(link);
		}
	}
	private String arrToStr(long[] ids){
		StringBuffer stringBuffer = new StringBuffer();
		if(ids==null ||ids.length<=0){
			return "";
		}
		for (int i = 0; i < ids.length; i++) {
			stringBuffer.append(ids[i]);
			stringBuffer.append(",");
		}
		stringBuffer.deleteCharAt(stringBuffer.length()-1);
		return stringBuffer.toString();
	}
	private List<Long> stringToArr(String ids){
		if(StringUtils.isEmpty(ids)){
			return Lists.newArrayList();
		}
		String[] sidarr = ids.split(",");
		List<Long> idArr = Lists.newArrayList();
		for(String str:sidarr){
			long id = Long.valueOf(str);
			if(id>=0){
				idArr.add(id);
			}
		}
		return idArr;
		
	}
}
