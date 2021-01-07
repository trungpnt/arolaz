package com.ecommerce.arolaz.Color.RequestResponseModels;

public class ColorResponseModel {
    private String colorId;
    private String colorName;

    public ColorResponseModel(){}

    public ColorResponseModel(String colorId, String colorName) {
        this.colorId = colorId;
        this.colorName = colorName;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
