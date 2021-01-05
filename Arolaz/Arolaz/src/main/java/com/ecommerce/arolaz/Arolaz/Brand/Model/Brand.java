package com.ecommerce.arolaz.Arolaz.Brand.Model;


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

    protected Brand(){}

    public ObjectId getBrandId() {
        return brandId;
    }
}
