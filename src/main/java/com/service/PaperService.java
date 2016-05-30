package com.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.constant.OperationTypeEnum;
import com.constant.PaperElementEnum;
import com.dao.OperationRecordDao;
import com.dao.PaperDao;
import com.dao.PaperImageDao;
import com.dao.PaperOutLinkDao;
import com.dao.PaperParaDao;
import com.dao.PaperSectionDao;
import com.entity.OperationRecord;
import com.entity.Paper;
import com.entity.PaperImage;
import com.entity.PaperParagraph;
import com.entity.PaperSection;
import com.entity.User;
import com.google.common.collect.Lists;
import com.model.OperationResult;
import com.model.PaperModel;
import com.model.PaperParagraphModel;
import com.model.PaperSectionModel;
import com.model.PaperTitleImgModel;

@Service
public class PaperService {
	
	@Autowired
	private PaperDao paperDao;
	@Autowired
	private PaperParaDao paraDao;
	
	@Autowired
	private PaperSectionDao sectionDao;
	
	@Autowired
	private PaperOutLinkDao outLinkDao;

	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private PaperImageDao imageDao;
	
	@Autowired
	private OperationRecordDao operationDao;
	
	private static final int CURRENT_PAGE_SIZE = 10;
	
	public Paper savePaper(Paper paper,User user){
		Long id = paper.getId();
		//操作类型
		OperationTypeEnum type =OperationTypeEnum.UPDATE;
		Paper entity = paperDao.save(paper);
		if(id == null || id <=0){
			entity.setPriority(entity.getId());
			entity =paperDao.save(entity);
			
			type = OperationTypeEnum.ADD;
		}
		//记录操作信息
		saveOperationRecord(entity.getId(),type,user);
		return entity;
	}
	//保存paper的操作记录
	public void saveOperationRecord(Long paperId,
									OperationTypeEnum type,
									User user){
		
		OperationRecord record = new OperationRecord();
		record.setEntity(PaperElementEnum.PAPER.getName());
		record.setEntityId(paperId);
		record.setOperatorName(user.getLoginName());
		record.setOperatorId(user.getId());
		record.setType(type.getName());
		operationDao.save(record);		
				
	}
	/**
	 * 分页获取数据
	 * @param cateId
	 * @param pageNo
	 * @return
	 */
	public List<Paper> getPapersByPage(Long cateId,Integer pageNo){
		int limit = getPageSizeByChannel(cateId);
		int offset = pageNo * limit;
		List<Paper> papers = null;
		if(cateId == null || cateId == 0){
			papers = paperDao.getPaperByPage(limit,offset);
		}else{
			papers = paperDao.getPaperByCategory(cateId,limit,offset);
		}
		return papers;
	}
	public List<Paper> getfPapersByPage(Long cateId,Integer pageNo){
		int limit = getPageSizeByChannel(cateId);
		int offset = pageNo * limit;
		List<Paper> papers = null;
		if(cateId == null || cateId == 0){
			papers = paperDao.fgetPaperByPage(limit,offset);
		}else{
			papers = paperDao.fgetPaperByCategory(cateId,limit,offset);
		}
		return papers;
	}
	public List<Paper> getPapersByCondition(Long cateId,Integer pageNo,String queryTitle){
		int limit = getPageSizeByChannel(cateId);
		int offset = pageNo * limit;
		StringBuffer sql = new StringBuffer("select p from Paper p where p.disabled = 0 ");
		if(cateId != null && cateId > 0){
			sql.append(" and p.channelId="+cateId);
		}
		if(StringUtils.isNotEmpty(queryTitle)){
			sql.append(" and p.title like '%"+queryTitle+"%' ");
		}
		sql.append(" ORDER BY p.hasAudit DESC,p.isTop DESC,p.updateTime DESC");
		return paperDao.getPaperByCondition(sql.toString(),limit,offset);
	}
	/**
	 * 查看是否还有下一页，并返回最大页数
	 * @param pageNo
	 * @param pageSize
	 * @param currentPageSize
	 * @return
	 */
	public OperationResult<Long> hasNext(Long cateId,Integer pageNo,Integer currentPageSize,boolean front){
		OperationResult<Long> result = new OperationResult<Long>();
		int pageSize = getPageSizeByChannel(cateId); //获取页数大小
		Long total = 0l;
		if(cateId ==null || cateId == 0){
			if(front){
				total = paperDao.fgetAllPageSize();
			}else{
				total = paperDao.getAllPageSize();
			}
			
		}else{
			if(front){
				total = paperDao.fgetCategoryPageSize(cateId);
			}else{
				total = paperDao.getCategoryPageSize(cateId);
			}
			
		}
		result.setCode(0);
		result.setMsg("has next page");
		
		//求最大页数
		Long maxPageNo = 1l;
		if(total != null){
			if(total/pageSize >=1){
				maxPageNo = total/pageSize;
				if(total%pageSize > 0){
					maxPageNo = maxPageNo+1;
				}
			}
		}
		result.setData(maxPageNo);	
		
		Integer currentCount= pageNo*pageSize+currentPageSize;
		if(currentCount >= total){
			result.setCode(-1);
			result.setMsg("no next page");
		}
		return result;
	}
	public OperationResult<Long> hasNextByCondition(Long cateId,String queryTitle,Integer pageNo,Integer currentPageSize){
		OperationResult<Long> result = new OperationResult<Long>();
		int pageSize = getPageSizeByChannel(cateId); //获取页数大小
		Long total = 0l;
		StringBuffer sql = new StringBuffer("select count(p.id) from Paper p where p.disabled = 0 ");
		if(cateId != null && cateId > 0){
			sql.append(" and p.channelId="+cateId);
		}
		if(StringUtils.isNotEmpty(queryTitle)){
			sql.append(" and p.title like '%"+queryTitle+"%' ");
		}
		sql.append(" ORDER BY p.hasAudit DESC,p.isTop DESC,p.updateTime DESC");
		total = paperDao.getPaperSizeByCondition(sql.toString());
		result.setCode(0);
		result.setMsg("has next page");
		
		//求最大页数
		Long maxPageNo = 1l;
		if(total != null){
			if(total/pageSize >=1){
				maxPageNo = total/pageSize;
				if(total%pageSize > 0){
					maxPageNo = maxPageNo+1;
				}
			}
		}
		result.setData(maxPageNo);	
		
		Integer currentCount= pageNo*pageSize+currentPageSize;
		if(currentCount >= total){
			result.setCode(-1);
			result.setMsg("no next page");
		}
		return result;
	}
	/**
	 * 获取栏目下显示的页数
	 * @param channelId
	 * @return
	 */
	private  int getPageSizeByChannel(Long channelId) {
		Integer pageSize = channelService.getPageSizeByChannel(channelId);
		if(pageSize == null || pageSize== 0){
			pageSize = CURRENT_PAGE_SIZE;
		}
		return pageSize;
	}
	
	public synchronized boolean addViewCount(long paperId){
		Paper paper = paperDao.find(paperId);
		Long count = paper.getViewCount();
		if(count == null){
			count = 0l;
		}
		return paperDao.updateViewCount(paperId, ++count)>0;
	}
	
	public PaperModel getPaperModel(Paper paper){
		if(paper == null){
			return null;
		}
		
		PaperModel model = new PaperModel(paper);
		if(StringUtils.isNotEmpty(model.getTitleImg())){
			List<PaperImage> images = imageDao.findByUrl(model.getTitleImg());
			if(!CollectionUtils.isEmpty(images)){
				model.setImgHeight(images.get(0).getHeight());
				model.setImgWidth(images.get(0).getWidth());
				model.setImgUrlRatio(images.get(0).getRatio()); 
			}
		}
		List<PaperSection> sections = sectionDao.getSecitonByPaper(paper.getId());
		
		if(!CollectionUtils.isEmpty(sections)){
			List<PaperSectionModel> sectionModels = Lists.newArrayList();
			for(PaperSection section :sections){
				PaperSectionModel sectionModel = new PaperSectionModel();
				sectionModel.setId(section.getId());
				sectionModel.setPaperId(section.getPaperId());
				sectionModel.setOrderNum(section.getOrderNum());
				sectionModel.setTitle(section.getTitle());
				sectionModel.setParas(getParaModels(paraDao.getParaBySection(section.getId())));
				sectionModel.setOutLinks(outLinkDao.getOutLinkBySection(section.getId()));
				sectionModels.add(sectionModel);
			}
			
			model.setSections(sectionModels);
		}
		return model;
	}
	public List<PaperParagraphModel> getParaModels(List<PaperParagraph> paras){
		if(CollectionUtils.isEmpty(paras)){
			return Lists.newArrayList();
		}
		int orderNum = 1;
		 List<PaperParagraphModel>  results = Lists.newArrayList();
		for(PaperParagraph item:paras){
			PaperParagraphModel model = new PaperParagraphModel(item);
			if(StringUtils.isEmpty(model.getTitle())){
				model.setOrderNum(-1);
			}else{
				model.setOrderNum(orderNum++);
			}
			if(StringUtils.isNotEmpty(model.getImgUrl())){
				List<PaperImage> images = imageDao.findByUrl(model.getImgUrl());
				if(!CollectionUtils.isEmpty(images)){
					model.setImgHeight(images.get(0).getHeight());
					model.setImgWidth(images.get(0).getWidth());
					model.setImgUrlRatio(images.get(0).getRatio()); 
				}
			}
			results.add(model);
		}
		return results;
	}
	 public List<PaperModel> getPaperModels(List<Paper> papers){
		 if(CollectionUtils.isEmpty(papers)){
			 return Lists.newArrayList();
		 }
		 List<PaperModel> result = Lists.newArrayList();
		 for(Paper item:papers){
			 PaperModel model = getPaperModel(item);
			 result.add(model);
		 }
		 return result;
	 }
	 /**
	  * 获取推荐的三篇文章头图
	  * @return
	  */
	public List<PaperTitleImgModel> getPaperTitleImgs(HttpServletRequest request) {
		List<Paper> papers = paperDao.fgetPaperByPage(3,0);
		List<PaperTitleImgModel> modelsLists = Lists.newArrayList();

		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		if(!CollectionUtils.isEmpty(papers)){
			for(Paper item: papers){
				PaperTitleImgModel model = new PaperTitleImgModel();
				model.setPaperId(item.getId());
				model.setPaperUrl(basePath+"paperDetail?paperId="+item.getId());
				if(StringUtils.isNotEmpty(item.getTitleImg())){
					model.setTitleImg(basePath+item.getTitleImg());
					List<PaperImage> images = imageDao.findByUrl(item.getTitleImg());
					if(!CollectionUtils.isEmpty(images)){
						model.setImgHeight(images.get(0).getHeight());
						model.setImgWidth(images.get(0).getWidth());
						model.setImgUrlRatio(images.get(0).getRatio()); 
					}
				}
				
				modelsLists.add(model);
			}
		}
		return modelsLists;
	}
	
	public int higherPriority(Long paperId) {
		Paper hPaper = paperDao.find(paperId);
		int result = 0;
		List<Paper> papers = paperDao.findLPaper(hPaper.isHasAudit(), hPaper.getIsTop(), hPaper.getPriority(),hPaper.getIsRecom());
		if(!CollectionUtils.isEmpty(papers)){
			Paper lPaper = papers.get(0);
			result = paperDao.updatePriority(hPaper.getId(),lPaper.getPriority());
			paperDao.updatePriority(lPaper.getId(), hPaper.getPriority());
		}
		return result;
	}
	public int lowerPriority(Long paperId) {
		Paper lPaper = paperDao.find(paperId);
		int result = 0;
		List<Paper> papers = paperDao.findHPaper(lPaper.isHasAudit(), lPaper.getIsTop(), lPaper.getPriority(),lPaper.getIsRecom());
		if(!CollectionUtils.isEmpty(papers)){
			Paper hPaper = papers.get(0);
			result = paperDao.updatePriority(lPaper.getId(),hPaper.getPriority());
			paperDao.updatePriority(hPaper.getId(), lPaper.getPriority());
		}
		return result;
	}
}
