package com.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
	
	/**
	 * 分页获取文章列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="list")
	public ModelAndView list(Long channelId,
							 Integer pageNo) {
		logger.debug("Received request to list papers");
		if(pageNo == null || pageNo <=0){
			pageNo = CURRENT_PAGE_NO;
		}
		pageNo = pageNo -1;
		ModelAndView mav = new ModelAndView();
		List<Paper> papers = paperService.getPapersByPage(channelId,pageNo);
		logger.debug("papers Listing count = "+papers.size());
		//是否还有下一页，返回总条数
		OperationResult<Long> result = paperService.hasNext(channelId,pageNo,papers.size(),false);
		
		List<Channel> channels = channelDao.getRootCategory();
		mav.addObject("channels",channels);
		mav.addObject("channelId",channelId==null?0:channelId);
		mav.addObject("pageNo",pageNo==null?0:pageNo+1);
		mav.addObject("maxPageNo",result.getData());
		mav.addObject("hasNext",result.getCode()==0?true:false);
		mav.addObject("papers",papers);
		mav.setViewName("admin/paper/list");
		return mav;
		
	}
	@RequestMapping(method=RequestMethod.GET,value="queryByCondition")
	public ModelAndView queryByCondition(Long channelId,
							 Integer pageNo,
							 String queryTitle) {
		logger.debug("Received request to list papers");
		if(pageNo == null || pageNo <=0){
			pageNo = CURRENT_PAGE_NO;
		}
		pageNo = pageNo -1;
		ModelAndView mav = new ModelAndView();
		List<Paper> papers = paperService.getPapersByCondition(channelId,pageNo,queryTitle);
		logger.debug("papers Listing count = "+papers.size());
		//是否还有下一页，返回总条数
		OperationResult<Long> result = paperService.hasNextByCondition(channelId,queryTitle,pageNo,papers.size());
		
		List<Channel> channels = channelDao.getRootCategory();
		mav.addObject("channels",channels);
		mav.addObject("queryTitle",queryTitle);
		mav.addObject("channelId",channelId==null?0:channelId);
		mav.addObject("pageNo",pageNo==null?0:pageNo+1);
		mav.addObject("maxPageNo",result.getData());
		mav.addObject("hasNext",result.getCode()==0?true:false);
		mav.addObject("papers",papers);
		mav.setViewName("admin/paper/queryList");
		return mav;
		
	}
	@RequestMapping(method=RequestMethod.GET,value="detail") 
	public ModelAndView paperDetail(@RequestParam("paperId") Long paperId,
									Long channelId,
									Integer pageNo) {
		ModelAndView mav = new ModelAndView();
		
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
					   Long channelId,
					   Integer pageNo) {
		Paper paper =  paserPaper(null,request);
		paper =paperService.savePaper(paper);
		
		savePaperSection(paper.getId(),request);
		return "redirect:/paper/list?pageNo="+pageNo+"&channelId="+channelId;
	}
	@RequestMapping(method=RequestMethod.GET,value="toEdit") 
	public ModelAndView toEdit(@RequestParam("paperId") Long paperId,
							   Long channelId,
							   Integer pageNo) {
		ModelAndView mav = new ModelAndView();
		//查询父级栏目
		List<Channel> channels =channelDao.getRootCategory();
		Paper paper = paperDao.find(paperId);
		
		mav.addObject("channels",channels);
		mav.addObject("paper",paperService.getPaperModel(paper));
		mav.addObject("channelId",channelId);
		mav.addObject("pageNo",pageNo==null?0:pageNo);
		mav.setViewName("admin/paper/editPaper");
		return mav;
		
	}
	@RequestMapping(method=RequestMethod.POST,value="edit") 
	public String edit(HttpServletRequest request,
					   Long channelId,
					   Integer pageNo) {
		Long paperId = RequestUtil.longvalue(request,"paperId");
		Paper paper =  paserPaper(paperId,request);
		paper = paperDao.save(paper);

		savePaperSection(paper.getId(),request);
		return "redirect:/paper/list?pageNo="+pageNo+"&channelId="+channelId;
		
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
						   Integer pageNo) {
		paperDao.delete(paperId);
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
		}
		return "redirect:/paper/list?pageNo="+pageNo+"&channelId="+channelId;
		
	}
	@RequestMapping(method=RequestMethod.GET,value="updateAudit") 
	public String updateAudit(@RequestParam("paperId") Long paperId,
							  @RequestParam("hasAudit") Boolean hasAudit,
						      Long channelId,
						      Integer pageNo,
						      String redirect) {
		paperDao.auditPaper(paperId,hasAudit);
		if(StringUtils.isEmpty(redirect)){
			return "redirect:/paper/list?pageNo="+pageNo+"&channelId="+channelId;
		}else{
			return "redirect:"+redirect+"?paperId="+paperId+"&pageNo="+pageNo+"&channelId="+channelId;
		}
		
	}
	@RequestMapping(method=RequestMethod.POST,value="batchAudit") 
	public String batchAudit(HttpServletRequest request,
						    Long channelId,
						    Integer pageNo) {
		long[] wids = RequestUtil.longArrayValue(request, "ids");
		if(wids != null && wids.length >0){
			String string = arrToStr(wids);
			if(!StringUtils.isEmpty(string)){
				paperDao.batchAudit(string);
			}
		}
		return "redirect:/paper/list?pageNo="+pageNo+"&channelId="+channelId;
		
	}
	@RequestMapping(method=RequestMethod.GET,value="updateTop") 
	public String updateTop(@RequestParam("paperId") Long paperId,
						    @RequestParam("isTop") int isTop,
						    Long channelId,
						    Integer pageNo) {
		paperDao.updateTop(paperId, isTop);
		return "redirect:/paper/list?pageNo="+pageNo+"&channelId="+channelId;
		
	}
	@RequestMapping(method=RequestMethod.GET,value="updatePrior") 
	public String updatePrior(@RequestParam("paperId") Long paperId,
						    @RequestParam("type") Integer type,
						    Long channelId,
						    Integer pageNo) {
		//上移
		if(type == 1){
			paperDao.higherPriority(paperId);
		}
		//下移
		if(type == -1){
			paperDao.lowerPriority(paperId);
		}
		
		return "redirect:/paper/list?pageNo="+pageNo+"&channelId="+channelId;
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
		List<Paper> rePapers = paperDao.fgetRecPaperByCategory(paper.getChannelId(),paper.getId(),5, 0);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("paper", paperService.getPaperModel(paper));
		mav.addObject("rePapers", rePapers);
		mav.setViewName("forecontent");
		return mav;
	}
	
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
		if(channel != null){
			mav.addObject("channelTitleImg",channel.getTitleImg());
		}
		
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
							   HttpServletResponse response){
		Map<String, Object> result = Maps.newHashMap();

		try{
			//修改点击量
			paperService.addViewCount(paperId);

			//向数据库查询对应新闻的内容
			Paper paper = paperDao.find(paperId);
			
			List<Paper> recList = paperDao.fgetRecPaperByCategory(paper.getChannelId(),paper.getId(),5, 0);
			
	
			result.put("status", 0);//0标识成功，-1标识失败
			result.put("msg", "succ");
			result.put("paper", paperService.getPaperModel(paper));
			result.put("recList", recList);
		}catch(Exception ex){
			result.put("status", -1);//0标识成功，-1标识失败
			result.put("msg", "fail");
			result.put("paper", null);
			result.put("recList", Lists.newArrayList());
		}
		return render(JSONObject.fromObject(result).toString(),"application/json; charset=utf-8",response);
	}

	//=====================私有方法====================================
	//从request中获取paper对象
	private Paper paserPaper(Long paperId,HttpServletRequest request){
		Paper paper;
		if(paperId != null){
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
		if(channel == null){
			return null;
		}
		paper.setUpdateTime(new Date());
		paper.setChannelId(RequestUtil.longvalue(request, "channelId"));
		paper.setChannelName(channel.getName());
		paper.setTitle(RequestUtil.stringvalue(request, "title"));
		paper.setAuthor(RequestUtil.stringvalue(request, "author"));
		paper.setDescription(RequestUtil.stringvalue(request, "description"));
		paper.setIsRecom(RequestUtil.intvalue(request, "isRecom"));
		paper.setIsTop(RequestUtil.intvalue(request, "isTop"));
		String titleImg = RequestUtil.stringvalue(request, "titleImg");
		int index = titleImg.indexOf("res");
		if(index != -1){
			paper.setTitleImg(titleImg.substring(index));
		}
		paper.setPregStage(RequestUtil.intvalue(request, "pregStage"));
		paper.setRecPregWeeks(RequestUtil.intvalue(request, "recPregWeeks"));
		paper.setHospital(RequestUtil.stringvalue(request, "hospital"));
		return paper;
	}			
	/**
	 * 保存版块信息
	 * @param paperId
	 * @param request
	 */
	private void savePaperSection(Long paperId,HttpServletRequest request){
		int orderNum =1;
		for (int i = 1; i <= 10; i++) {
			Long sectionId = RequestUtil.longvalue(request, "sectionId"+i);
			String sectionTitle = RequestUtil.stringvalue(request, "sectionTitle"+i);
			if(StringUtils.isNotEmpty(sectionTitle)){
				PaperSection section =null;
				if(sectionId != null){
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
		for(int i=1;i<=15;i++){
			Long paraId = RequestUtil.longvalue(request, "paraId"+orderId+"-"+i);
			String paraTitle = RequestUtil.stringvalue(request, "paraTitle"+orderId+"-"+i);
			if(StringUtils.isEmpty(paraTitle)){
				continue;
			}
			PaperParagraph para =null;
			if(paraId != null){
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
		for(int i=1;i<=15;i++){
			Long outLinkId = RequestUtil.longvalue(request, "outLinkId"+orderId+"-"+i);

			String outTitle = RequestUtil.stringvalue(request, "outTitle"+orderId+"-"+i);
			if(StringUtils.isEmpty(outTitle)){
				continue;
			}
			PaperOutLink link = null;
			if(outLinkId != null){
				link = outLinkDao.find(outLinkId); 
			}
			link =(link==null)?new PaperOutLink():link;
			link.setTitle(outTitle);
			link.setSecTitle(RequestUtil.stringvalue(request, "outSecTitle"+orderId+"-"+i));
			link.setPrize(RequestUtil.doublevalue(request, "outPrize"+orderId+"-"+i));
			link.setOuterUrl(RequestUtil.stringvalue(request, "outUrl"+orderId+"-"+i));
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
}
