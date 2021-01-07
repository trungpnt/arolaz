package com.ecommerce.arolaz.OrderDetails.Repository;

import com.ecommerce.arolaz.OrderDetails.Model.OrderDetails;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderDetailsRepository extends PagingAndSortingRepository<OrderDetails, ObjectId> {

    @Override
    Iterable<OrderDetails> findAll(Sort sort);

    Page<OrderDetails> findByOrderId(@Param("orderId") ObjectId orderId, Pageable pageable);

    @Override
    Page<OrderDetails> findAll(Pageable pageable);

    @Override
    <S extends OrderDetails> S save(S s);

    @Override
    <S extends OrderDetails> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    Optional<OrderDetails> findById(ObjectId objectId);

    @Override
    boolean existsById(ObjectId objectId);

    @Override
    Iterable<OrderDetails> findAll();

    @Override
    Iterable<OrderDetails> findAllById(Iterable<ObjectId> iterable);

    @Override
    long count();

    @Override
    void deleteById(ObjectId objectId);

    @Override
    void delete(OrderDetails orderDetails);

    @Override
    void deleteAll(Iterable<? extends OrderDetails> iterable);

    @Override
    void deleteAll();
}
