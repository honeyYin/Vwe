package com.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.model.PregStage;

import lombok.Getter;
import lombok.Setter;
/**
 * 文章
 * @author hzyinhonglian
 *
 */
@Setter
@Getter
@Entity(name="Paper")
public class Paper  implements Serializable{
	private static final long serialVersionUID = -1308795024262635691L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column	
	private Date createTime =new Date();
	
	@Column	
	private Date updateTime=new Date();
	//发布时间
	@Column
	private Date auditTime;
	
	@Column(nullable = false)
	private String title;

	//简介
	@Column	
	private String description;
	
	@Column	
	private String author="admin";
	//点击量
	@Column	
	private Long viewCount=0l;
	
	@Column(nullable = false)
	private Long channelId;
	
	@Column 
	private String channelName;
	//是否已发布
	@Column	
	private boolean hasAudit=false;
	//是否置顶
	@Column	
	private int isTop=0;
	//是否推荐
	@Column	
	private int isRecom=0;
	//是否删除
	@Column	
	private int disabled=0;
	//封面图url
	@Column	
	private String titleImg;
	
	//推荐孕周
	@Column
	private PregStage pregStage;
	
	
	@Column
	private Integer recPregWeeks;
	
	//所属医院
	@Column
	private String hospital;
	//顺序
//	@Column 
//	private Integer orderNum=20;
	
	public void setPregStage(int stage){
		this.pregStage = PregStage.valueOfCode(stage);
	}
	public PregStage getPregStage(){
		return this.pregStage;
		
	}
}
