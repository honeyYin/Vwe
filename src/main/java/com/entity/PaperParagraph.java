package com.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * 文章小节
 * @author hzyinhonglian
 *
 */
@Setter
@Getter
@Entity(name="PaperParagraph")
public class PaperParagraph implements Serializable{
	
	private static final long serialVersionUID = -1308795024262635691L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column	
	private Date createTime=new Date();
	
	@Column	
	private Date updateTime=new Date();
	
	/**
	 * 小节标题
	 */
	@Column(nullable=false)		
	private String title;
	
	/**
	 * 正文
	 */
	@Column	
	private String content;
	
	/**
	 * 配图url
	 */
	@Column	
	private String imgUrl;
	
	/**
	 * 排序
	 */
	@Column
	private Integer orderNum;
	
	/**
	 * 所属版块id
	 */
	@Column(nullable=false)		
	private Long sectionId;
	/**
	 * 所属文章id
	 */
	@Column(nullable=false)		
	private Long paperId;
	//是否删除
	@Column	
	private int disabled=0;
	
}
