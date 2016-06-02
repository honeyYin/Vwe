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

@Setter
@Getter
@Entity(name="CrawlerSite")
public class CrawlerSite implements Serializable{
	
	private static final long serialVersionUID = -1308795024262635691L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column	
	private Date createTime=new Date();
	
	@Column	
	private Date updateTime=new Date();
	
	@Column	
	private String title;
	
	@Column	
	private String url;
	
	@Column	
    private int disabled=0;//0激活 1未激活
}
