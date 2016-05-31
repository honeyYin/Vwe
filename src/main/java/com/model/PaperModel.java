package com.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.entity.Paper;
import com.google.common.collect.Lists;
import com.util.DateUtil;
import com.util.StringUtil;
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
	
	private String author;
	//点击量
	private Long viewCount;
	
	private Long channelId;
	
	private String channelName;
	//是否已发布
	private boolean hasAudit=false;
	//是否置顶
	private int isTop=0;
	
	private int isRecom=0;
	//是否删除
	private int disabled=0;
	//封面图url
	private String titleImg;
	
	private Integer imgHeight;
	
	private Integer imgWidth;
	//图片宽高比
	private String imgUrlRatio;

	private Integer pregStageCode;
	
	private String pregStage;
	
	private Integer recPregWeeks;
	
	//所属医院
	private String hospital;	
	
	private List<PaperSectionModel> sections;
	
	PaperModel(){}
	
	public PaperModel(Paper paper){
		this.auditTime = DateUtil.dateToStr(paper.getAuditTime());
		this.author = StringUtil.encode(paper.getAuthor());
		this.channelId =paper.getChannelId();
		this.channelName = StringUtil.encode(paper.getChannelName());
		this.description = StringUtil.encode(paper.getDescription());
		this.disabled = paper.getDisabled();
		this.title = StringUtil.encode(paper.getTitle());
		this.hasAudit = paper.isHasAudit();
		this.isTop = paper.getIsTop();
		this.isRecom = paper.getIsRecom();
		this.id = paper.getId();
		this.titleImg = paper.getTitleImg();
		this.viewCount = paper.getViewCount();
		if(paper.getPregStage() != null){
			this.pregStageCode = paper.getPregStage().getCode();
		}
		this.pregStage = paper.getPregStage().getName();
		this.recPregWeeks = paper.getRecPregWeeks();
		this.hospital = StringUtil.encode(paper.getHospital());
	}
	public static List<PaperModel> getPaperModels(List<Paper> papers){
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
