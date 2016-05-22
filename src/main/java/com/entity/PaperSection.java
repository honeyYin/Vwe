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
 * 文章版块
 * @author hzyinhonglian
 *
 */
@Setter
@Getter
@Entity(name="PaperSection")
public class PaperSection  implements Serializable{
	private static final long serialVersionUID = -1308795024262635691L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column	
	private Date createTime=new Date();
	
	@Column	
	private Date updateTime=new Date();
	
	/**
	 * 版块标题
	 */
	@Column(nullable=false)		
	private String title;
	
	@Column(nullable = false)
	private Long paperId;
	
}
