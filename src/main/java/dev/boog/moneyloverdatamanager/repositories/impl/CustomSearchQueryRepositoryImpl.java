package dev.boog.moneyloverdatamanager.repositories.impl;

import dev.boog.moneyloverdatamanager.repositories.CustomSearchQueryRepository;
import dev.boog.moneyloverdatamanager.repositories.utils.SearchQueryHelper;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class CustomSearchQueryRepositoryImpl<E> implements CustomSearchQueryRepository<E> {

    private final EntityManager em;

    public CustomSearchQueryRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public QueryResult<E> findAll(QueryRequest<E> queryRequest) {
        Class<E> entityClass = queryRequest.entityClass();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<E> query = cb.createQuery(entityClass);
        Root<E> root = query.from(entityClass);

        SearchQueryHelper.addPredicate(query, queryRequest, root, cb);

        TypedQuery<E> typedQuery = em.createQuery(query);

        SearchQueryHelper.paginateQuery(typedQuery, queryRequest);
        List<E> results = typedQuery.getResultList();

        return SearchQueryHelper.buildQueryResult(results, queryRequest);
    }

    @Override
    public QueryResult<Long> findAllAndSelectIds(QueryRequest<E> queryRequest) {
        Class<E> entityClass = queryRequest.entityClass();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<E> root = query.from(entityClass);

        SearchQueryHelper.addPredicate(query, queryRequest, root, cb);

        query.select(root.get("id"));

        TypedQuery<Long> typedQuery = em.createQuery(query);

        SearchQueryHelper.paginateQuery(typedQuery, queryRequest);

        return SearchQueryHelper.buildQueryResult(
                typedQuery.getResultList(), queryRequest);
    }

}
