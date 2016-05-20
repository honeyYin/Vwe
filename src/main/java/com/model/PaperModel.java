package com.model;

import java.io.Serializable;
import java.util.List;
import com.entity.Paper;
import com.google.common.collect.Lists;
import com.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
/**
 * 文章
 * @author hzyinhonglian
 *
 */
@Setter
@Getter
public class PaperModel  implements Serializable{
	private static final long serialVersionUID = -1308795024262635691L;

	private Long id;
	
	//发布时间
	private String auditTime;
	
	private String title;
	
	//副标题
	private String secTitle;
	
	//简介
	private String description;
	//内容
	private String content;
	
	private String author;
	//点击量
	private Long viewCount;
	
	private Long channelId;
	
	private String channelName;
	//是否已发布
	private boolean hasAudit=false;
	//是否置顶
	private int isTop=0;
	//是否推荐
	private int isRecom=0;
	//是否删除
	private int disabled=0;
	//封面图url
	private String titleImg;
	
	PaperModel(){}
	
	PaperModel(Paper paper){
		this.auditTime = DateUtil.dateToStr(paper.getAuditTime());
		this.author = paper.getAuthor();
		this.channelId =paper.getChannelId();
		this.channelName = paper.getChannelName();
		this.content = paper.getContent();
		this.description = paper.getDescription();
		this.disabled = paper.getDisabled();
		this.title = paper.getTitle();
		this.secTitle = paper.getSecTitle();
		this.hasAudit = paper.isHasAudit();
		this.isTop = paper.getIsTop();
		this.id = paper.getId();
		this.isRecom = paper.getIsRecom();
		this.titleImg = paper.getTitleImg();
		this.viewCount = paper.getViewCount();
	}
	public static List getPaperModels(List<Paper> papers){
		List<PaperModel> results = Lists.newArrayList();
		if (papers == null || papers.size()<=0) {
			return Lists.newArrayList();
		}
		for(Paper paper:papers){
			results.add(new PaperModel(paper));
		}	
		return results;
	}
}
