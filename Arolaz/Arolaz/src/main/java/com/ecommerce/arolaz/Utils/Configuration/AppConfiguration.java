package com.ecommerce.arolaz.Utils.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Metamodel;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Configuration
public class AppConfiguration {
    @Bean
    public EntityManager entityManagerConfig(){
        return new EntityManager() {
            @Override
            public void persist(Object o) {

            }

            @Override
            public <T> T merge(T t) {
                return null;
            }

            @Override
            public void remove(Object o) {

            }

            @Override
            public <T> T find(Class<T> aClass, Object o) {
                return null;
            }

            @Override
            public <T> T find(Class<T> aClass, Object o, Map<String, Object> map) {
                return null;
            }

            @Override
            public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType) {
                return null;
            }

            @Override
            public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType, Map<String, Object> map) {
                return null;
            }

            @Override
            public <T> T getReference(Class<T> aClass, Object o) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public void setFlushMode(FlushModeType flushModeType) {

            }

            @Override
            public FlushModeType getFlushMode() {
                return null;
            }

            @Override
            public void lock(Object o, LockModeType lockModeType) {

            }

            @Override
            public void lock(Object o, LockModeType lockModeType, Map<String, Object> map) {

            }

            @Override
            public void refresh(Object o) {

            }

            @Override
            public void refresh(Object o, Map<String, Object> map) {

            }

            @Override
            public void refresh(Object o, LockModeType lockModeType) {

            }

            @Override
            public void refresh(Object o, LockModeType lockModeType, Map<String, Object> map) {

            }

            @Override
            public void clear() {

            }

            @Override
            public void detach(Object o) {

            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public LockModeType getLockMode(Object o) {
                return null;
            }

            @Override
            public void setProperty(String s, Object o) {

            }

            @Override
            public Map<String, Object> getProperties() {
                return null;
            }

            @Override
            public Query createQuery(String s) {
                return null;
            }

            @Override
            public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
                return null;
            }

            @Override
            public <T> TypedQuery<T> createQuery(String s, Class<T> aClass) {
                return null;
            }

            @Override
            public Query createNamedQuery(String s) {
                return null;
            }

            @Override
            public <T> TypedQuery<T> createNamedQuery(String s, Class<T> aClass) {
                return null;
            }

            @Override
            public Query createNativeQuery(String s) {
                return null;
            }

            @Override
            public Query createNativeQuery(String s, Class aClass) {
                return null;
            }

            @Override
            public Query createNativeQuery(String s, String s1) {
                return null;
            }

            @Override
            public void joinTransaction() {

            }

            @Override
            public <T> T unwrap(Class<T> aClass) {
                return null;
            }

            @Override
            public Object getDelegate() {
                return null;
            }

            @Override
            public void close() {

            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public EntityTransaction getTransaction() {
                return null;
            }

            @Override
            public EntityManagerFactory getEntityManagerFactory() {
                return null;
            }

            @Override
            public CriteriaBuilder getCriteriaBuilder() {
                return null;
            }

            @Override
            public Metamodel getMetamodel() {
                return null;
            }
        };
    }

    @Bean
    public CriteriaBuilder criteriaBuilderConfig(){
        return new CriteriaBuilder() {
            @Override
            public CriteriaQuery<Object> createQuery() {
                return null;
            }

            @Override
            public <T> CriteriaQuery<T> createQuery(Class<T> aClass) {
                return null;
            }

            @Override
            public CriteriaQuery<Tuple> createTupleQuery() {
                return null;
            }

            @Override
            public <Y> CompoundSelection<Y> construct(Class<Y> aClass, Selection<?>... selections) {
                return null;
            }

            @Override
            public CompoundSelection<Tuple> tuple(Selection<?>... selections) {
                return null;
            }

            @Override
            public CompoundSelection<Object[]> array(Selection<?>... selections) {
                return null;
            }

            @Override
            public Order asc(Expression<?> expression) {
                return null;
            }

            @Override
            public Order desc(Expression<?> expression) {
                return null;
            }

            @Override
            public <N extends Number> Expression<Double> avg(Expression<N> expression) {
                return null;
            }

            @Override
            public <N extends Number> Expression<N> sum(Expression<N> expression) {
                return null;
            }

            @Override
            public Expression<Long> sumAsLong(Expression<Integer> expression) {
                return null;
            }

            @Override
            public Expression<Double> sumAsDouble(Expression<Float> expression) {
                return null;
            }

            @Override
            public <N extends Number> Expression<N> max(Expression<N> expression) {
                return null;
            }

            @Override
            public <N extends Number> Expression<N> min(Expression<N> expression) {
                return null;
            }

            @Override
            public <X extends Comparable<? super X>> Expression<X> greatest(Expression<X> expression) {
                return null;
            }

            @Override
            public <X extends Comparable<? super X>> Expression<X> least(Expression<X> expression) {
                return null;
            }

            @Override
            public Expression<Long> count(Expression<?> expression) {
                return null;
            }

            @Override
            public Expression<Long> countDistinct(Expression<?> expression) {
                return null;
            }

            @Override
            public Predicate exists(Subquery<?> subquery) {
                return null;
            }

            @Override
            public <Y> Expression<Y> all(Subquery<Y> subquery) {
                return null;
            }

            @Override
            public <Y> Expression<Y> some(Subquery<Y> subquery) {
                return null;
            }

            @Override
            public <Y> Expression<Y> any(Subquery<Y> subquery) {
                return null;
            }

            @Override
            public Predicate and(Expression<Boolean> expression, Expression<Boolean> expression1) {
                return null;
            }

            @Override
            public Predicate and(Predicate... predicates) {
                return null;
            }

            @Override
            public Predicate or(Expression<Boolean> expression, Expression<Boolean> expression1) {
                return null;
            }

            @Override
            public Predicate or(Predicate... predicates) {
                return null;
            }

            @Override
            public Predicate not(Expression<Boolean> expression) {
                return null;
            }

            @Override
            public Predicate conjunction() {
                return null;
            }

            @Override
            public Predicate disjunction() {
                return null;
            }

            @Override
            public Predicate isTrue(Expression<Boolean> expression) {
                return null;
            }

            @Override
            public Predicate isFalse(Expression<Boolean> expression) {
                return null;
            }

            @Override
            public Predicate isNull(Expression<?> expression) {
                return null;
            }

            @Override
            public Predicate isNotNull(Expression<?> expression) {
                return null;
            }

            @Override
            public Predicate equal(Expression<?> expression, Expression<?> expression1) {
                return null;
            }

            @Override
            public Predicate equal(Expression<?> expression, Object o) {
                return null;
            }

            @Override
            public Predicate notEqual(Expression<?> expression, Expression<?> expression1) {
                return null;
            }

            @Override
            public Predicate notEqual(Expression<?> expression, Object o) {
                return null;
            }

            @Override
            public <Y extends Comparable<? super Y>> Predicate greaterThan(Expression<? extends Y> expression, Expression<? extends Y> expression1) {
                return null;
            }

            @Override
            public <Y extends Comparable<? super Y>> Predicate greaterThan(Expression<? extends Y> expression, Y y) {
                return null;
            }

            @Override
            public <Y extends Comparable<? super Y>> Predicate greaterThanOrEqualTo(Expression<? extends Y> expression, Expression<? extends Y> expression1) {
                return null;
            }

            @Override
            public <Y extends Comparable<? super Y>> Predicate greaterThanOrEqualTo(Expression<? extends Y> expression, Y y) {
                return null;
            }

            @Override
            public <Y extends Comparable<? super Y>> Predicate lessThan(Expression<? extends Y> expression, Expression<? extends Y> expression1) {
                return null;
            }

            @Override
            public <Y extends Comparable<? super Y>> Predicate lessThan(Expression<? extends Y> expression, Y y) {
                return null;
            }

            @Override
            public <Y extends Comparable<? super Y>> Predicate lessThanOrEqualTo(Expression<? extends Y> expression, Expression<? extends Y> expression1) {
                return null;
            }

            @Override
            public <Y extends Comparable<? super Y>> Predicate lessThanOrEqualTo(Expression<? extends Y> expression, Y y) {
                return null;
            }

            @Override
            public <Y extends Comparable<? super Y>> Predicate between(Expression<? extends Y> expression, Expression<? extends Y> expression1, Expression<? extends Y> expression2) {
                return null;
            }

            @Override
            public <Y extends Comparable<? super Y>> Predicate between(Expression<? extends Y> expression, Y y, Y y1) {
                return null;
            }

            @Override
            public Predicate gt(Expression<? extends Number> expression, Expression<? extends Number> expression1) {
                return null;
            }

            @Override
            public Predicate gt(Expression<? extends Number> expression, Number number) {
                return null;
            }

            @Override
            public Predicate ge(Expression<? extends Number> expression, Expression<? extends Number> expression1) {
                return null;
            }

            @Override
            public Predicate ge(Expression<? extends Number> expression, Number number) {
                return null;
            }

            @Override
            public Predicate lt(Expression<? extends Number> expression, Expression<? extends Number> expression1) {
                return null;
            }

            @Override
            public Predicate lt(Expression<? extends Number> expression, Number number) {
                return null;
            }

            @Override
            public Predicate le(Expression<? extends Number> expression, Expression<? extends Number> expression1) {
                return null;
            }

            @Override
            public Predicate le(Expression<? extends Number> expression, Number number) {
                return null;
            }

            @Override
            public <N extends Number> Expression<N> neg(Expression<N> expression) {
                return null;
            }

            @Override
            public <N extends Number> Expression<N> abs(Expression<N> expression) {
                return null;
            }

            @Override
            public <N extends Number> Expression<N> sum(Expression<? extends N> expression, Expression<? extends N> expression1) {
                return null;
            }

            @Override
            public <N extends Number> Expression<N> sum(Expression<? extends N> expression, N n) {
                return null;
            }

            @Override
            public <N extends Number> Expression<N> sum(N n, Expression<? extends N> expression) {
                return null;
            }

            @Override
            public <N extends Number> Expression<N> prod(Expression<? extends N> expression, Expression<? extends N> expression1) {
                return null;
            }

            @Override
            public <N extends Number> Expression<N> prod(Expression<? extends N> expression, N n) {
                return null;
            }

            @Override
            public <N extends Number> Expression<N> prod(N n, Expression<? extends N> expression) {
                return null;
            }

            @Override
            public <N extends Number> Expression<N> diff(Expression<? extends N> expression, Expression<? extends N> expression1) {
                return null;
            }

            @Override
            public <N extends Number> Expression<N> diff(Expression<? extends N> expression, N n) {
                return null;
            }

            @Override
            public <N extends Number> Expression<N> diff(N n, Expression<? extends N> expression) {
                return null;
            }

            @Override
            public Expression<Number> quot(Expression<? extends Number> expression, Expression<? extends Number> expression1) {
                return null;
            }

            @Override
            public Expression<Number> quot(Expression<? extends Number> expression, Number number) {
                return null;
            }

            @Override
            public Expression<Number> quot(Number number, Expression<? extends Number> expression) {
                return null;
            }

            @Override
            public Expression<Integer> mod(Expression<Integer> expression, Expression<Integer> expression1) {
                return null;
            }

            @Override
            public Expression<Integer> mod(Expression<Integer> expression, Integer integer) {
                return null;
            }

            @Override
            public Expression<Integer> mod(Integer integer, Expression<Integer> expression) {
                return null;
            }

            @Override
            public Expression<Double> sqrt(Expression<? extends Number> expression) {
                return null;
            }

            @Override
            public Expression<Long> toLong(Expression<? extends Number> expression) {
                return null;
            }

            @Override
            public Expression<Integer> toInteger(Expression<? extends Number> expression) {
                return null;
            }

            @Override
            public Expression<Float> toFloat(Expression<? extends Number> expression) {
                return null;
            }

            @Override
            public Expression<Double> toDouble(Expression<? extends Number> expression) {
                return null;
            }

            @Override
            public Expression<BigDecimal> toBigDecimal(Expression<? extends Number> expression) {
                return null;
            }

            @Override
            public Expression<BigInteger> toBigInteger(Expression<? extends Number> expression) {
                return null;
            }

            @Override
            public Expression<String> toString(Expression<Character> expression) {
                return null;
            }

            @Override
            public <T> Expression<T> literal(T t) {
                return null;
            }

            @Override
            public <T> Expression<T> nullLiteral(Class<T> aClass) {
                return null;
            }

            @Override
            public <T> ParameterExpression<T> parameter(Class<T> aClass) {
                return null;
            }

            @Override
            public <T> ParameterExpression<T> parameter(Class<T> aClass, String s) {
                return null;
            }

            @Override
            public <C extends Collection<?>> Predicate isEmpty(Expression<C> expression) {
                return null;
            }

            @Override
            public <C extends Collection<?>> Predicate isNotEmpty(Expression<C> expression) {
                return null;
            }

            @Override
            public <C extends Collection<?>> Expression<Integer> size(Expression<C> expression) {
                return null;
            }

            @Override
            public <C extends Collection<?>> Expression<Integer> size(C objects) {
                return null;
            }

            @Override
            public <E, C extends Collection<E>> Predicate isMember(Expression<E> expression, Expression<C> expression1) {
                return null;
            }

            @Override
            public <E, C extends Collection<E>> Predicate isMember(E e, Expression<C> expression) {
                return null;
            }

            @Override
            public <E, C extends Collection<E>> Predicate isNotMember(Expression<E> expression, Expression<C> expression1) {
                return null;
            }

            @Override
            public <E, C extends Collection<E>> Predicate isNotMember(E e, Expression<C> expression) {
                return null;
            }

            @Override
            public <V, M extends Map<?, V>> Expression<Collection<V>> values(M m) {
                return null;
            }

            @Override
            public <K, M extends Map<K, ?>> Expression<Set<K>> keys(M m) {
                return null;
            }

            @Override
            public Predicate like(Expression<String> expression, Expression<String> expression1) {
                return null;
            }

            @Override
            public Predicate like(Expression<String> expression, String s) {
                return null;
            }

            @Override
            public Predicate like(Expression<String> expression, Expression<String> expression1, Expression<Character> expression2) {
                return null;
            }

            @Override
            public Predicate like(Expression<String> expression, Expression<String> expression1, char c) {
                return null;
            }

            @Override
            public Predicate like(Expression<String> expression, String s, Expression<Character> expression1) {
                return null;
            }

            @Override
            public Predicate like(Expression<String> expression, String s, char c) {
                return null;
            }

            @Override
            public Predicate notLike(Expression<String> expression, Expression<String> expression1) {
                return null;
            }

            @Override
            public Predicate notLike(Expression<String> expression, String s) {
                return null;
            }

            @Override
            public Predicate notLike(Expression<String> expression, Expression<String> expression1, Expression<Character> expression2) {
                return null;
            }

            @Override
            public Predicate notLike(Expression<String> expression, Expression<String> expression1, char c) {
                return null;
            }

            @Override
            public Predicate notLike(Expression<String> expression, String s, Expression<Character> expression1) {
                return null;
            }

            @Override
            public Predicate notLike(Expression<String> expression, String s, char c) {
                return null;
            }

            @Override
            public Expression<String> concat(Expression<String> expression, Expression<String> expression1) {
                return null;
            }

            @Override
            public Expression<String> concat(Expression<String> expression, String s) {
                return null;
            }

            @Override
            public Expression<String> concat(String s, Expression<String> expression) {
                return null;
            }

            @Override
            public Expression<String> substring(Expression<String> expression, Expression<Integer> expression1) {
                return null;
            }

            @Override
            public Expression<String> substring(Expression<String> expression, int i) {
                return null;
            }

            @Override
            public Expression<String> substring(Expression<String> expression, Expression<Integer> expression1, Expression<Integer> expression2) {
                return null;
            }

            @Override
            public Expression<String> substring(Expression<String> expression, int i, int i1) {
                return null;
            }

            @Override
            public Expression<String> trim(Expression<String> expression) {
                return null;
            }

            @Override
            public Expression<String> trim(Trimspec trimspec, Expression<String> expression) {
                return null;
            }

            @Override
            public Expression<String> trim(Expression<Character> expression, Expression<String> expression1) {
                return null;
            }

            @Override
            public Expression<String> trim(Trimspec trimspec, Expression<Character> expression, Expression<String> expression1) {
                return null;
            }

            @Override
            public Expression<String> trim(char c, Expression<String> expression) {
                return null;
            }

            @Override
            public Expression<String> trim(Trimspec trimspec, char c, Expression<String> expression) {
                return null;
            }

            @Override
            public Expression<String> lower(Expression<String> expression) {
                return null;
            }

            @Override
            public Expression<String> upper(Expression<String> expression) {
                return null;
            }

            @Override
            public Expression<Integer> length(Expression<String> expression) {
                return null;
            }

            @Override
            public Expression<Integer> locate(Expression<String> expression, Expression<String> expression1) {
                return null;
            }

            @Override
            public Expression<Integer> locate(Expression<String> expression, String s) {
                return null;
            }

            @Override
            public Expression<Integer> locate(Expression<String> expression, Expression<String> expression1, Expression<Integer> expression2) {
                return null;
            }

            @Override
            public Expression<Integer> locate(Expression<String> expression, String s, int i) {
                return null;
            }

            @Override
            public Expression<Date> currentDate() {
                return null;
            }

            @Override
            public Expression<Timestamp> currentTimestamp() {
                return null;
            }

            @Override
            public Expression<Time> currentTime() {
                return null;
            }

            @Override
            public <T> In<T> in(Expression<? extends T> expression) {
                return null;
            }

            @Override
            public <Y> Expression<Y> coalesce(Expression<? extends Y> expression, Expression<? extends Y> expression1) {
                return null;
            }

            @Override
            public <Y> Expression<Y> coalesce(Expression<? extends Y> expression, Y y) {
                return null;
            }

            @Override
            public <Y> Expression<Y> nullif(Expression<Y> expression, Expression<?> expression1) {
                return null;
            }

            @Override
            public <Y> Expression<Y> nullif(Expression<Y> expression, Y y) {
                return null;
            }

            @Override
            public <T> Coalesce<T> coalesce() {
                return null;
            }

            @Override
            public <C, R> SimpleCase<C, R> selectCase(Expression<? extends C> expression) {
                return null;
            }

            @Override
            public <R> Case<R> selectCase() {
                return null;
            }

            @Override
            public <T> Expression<T> function(String s, Class<T> aClass, Expression<?>... expressions) {
                return null;
            }
        };
    }
}
