package com.ecommerce.arolaz.Utils;

public class ObjectCreationSuccessResponse {
    private String id;
    private Integer responseCode;

    public ObjectCreationSuccessResponse(){}

    public ObjectCreationSuccessResponse(String id, Integer responseCode) {
        this.id = id;
        this.responseCode = responseCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
}
