package com.ecommerce.arolaz.Color.Model;

import com.ecommerce.arolaz.Product.Model.Product;
import com.querydsl.core.annotations.QueryEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@QueryEntity
@Document(collection = "colors")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId colorId;

    private String colorName;

    private List<Product> products;

    protected Color(){}

    public Color(String colorName, List<Product> products) {
        this.colorName = colorName;
        this.products = products;
    }

    public ObjectId getColorId() {
        return colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
