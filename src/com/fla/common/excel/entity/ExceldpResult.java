package com.fla.common.excel.entity;

import java.util.ArrayList;
import java.util.List;

public class ExceldpResult<T> {
    private boolean succeed;
    private String message;
    private List<String> errorList = new ArrayList<String>();
    private T attribute;

    public ExceldpResult() {
        this.succeed = true;
        this.message = "Excel文件导入成功.";
    }

    public ExceldpResult(boolean succeed, String message) {
        this.succeed = succeed;
        this.message = message;
    }

    public void addError(String message){
        this.errorList.add(message);
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    public T getAttribute() {
        return attribute;
    }

    public void setAttribute(T attribute) {
        this.attribute = attribute;
    }
}
