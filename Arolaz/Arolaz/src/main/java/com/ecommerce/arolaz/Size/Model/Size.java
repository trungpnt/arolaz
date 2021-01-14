package com.ecommerce.arolaz.Size.Model;


import com.querydsl.core.annotations.QueryEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@QueryEntity
@Document(collection = "sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    ObjectId sizeId;

    private String sizeName;

    public Size(){}

    public Size(String sizeName) {
        this.sizeName = sizeName;
    }

    public ObjectId getSizeId() {
        return sizeId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
}
