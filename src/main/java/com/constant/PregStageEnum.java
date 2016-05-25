package com.constant;

public enum PregStageEnum {
	P_ALL(0,"全孕周"),  //全孕周
	P_LT_40(1,"40周以上"),  //40周以上
	P_ST_40(2,"0-40周");  //0-4周
	
	
	private int code;
	private String name;
	
	PregStageEnum(){}

	PregStageEnum(int code,String name){
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
	
	public static PregStageEnum valueOfCode(int code){
		for(PregStageEnum item: PregStageEnum.values()){
			if(item.getCode() == code){
				return item;
			}
		}
		return null;
	}
}
