package com.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dao.PaperDao;
import com.dao.PaperOutLinkDao;
import com.dao.PaperParaDao;
import com.dao.PaperSectionDao;
import com.entity.Paper;
import com.entity.PaperSection;
import com.google.common.collect.Lists;
import com.model.OperationResult;
import com.model.PaperModel;
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
	private static final int CURRENT_PAGE_SIZE = 10;
	
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
			papers = paperDao.getfPaperByPage(limit,offset);
		}else{
			papers = paperDao.getfPaperByCategory(cateId,limit,offset);
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
				total = paperDao.getfAllPageSize();
			}else{
				total = paperDao.getAllPageSize();
			}
			
		}else{
			if(front){
				total = paperDao.getfCategoryPageSize(cateId);
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
		List<PaperSection> sections = sectionDao.getSecitonByPaper(paper.getId());
		
		if(!CollectionUtils.isEmpty(sections)){
			List<PaperSectionModel> sectionModels = Lists.newArrayList();
			for(PaperSection section :sections){
				PaperSectionModel sectionModel = new PaperSectionModel();
				sectionModel.setId(section.getId());
				sectionModel.setPaperId(section.getPaperId());
				sectionModel.setOrderNum(section.getOrderNum());
				sectionModel.setTitle(section.getTitle());
				sectionModel.setParas(paraDao.getParaBySection(section.getId()));
				sectionModel.setOutLinks(outLinkDao.getOutLinkBySection(section.getId()));
				sectionModels.add(sectionModel);
			}
			
			model.setSections(sectionModels);
		}
		return model;
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
		List<Paper> papers = paperDao.getPaperByPage(3,0);
		List<PaperTitleImgModel> modelsLists = Lists.newArrayList();

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		if(!CollectionUtils.isEmpty(papers)){
			for(Paper item: papers){
				PaperTitleImgModel model = new PaperTitleImgModel();
				model.setPaperId(item.getId());
				model.setTitleImg(basePath+item.getTitleImg());
				
				model.setPaperUrl(basePath+"paperDetail?paperId="+item.getId());
				modelsLists.add(model);
			}
		}
		return modelsLists;
	}
}
