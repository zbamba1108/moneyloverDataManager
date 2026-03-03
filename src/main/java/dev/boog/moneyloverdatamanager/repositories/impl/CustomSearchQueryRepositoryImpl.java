package dev.boog.moneyloverdatamanager.repositories.impl;

import dev.boog.moneyloverdatamanager.repositories.CustomSearchQueryRepository;
import dev.boog.moneyloverdatamanager.utils.*;
import dev.boog.moneyloverdatamanager.utils.mappers.models.Page;
import dev.boog.moneyloverdatamanager.utils.mappers.models.QueryResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.*;

public class CustomSearchQueryRepositoryImpl<E> implements CustomSearchQueryRepository<E> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public QueryResult<E> searchByUserId(Class<E> clazz, String userId, ResultFilters resultFilters, boolean mapDetails) {
        return search(clazz, userId, null, null, resultFilters, mapDetails);
    }

    @Override
    public QueryResult<E> searchByUserIdAndIds(Class<E> clazz, String userId, List<String> ids, ResultFilters resultFilters, boolean mapDetails) {
        return search(clazz, userId, ids, null, resultFilters, mapDetails);
    }

    @Override
    public QueryResult<E> searchByUserIdAndOptionalParams(Class<E> clazz, String userId, HashMap<String, String> optionalParams, ResultFilters resultFilters, boolean mapDetails) {
        return search(clazz, userId, null, optionalParams, resultFilters, mapDetails);
    }

    @Override
    public QueryResult<E> searchByUserIdAndIdsAndOptionalParams(Class<E> clazz, String userId, List<String> ids, HashMap<String, String> optionalParams, ResultFilters resultFilters, boolean mapDetails) {
        return search(clazz, userId, ids, optionalParams, resultFilters, mapDetails);
    }

    @Override
    public QueryResult<E> search(Class<E> clazz, String userId, List<String> ids, HashMap<String, String> optionalParams, ResultFilters resultFilters, boolean mapDetails) {
        Page page = null;
        if (resultFilters != null && resultFilters.getPageSize() != null && resultFilters.getPage() != null) {
            Long totalRecords = (Long) new QueryBuilder<>(em, clazz, userId, resultFilters, mapDetails, true)
                    .addEntityGraph()
                    .addIds(ids)
                    .addOptionalParam(optionalParams)
                    .addDateRange()
                    .createQuery()
                    .build()
                    .getSingleResult();

            boolean hasMore = totalRecords > (long) resultFilters.getPageSize() * (resultFilters.getPage() + 1);
            page = new Page(totalRecords, hasMore);
        }

        List<E> resultList = (List<E>) new QueryBuilder<>(em, clazz, userId, resultFilters, mapDetails, false)
                .addEntityGraph()
                .addIds(ids)
                .addOptionalParam(optionalParams)
                .addDateRange()
                .createQuery()
                .addPaginationParam(resultFilters)
                .build()
                .getResultList();

        return new QueryResult<>(resultList, page);
    }

    /*public QueryResult<E> search(Class<E> clazz, String userId, List<String> ids, HashMap<String, String> optionalParams, ResultFilters resultFilters, boolean mapDetails) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<E> cr = cb.createQuery(clazz);

        Root<E> root = cr.from(clazz);
        Predicate[] predicates = new Predicate[2];

        predicates[0] = cb.equal(root.get("user.id"), userId);

        cr.select(root).where(predicates);

        //cr.where(cb.equal(root.get("user_id"), Long.parseLong(userId)));

        cr.where(root.get("id").in(ids));

        for (Map.Entry<String, String> entry : optionalParams.entrySet()) {
            cr.where(cb.equal(root.get(QueryHelper.capitalizeProperty(entry.getKey())), entry.getValue()));
        }

        optionalParams.forEach((k,v) -> {
            cr.where(cb.equal(root.get(QueryHelper.capitalizeProperty(k)), v));
        });

        cr.where(cb.ge(root.get("createdAt"), Long
                .parseLong(resultFilters.getDateRange()[0])));

        cr.where(cb.le(root.get("createdAt"), Long
                .parseLong(resultFilters.getDateRange()[1])));


        TypedQuery<E> query = em.createQuery(cr);

        List<E> resultList = query.getResultList();


        return new QueryResult<>(resultList, new Page(100, true));
    }*/

}
