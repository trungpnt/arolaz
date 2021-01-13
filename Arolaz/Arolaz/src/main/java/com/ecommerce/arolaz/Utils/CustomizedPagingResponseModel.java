package com.ecommerce.arolaz.Utils;

import java.util.ArrayList;
import java.util.List;

public class CustomizedPagingResponseModel<T> {
    private List<T> pagingData = new ArrayList<T>();
    int totalPage;

    public CustomizedPagingResponseModel(List<T> pagingData, int totalPage) {
        this.pagingData = pagingData;
        this.totalPage = totalPage;
    }
    public CustomizedPagingResponseModel(){}
    public void populate(T t){
        pagingData.add(t);
    }
    public List<T> getPagingData() {
        return pagingData;
    }

    public void setPagingData(List<T> pagingData) {
        this.pagingData = pagingData;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
