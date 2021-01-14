package com.ecommerce.arolaz.Product.Service;

import com.ecommerce.arolaz.Product.Model.Product;
import com.ecommerce.arolaz.Product.Model.ProductDynamicQuery;
import com.ecommerce.arolaz.Product.Repository.ProductRepository;
import com.ecommerce.arolaz.Utils.ExceptionHandlers.ProductNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Page<Product> getCriteriaProductV1( ProductDynamicQuery productDynamicQuery){
        return new PageImpl<>(query(productDynamicQuery));
    }

    @Override
    public Page<Product> findByCategoryId(String categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId,pageable);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    //@Cacheable
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findByProductId(ObjectId productId) {
        Optional<Product> product = productRepository.findByProductId(productId);
        if(!product.isPresent()){
            throw new ProductNotFoundException(String.format("Product with '%s' not found !",productId.toString()));
        }
        return product;
    }

    @Override
    public void deleteProductById(ObjectId productId) {
        Optional<Product> product = findByProductId(productId);
        if(product.isPresent()){
            productRepository.delete(product.get());
        }
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
                query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
                query.with(Sort.by(Sort.Direction.DESC, productDynamicQuery.getSortBy()));
            }
            else{
                query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
            }
        }

        return mongoTemplate.find(query, Product.class);
    }
}
