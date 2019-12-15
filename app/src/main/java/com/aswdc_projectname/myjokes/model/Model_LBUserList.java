package com.aswdc_projectname.myjokes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 01-12-2017.
 */

public class Model_LBUserList {

    @SerializedName("IsResult")
    @Expose
    private Integer isResult;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("ResultList")
    @Expose
    private Object resultList;

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

    public Object getResultList() {
        return resultList;
    }

    public void setResultList(Object resultList) {
        this.resultList = resultList;
    }
}
