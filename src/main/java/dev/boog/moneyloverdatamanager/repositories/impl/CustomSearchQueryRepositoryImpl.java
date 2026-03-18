package dev.boog.moneyloverdatamanager.repositories.impl;

import dev.boog.moneyloverdatamanager.repositories.CustomSearchQueryRepository;
import dev.boog.moneyloverdatamanager.utils.QueryHelper;
import dev.boog.moneyloverdatamanager.utils.models.Page;
import dev.boog.moneyloverdatamanager.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.utils.models.QueryResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class CustomSearchQueryRepositoryImpl<E> implements CustomSearchQueryRepository<E> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public QueryResult<E> findAll(Class<E> clazz, QueryRequest<E> queryRequest) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<E> query = cb.createQuery(clazz);
        Root<E> root = query.from(clazz);

        Predicate predicate = QueryHelper
                .buildSpecification(queryRequest)
                .toPredicate(root, query, cb);

        if (predicate != null) {
            query.where(predicate);
        }

        TypedQuery<E> typedQuery = em.createQuery(query);

        typedQuery.setFirstResult(queryRequest.resultFilters().page() * queryRequest.resultFilters().pageSize());
        typedQuery.setMaxResults(queryRequest.resultFilters().pageSize() + 1);

        List<E> results = typedQuery.getResultList();

        boolean hasNext = results != null && results.size() > queryRequest.resultFilters().pageSize();

        if (hasNext) {
            results = results.subList(0,  queryRequest.resultFilters().pageSize());
        }

        return QueryResult.<E>builder()
                .results(results)
                .page(Page
                        .builder()
                        .hasNext(hasNext)
                        .records(results != null ? results.size() : 0)
                        .build())
        .build();
    }

    @Override
    public QueryResult<Long> findAllAndSelectIds(Class<E> clazz, QueryRequest<E> queryRequest) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<E> root = query.from(clazz);

        Predicate predicate = QueryHelper
                .buildSpecification(queryRequest)
                .toPredicate(root, query, cb);

        if (predicate != null) {
            query.where(predicate);
        }

        query.select(root.get("id"));

        TypedQuery<Long> typedQuery = em.createQuery(query);

        typedQuery.setFirstResult(queryRequest.resultFilters().page() * queryRequest.resultFilters().pageSize());
        typedQuery.setMaxResults(queryRequest.resultFilters().pageSize() + 1);

        List<Long> results = typedQuery.getResultList();

        boolean hasNext = results != null && results.size() > queryRequest.resultFilters().pageSize();;

        if (hasNext) {
            results = results.subList(0,  queryRequest.resultFilters().pageSize());
        }

        return QueryResult.<Long>builder()
                .results(results)
                .page(Page
                        .builder()
                        .hasNext(hasNext)
                        .records(results != null ? results.size() : 0)
                        .build())
                .build();
    }

}
