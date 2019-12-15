package com.aswdc_projectname.myjokes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model_ApiResponse {

    @SerializedName("IsResult")
    @Expose
    private Integer isResult;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("ResultList")
    @Expose
    private List<Model_LBUserList> resultList = null;

    public Integer getIsResult() {
        return isResult;
    }

    public void setIsResult(Integer isResult) {
        this.isResult = isResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Model_LBUserList> getResultList() {
        return resultList;
    }

    public void setResultList(List<Model_LBUserList> resultList) {
        this.resultList = resultList;
    }

}