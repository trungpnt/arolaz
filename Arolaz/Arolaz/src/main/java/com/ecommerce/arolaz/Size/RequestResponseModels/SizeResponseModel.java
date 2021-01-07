package com.ecommerce.arolaz.Size.RequestResponseModels;

public class SizeResponseModel {
    private String sizeId;
    private String sizeName;

    public SizeResponseModel(String sizeId, String sizeName) {
        this.sizeId = sizeId;
        this.sizeName = sizeName;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
}
