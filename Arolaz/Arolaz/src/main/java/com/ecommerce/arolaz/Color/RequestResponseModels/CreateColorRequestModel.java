package com.ecommerce.arolaz.Color.RequestResponseModels;

public class CreateColorRequestModel {
    private String colorName;
    public CreateColorRequestModel(){
    }

    public CreateColorRequestModel(String colorName) {
        this.colorName = colorName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
