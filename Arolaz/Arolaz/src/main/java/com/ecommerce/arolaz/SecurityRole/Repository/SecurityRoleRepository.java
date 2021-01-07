package com.ecommerce.arolaz.SecurityRole.Repository;

import com.ecommerce.arolaz.SecurityRole.Model.SecurityRole;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityRoleRepository extends PagingAndSortingRepository<SecurityRole, ObjectId> {
    SecurityRole findByRoleName(/*@Param("roleName")*/ String roleName);

    @Override
    Iterable<SecurityRole> findAll(Sort sort);

    @Override
    Page<SecurityRole> findAll(Pageable pageable);

    @Override
    <S extends SecurityRole> S save(S s);

    @Override
    <S extends SecurityRole> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    Optional<SecurityRole> findById(ObjectId objectId);

    @Override
    boolean existsById(ObjectId objectId);

    @Override
    Iterable<SecurityRole> findAll();

    @Override
    Iterable<SecurityRole> findAllById(Iterable<ObjectId> iterable);

    @Override
    long count();

    @Override
    void deleteById(ObjectId objectId);

    @Override
    void delete(SecurityRole securityRole);

    @Override
    void deleteAll(Iterable<? extends SecurityRole> iterable);

    @Override
    void deleteAll();
}
