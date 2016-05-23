package com.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.entity.PaperOutLink;
import com.entity.PaperParagraph;
import com.entity.PaperSection;

/**
 * 文章版块
 * @author hzyinhonglian
 *
 */
@Setter
@Getter
public class PaperSectionModel {
	private Long id;
	/**
	 * 版块标题
	 */
	private String title;
	
	private Long paperId;
	
	private int orderNum;

	private List<PaperParagraph> paras;
	
	private List<PaperOutLink> outLinks;
	
}
