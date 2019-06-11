package com.yjh.test.model;

import com.yjh.test.util.GSONUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Result<T> {
    private boolean status;
    private List<String> reasons;
    private T data;
    public Result() {
        this.status = false;
        this.reasons = null;
        this.data = null;
    }
    public Result(String... reasons){
        this.status = false;
        this.reasons = Arrays.asList(reasons);
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(String... reasons) {
        this.reasons = Arrays.asList(reasons);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void addReason(String reason){
        this.status = false;
        this.reasons = new ArrayList<>();
        reasons.add(reason);
    }

    @Override
    public String toString() {
        return GSONUtil.toJson(this);
    }
}
