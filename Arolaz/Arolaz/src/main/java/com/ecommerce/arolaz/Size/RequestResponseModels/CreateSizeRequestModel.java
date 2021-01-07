package com.ecommerce.arolaz.Size.RequestResponseModels;

public class CreateSizeRequestModel {
    private String sizeName;

    public CreateSizeRequestModel(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
}
