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
 * 分类
 * @author hzyinhonglian
 *
 */
@Setter
@Getter
@Entity(name="Channel")
public class Channel implements Serializable{
	
	private static final long serialVersionUID = -1308795024262635691L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column	
	private Date createTime=new Date();
	
	@Column	
	private Date updateTime=new Date();
	
	@Column	
	private String name;
	
	@Column	
	private Long parentId = 0l;
	
	@Column	
    private int disabled=0;//0激活 1未激活
	
	//是否发布
	@Column
	private int isDeploy=0;
	
	//排列优先级
	@Column
	private int priority=10;
	
	//栏目每页条数
	@Column
	private int pageSize=10;
	
	@Column
	private String description;
	
	/*//栏目图片
	@Column
	private String titleImg;*/
}
