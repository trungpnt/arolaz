package com.ecommerce.arolaz.S3BucketFileHandler;

public class ProductImgUrlResponseModel {
    private String fileName;
    private String imgUrl;
    protected ProductImgUrlResponseModel(){}

    public ProductImgUrlResponseModel(String fileName, String imgUrl) {
        this.fileName = fileName;
        this.imgUrl = imgUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
