package dev.boog.moneyloverdatamanager.repositories.impl;

import dev.boog.moneyloverdatamanager.repositories.CustomSearchQueryRepository;
import dev.boog.moneyloverdatamanager.utils.*;
import dev.boog.moneyloverdatamanager.utils.enums.StringBuilderType;
import dev.boog.moneyloverdatamanager.utils.mappers.models.Page;
import dev.boog.moneyloverdatamanager.utils.mappers.models.QueryResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.*;

public class CustomSearchQueryRepositoryImpl<E> implements CustomSearchQueryRepository<E> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public QueryResult<E> searchByUserId(Class<E> clazz, String userId, ResultFilters resultFilters, boolean mapDetails, boolean hasChildren) {
        return search(clazz, userId, null, null, resultFilters, mapDetails, hasChildren);
    }

    @Override
    public QueryResult<E> searchByUserIdAndIds(Class<E> clazz, String userId, List<String> ids, ResultFilters resultFilters, boolean mapDetails, boolean hasChildren) {
        return search(clazz, userId, ids, null, resultFilters, mapDetails, hasChildren);
    }

    @Override
    public QueryResult<E> searchByUserIdAndOptionalParams(Class<E> clazz, String userId, HashMap<String, String> optionalParams, ResultFilters resultFilters, boolean mapDetails, boolean hasChildren) {
        return search(clazz, userId, null, optionalParams, resultFilters, mapDetails, hasChildren);
    }

    @Override
    public QueryResult<E> searchByUserIdAndIdsAndOptionalParams(Class<E> clazz, String userId, List<String> ids, HashMap<String, String> optionalParams, ResultFilters resultFilters, boolean mapDetails, boolean hasChildren) {
        return search(clazz, userId, ids, optionalParams, resultFilters, mapDetails, hasChildren);
    }

    @Override
    public QueryResult<E> search(Class<E> clazz, String userId, List<String> ids, HashMap<String, String> optionalParams, ResultFilters resultFilters, boolean mapDetails, boolean hasChildren) {
        List<E> resultList;

        Page page = null;
        if (resultFilters != null && resultFilters.getPageSize() != null && resultFilters.getPage() != null) {
            Long totalRecords = (Long) new QueryBuilder<>(em,
                                                          clazz,
                                                          QueryHelper.getStringBuilder(StringBuilderType.COUNT, clazz.getSimpleName()),
                                                          userId,
                                                          resultFilters,
                                                          mapDetails,
                                                true)
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

        if (mapDetails && hasChildren) {
            List<Long> matchingIds = (List<Long>) new QueryBuilder<>(em,
                                                                     clazz,
                                                                     QueryHelper.getStringBuilder(StringBuilderType.RETRIEVE_IDS, clazz.getSimpleName()),
                                                                     userId,
                                                                     resultFilters,
                                                                     mapDetails,
                                                           true)
                    //.addEntityGraph()
                    .addIds(ids)
                    .addOptionalParam(optionalParams)
                    .addDateRange()
                    .createQuery()
                    .addPaginationParam(resultFilters)
                    .build()
                    .getResultList();

            resultList = (List<E>) new QueryBuilder<>(em,
                                                      clazz,
                                                      QueryHelper.getStringBuilder(StringBuilderType.COMPLETE_QUERY, clazz.getSimpleName()),
                                                      userId,
                                            null,
                                                      mapDetails,
                                            false)
                    .addEntityGraph()
                    .addIds(matchingIds
                            .stream()
                            .map(Object::toString)
                            .toList())
                    .createQuery()
                    .build()
                    .getResultList();
        } else {
            resultList = (List<E>) new QueryBuilder<>(em,
                                                      clazz,
                                                      QueryHelper.getStringBuilder(StringBuilderType.COMPLETE_QUERY, clazz.getSimpleName()),
                                                      userId,
                                                      resultFilters,
                                                      mapDetails,
                                            false)
                    .addEntityGraph()
                    .addIds(ids)
                    .addOptionalParam(optionalParams)
                    .addDateRange()
                    .createQuery()
                    .addPaginationParam(resultFilters)
                    .build()
                    .getResultList();
        }

        return new QueryResult<>(resultList, page);
    }

}
