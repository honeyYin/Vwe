package com.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hzyinhonglian on 16/5/9.
 */
@Setter
@Getter
@ToString
public class OperationResult<T> {
    private Integer code;
    private String  msg;
    private T       data;

    public OperationResult(){
    }

    public OperationResult(T data){
        this.data = data;
        this.code = 0;
        this.msg = "操作成功.";
    }
}