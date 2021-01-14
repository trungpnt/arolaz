package com.ecommerce.arolaz.Product.Repository;

import com.ecommerce.arolaz.Product.Model.Product;
import com.ecommerce.arolaz.Product.Model.ProductDynamicQuery;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, ObjectId> {

    Page<Product> findByCategoryId(String categoryId, Pageable pageable);
    Optional<Product> findByProductId(ObjectId productId);
    Page<Product> findAll(Pageable pageable);
    @Override
    <S extends Product> S save(S s);

    @Override
    <S extends Product> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    Optional<Product> findById(ObjectId objectId);

    @Override
    boolean existsById(ObjectId objectId);

    @Override
    Iterable<Product> findAll();

    @Override
    Iterable<Product> findAllById(Iterable<ObjectId> iterable);

    @Override
    long count();

    @Override
    void deleteById(ObjectId objectId);

    @Override
    void delete(Product product);

    @Override
    void deleteAll(Iterable<? extends Product> iterable);

    @Override
    void deleteAll();
}
