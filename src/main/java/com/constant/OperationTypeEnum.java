package com.constant;

public enum OperationTypeEnum {
	ADD(0,"新增"),  
	UPDATE(1,"更新"),  
	DELETE(2,"删除"),  
	AUDIT(3,"发布"),  
	TO_TOP(4,"置顶"),  
	UP_LEVEL(5,"上移"), 
	DOWN_LEVEL(6,"下移");  
	
	
	private int code;
	private String name;
	
	OperationTypeEnum(){}

	OperationTypeEnum(int code,String name){
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
	
	public static OperationTypeEnum valueOfCode(int code){
		for(OperationTypeEnum item: OperationTypeEnum.values()){
			if(item.getCode() == code){
				return item;
			}
		}
		return null;
	}
}
