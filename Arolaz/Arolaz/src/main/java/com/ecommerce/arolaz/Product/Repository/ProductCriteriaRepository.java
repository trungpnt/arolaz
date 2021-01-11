//package com.ecommerce.arolaz.Product.Repository;
//
//import com.ecommerce.arolaz.Product.Model.Product;
//import com.ecommerce.arolaz.Product.Model.ProductPage;
//import com.ecommerce.arolaz.Product.Model.ProductSearchCriteria;
//import org.springframework.data.domain.*;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//@Repository
//public class ProductCriteriaRepository {
//    private final EntityManager entityManager;
//
//    private final CriteriaBuilder criteriaBuilder;
//
//    public ProductCriteriaRepository(EntityManager entityManager) {
//        this.entityManager = entityManager;
//        this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
//    }
//
//    public Page<Product> findAllWithFilters(ProductPage productPage, ProductSearchCriteria productSearchCriteria){
//        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
//        Root<Product> productRoot = criteriaQuery.from(Product.class);
//        Predicate predicate = getPredicate(productSearchCriteria, productRoot);
//        criteriaQuery.where(predicate);
//        setOrder(productPage, criteriaQuery, productRoot);
//
//        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
//        typedQuery.setFirstResult(productPage.getPageNumber() * productPage.getPageSize());
//        typedQuery.setMaxResults(productPage.getPageSize());
//
//        Pageable pageable = getPageable(productPage);
//
//        long productCount = getProductCount(predicate);
//
//        return new PageImpl<>(typedQuery.getResultList(), pageable, productCount);
//
//    }
//
//    private Predicate getPredicate(ProductSearchCriteria productSearchCriteria,
//                                   Root<Product> productRoot) {
//        List<Predicate> predicates = new ArrayList<>();
//        if(Objects.nonNull(productSearchCriteria.getProductName())){
//            predicates.add(
//                    criteriaBuilder.like(productRoot.get("productName"),
//                            "%" + productSearchCriteria.getProductName() + "%")
//            );
//        }
//        if(Objects.nonNull(productSearchCriteria.getBrandName())){
//            predicates.add(
//                    criteriaBuilder.like(productRoot.get("brandName"),
//                            "%" + productSearchCriteria.getBrandName() + "%")
//            );
//        }
//        if(Objects.nonNull(productSearchCriteria.getColorName())){
//            predicates.add(
//                    criteriaBuilder.like(productRoot.get("colorName"),
//                            "%" + productSearchCriteria.getColorName() + "%")
//            );
//        }
//        if(Objects.nonNull(productSearchCriteria.getPrice())){
//            predicates.add(
//                    criteriaBuilder.like(productRoot.get("productPrice"),
//                            "%" + productSearchCriteria.getBrandName() + "%")
//            );
//        }
//        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//    }
//
//    private void setOrder(ProductPage productPage,
//                          CriteriaQuery<Product> criteriaQuery,
//                          Root<Product> productRoot) {
//        if(productPage.getSortDirection().equals(Sort.Direction.ASC)){
//            criteriaQuery.orderBy(criteriaBuilder.asc(productRoot.get(productPage.getSortBy())));
//        } else {
//            criteriaQuery.orderBy(criteriaBuilder.desc(productRoot.get(productPage.getSortBy())));
//        }
//    }
//
//    private Pageable getPageable(ProductPage productPage) {
//        Sort sort = Sort.by(productPage.getSortDirection(), productPage.getSortBy());
//        return PageRequest.of(productPage.getPageNumber(),productPage.getPageSize(), sort);
//    }
//
//    private long getProductCount(Predicate predicate) {
//        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
//        Root<Product> countRoot = countQuery.from(Product.class);
//        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
//        return entityManager.createQuery(countQuery).getSingleResult();
//    }
//}
