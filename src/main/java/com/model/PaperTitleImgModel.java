package com.model;

import lombok.Getter;
import lombok.Setter;
/**
 * 仅包含文章头图的模型
 * @author lya
 *
 */
@Setter
@Getter
public class PaperTitleImgModel {

	private String title;
	//头图地址
	private String titleImg;
	
	private Long paperId;
	//文章类型
	private Integer type;
	
	//访问文章详情的url
	private String paperUrl;
	
	private Integer imgHeight;
	
	private Integer imgWidth;
	
	private String imgUrlRatio;
}
