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
 * 用户
 * @author hzyinhonglian
 *
 */
@Setter
@Getter
@Entity(name="User")
public class User  implements Serializable{

	private static final long serialVersionUID = -1308795024262635690L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column	
	private Date createTime;
	
	@Column	
	private Date updateTime=new Date();
	
	@Column	
	private String loginName;
	@Column	
	private String realName;
	@Column	
    private String password;
	@Column	
    private String email;
	@Column	
    private int disabled;//0激活 1未激活
	@Column	
    private int isAdmin;  //0 1

}
