package com.ecommerce.arolaz.SecurityUser.Repository;

import com.ecommerce.arolaz.SecurityUser.Model.SecurityUser;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityUserRepository extends PagingAndSortingRepository<SecurityUser, ObjectId> {
    Optional<SecurityUser> findByEmail(String email);
    Optional<SecurityUser> findByPhoneNumber(String phone);
    Optional<SecurityUser> findByFullName(String fullName);

    @Override
    Iterable<SecurityUser> findAll(Sort sort);

    Page<SecurityUser> findAll(Pageable pageable);

    @Override
    <S extends SecurityUser> S save(S s);

    @Override
    <S extends SecurityUser> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    Optional<SecurityUser> findById(ObjectId objectId);

    @Override
    boolean existsById(ObjectId objectId);

    @Override
    Iterable<SecurityUser> findAll();

    @Override
    Iterable<SecurityUser> findAllById(Iterable<ObjectId> iterable);

    @Override
    long count();

    @Override
    void deleteById(ObjectId objectId);

    @Override
    void delete(SecurityUser securityUser);

    @Override
    void deleteAll(Iterable<? extends SecurityUser> iterable);

    @Override
    void deleteAll();
}
