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

	//头图地址
	private String titleImg;
	
	private Long paperId;
	
	//访问文章详情的url
	private String paperUrl;
}
