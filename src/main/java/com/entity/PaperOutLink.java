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
 * 外部链接区域
 * @author lya
 *
 */
@Setter
@Getter
@Entity(name="OutLink")
public class PaperOutLink implements Serializable{
	
	private static final long serialVersionUID = -1308795024262635691L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column	
	private Date createTime =new Date();
	
	@Column	
	private Date updateTime=new Date();
	
	@Column	
	private String title;
	
	@Column	
	private String secTitle;
	
	@Column	
	private Double prize;
	
	@Column	
	private String outerUrl;
	
	@Column	
	private Long sectionId;
	
	@Column	
	private Long paperId;
}
