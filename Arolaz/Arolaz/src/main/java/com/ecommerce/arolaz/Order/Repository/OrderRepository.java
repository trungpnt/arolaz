package com.ecommerce.arolaz.Order.Repository;

import com.ecommerce.arolaz.Order.Model.Order;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order,ObjectId> {

    Page<Order> findBySecurityUserId(@Param("securityUserId") String securityUserId, Pageable pageable);
    Page<Order> findByUnregisteredUserId(@Param("unregisteredUserId") String unregisteredUserId, Pageable pageable);
    @Override
    Iterable<Order> findAll(Sort sort);

    @Override
    Page<Order> findAll(Pageable pageable);

    @Override
    <S extends Order> S save(S s);

    @Override
    <S extends Order> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    Optional<Order> findById(ObjectId objectId);

    @Override
    boolean existsById(ObjectId objectId);

    @Override
    Iterable<Order> findAll();

    @Override
    Iterable<Order> findAllById(Iterable<ObjectId> iterable);

    @Override
    long count();

    @Override
    void deleteById(ObjectId objectId);

    @Override
    void delete(Order order);

    @Override
    void deleteAll(Iterable<? extends Order> iterable);

    @Override
    void deleteAll();
}
