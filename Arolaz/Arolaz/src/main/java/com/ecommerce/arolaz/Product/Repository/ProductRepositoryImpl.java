package com.ecommerce.arolaz.Product.Repository;

import com.ecommerce.arolaz.Product.Model.Product;
import com.ecommerce.arolaz.Product.Model.ProductDynamicQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.MongoRegexCreator;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.query.parser.Part;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private MongoTemplate mongoTemplate;

    @Autowired
    public ProductRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Product> query(ProductDynamicQuery productDynamicQuery) {
        final Query query = new Query();
        final List<Criteria> criteria = new ArrayList<>();

        if(productDynamicQuery.getSizeName() != null){
            criteria.add(Criteria.where("basicSizeName").is(productDynamicQuery.getSizeName()));
        }

        if(productDynamicQuery.getProductName() != null){
            criteria.add(Criteria.where("productName").is(productDynamicQuery.getProductName()));
        }

        if(productDynamicQuery.getBrandName() != null){
            criteria.add(Criteria.where("brandName").is(productDynamicQuery.getBrandName()));
        }

        if(productDynamicQuery.getColorName() != null){
            criteria.add(Criteria.where("basicColorName").is(productDynamicQuery.getColorName()));
        }

        if(productDynamicQuery.getPrice() != null){
            criteria.add(Criteria.where("basicSmallSizePrice").is(productDynamicQuery.getPrice()));
        }

        if(!criteria.isEmpty()) {
            if(productDynamicQuery.getSortBy() != null){
                query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()]))).with(Sort.by(Sort.Direction.DESC, productDynamicQuery.getSortBy()));
            }
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        }

        return mongoTemplate.find(query, Product.class);
    }
}
