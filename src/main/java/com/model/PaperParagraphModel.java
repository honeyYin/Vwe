package com.model;


import java.io.Serializable;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

import com.entity.PaperParagraph;

/**
 * 文章小节
 * @author hzyinhonglian
 *
 */
@Setter
@Getter
public class PaperParagraphModel implements Serializable{
	
	private Long id;
	
	/**
	 * 小节标题
	 */
	private String title;
	
	/**
	 * 正文
	 */
	private String content;
	
	/**
	 * 配图url
	 */
	@Column	
	private String imgUrl;
	
	private Integer imgHeight;
	
	private Integer imgWidth;
	//图片宽高比
	private String imgUrlRatio;
	/**
	 * 排序
	 */
	private Integer orderNum;
	
	/**
	 * 所属版块id
	 */
	private Long sectionId;
	/**
	 * 所属文章id
	 */
	private Long paperId;
	
	public PaperParagraphModel(){}
	
	public PaperParagraphModel(PaperParagraph para){
		this.title = para.getTitle();
		this.id = para.getId();
		this.content = para.getContent();
		this.imgUrl = para.getImgUrl();
		this.orderNum = para.getOrderNum();
		this.sectionId = para.getSectionId();
		this.paperId = para.getPaperId();
	}
	
}
