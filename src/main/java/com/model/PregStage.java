package com.model;

public enum PregStage {
	P_ALL(0,""),  //全孕周
	P_LT_40(1,""),  //40周以上
	P_ST_40(2,"");  //0-4周
	
	
	private int code;
	private String name;
	
	PregStage(){}

	PregStage(int code,String name){
		this.code = code;
		this.name = name;
	}
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static PregStage valueOfCode(int code){
		for(PregStage item: PregStage.values()){
			if(item.getCode() == code){
				return item;
			}
		}
		return null;
	}
}
