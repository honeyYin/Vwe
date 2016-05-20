package com.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.PaperDao;
import com.entity.Paper;
import com.model.OperationResult;

@Service
public class PaperService {
	
	@Autowired
	private PaperDao paperDao;

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
}
