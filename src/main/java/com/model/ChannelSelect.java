package com.model;

public class ChannelSelect {
	private long id;
	private String name;
	
	private boolean select = false;
	
	private boolean isfolder = false;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public boolean isIsfolder() {
		return isfolder;
	}
	public void setIsfolder(boolean isfolder) {
		this.isfolder = isfolder;
	}
	public String toString() {
		return name+ "=" + id + "\r\n";
	}
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	
	
	
	

}
