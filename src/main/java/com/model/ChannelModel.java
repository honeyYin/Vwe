package com.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
/**
 * 分类
 * @author hzyinhonglian
 *
 */
@Setter
@Getter
public class ChannelModel{
	
	private Long id;
	
	private Date createTime;
	
	private Date updateTime;
	
	private String name;
	
	private Long parentId;
	
    private int disabled;//0激活 1未激活
	
	//排列优先级
	private int priority;
	
	private List<ChannelModel> sets = new ArrayList<ChannelModel>(0);
	
	private final static String SP = ",";
	public String toJson(){
		
		StringBuffer sb = new StringBuffer("{");
		
		sb.append("\"id\":\"" + this.getId() +"\""+ SP);
		sb.append("\"channelId\":" + this.getId() + SP);
		//text
		sb.append("\"text\":\"" + this.getName()+"\""+ SP);
		//value : 0
		sb.append("\"value\":0"+ SP);
		//showcheck  true
		sb.append("\"showcheck\":true"+ SP);
		//isexpand  true  false
//		sb.append("\"isexpand\":");
		if(this.getParentId() == 0){
			sb.append("\"isexpand\":true"+ SP);
		}else {
			sb.append("\"isexpand\":false"+ SP);
		}
		//checkstate 0
		sb.append("\"checkstate\":0"+ SP);
		//complete :false
		
		
		//hasChildren  true  false
		if(null != sets && sets.size() > 0){
			sb.append("\"hasChildren\":true"+ SP);
			sb.append("\"complete\":true"+ SP);
		}else {
			sb.append("\"hasChildren\":false"+ SP);
			sb.append("\"complete\":false"+ SP);
		}
		
		//ChildNodes []
		sb.append("\"ChildNodes\":");
		if(null != sets && sets.size() > 0){
			sb.append("[");
//			Collections.so
			List<ChannelModel> temp = new ArrayList<ChannelModel>(sets);
			Collections.sort(temp, new ChannelSorter());
			int length = temp.size();
			
			sb.append(temp.get(0).toJson());
			
			for (int i = 1; i < length; i++) {
				sb.append(SP);
				sb.append(temp.get(i).toJson());
			}
//			for (JcChannel jcChannel :temp) {
//				sb.append(jcChannel.toJson());
//				
//				
//			}
			
			sb.append("]");
		}else {
			sb.append("null");
		}
		
		sb.append("}");
		return sb.toString();
	}
	
	public List<ChannelSelect> toSelects(String prex, String afterprex, long channelId){
		 List<ChannelSelect> result = new ArrayList<ChannelSelect>();
		 ChannelSelect select = new ChannelSelect();
		 select.setId(this.getId());
		 select.setName(prex + this.getName());
		 
		 if(select.getId() == channelId){
			 select.setSelect(true);
		 }
		 result.add(select);
		 
		 String nextpref = prex + afterprex;
		 if(null != sets && sets.size() > 0){
			 select.setIsfolder(true);
			 List<ChannelModel> temp = new ArrayList<ChannelModel>(sets);
				Collections.sort(temp, new ChannelSorter());
				for(ChannelModel jcChannel : temp){
					add(result, jcChannel.toSelects(nextpref, afterprex,channelId));
				}
		 }
		 
		return result;
	}
	
	private void add(List<ChannelSelect> list,List<ChannelSelect> lists){
		if(null != lists){
			for(ChannelSelect select : lists){
				list.add(select);
			}
		}
	}
	
}
