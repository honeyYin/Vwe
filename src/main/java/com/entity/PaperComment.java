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
 * 评论表
 * @author lya
 *
 */
@Setter
@Getter
@Entity(name="PaperComment")
public class PaperComment implements Serializable{
	private static final long serialVersionUID = -1308795024262635691L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column	
	private Date createTime;
	
	@Column	
	private Date updateTime=new Date();
	
	/**
	 * 会员id
	 */
	@Column(nullable=true)		
	private Long userId;
	
	/**
	 * 评论文章id
	 */
	@Column(nullable=false)		
	private Long paperId;

	@Column(nullable=false)		
	private String comment;
	/**
	 * 评论时间
	 */
	@Column(nullable=false)	
	private Date commentTime;
	/**
	 * 父评论id，若为一级评论则为0
	 */
	@Column(nullable=true)
	private Long parentId;
}
