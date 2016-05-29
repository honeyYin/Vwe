package com.constant;

public enum PaperElementEnum {
	PAPER(0,"paper"),  
	SECTION(1,"section"),  
	OUTLINK(2,"outlink"), 
	PARA(3,"para"), 
	CHANNEL(4,"para");  
	
	
	private int code;
	private String name;
	
	PaperElementEnum(){}

	PaperElementEnum(int code,String name){
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
	
	public static PaperElementEnum valueOfCode(int code){
		for(PaperElementEnum item: PaperElementEnum.values()){
			if(item.getCode() == code){
				return item;
			}
		}
		return null;
	}
	public static PaperElementEnum valueOfName(String name){
		for(PaperElementEnum item: PaperElementEnum.values()){
			if(name.trim().equals(item.getName())){
				return item;
			}
		}
		return null;
	}
}
