package com.ecommerce.arolaz.Brand.Model;


import com.querydsl.core.annotations.QueryEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@QueryEntity
@Document(collection = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId brandId;

    private String brandName;

    public Brand(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    protected Brand(){}

    public ObjectId getBrandId() {
        return brandId;
    }
}
