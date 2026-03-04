package dev.boog.moneyloverdatamanager.repositories.impl;

import dev.boog.moneyloverdatamanager.repositories.CustomSearchQueryRepository;
import dev.boog.moneyloverdatamanager.utils.*;
import dev.boog.moneyloverdatamanager.utils.enums.StringBuilderType;
import dev.boog.moneyloverdatamanager.utils.mappers.models.Page;
import dev.boog.moneyloverdatamanager.utils.mappers.models.QueryRequest;
import dev.boog.moneyloverdatamanager.utils.mappers.models.QueryResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.*;

public class CustomSearchQueryRepositoryImpl<E> implements CustomSearchQueryRepository<E> {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public QueryResult<E> search(QueryRequest queryRequest) {

        final List<E> resultList;
        Page page = null;

        final boolean count = queryRequest.getResultFilters() != null && queryRequest.getResultFilters().getPageSize() != null && queryRequest.getResultFilters().getPage() != null;
        if (count) {
            Long totalRecords = (Long) new QueryBuilder<>(em,
                                                          queryRequest.getClazz(),
                                                          QueryHelper.getStringBuilder(StringBuilderType.COUNT, queryRequest.getClazz().getSimpleName()),
                                                          queryRequest.getUserId(),
                                                          queryRequest.getResultFilters(),
                                                          queryRequest.isMapDetails(),
                                                true)
                    .addEntityGraph()
                    .addIds(queryRequest.getIds())
                    .addOptionalParam(queryRequest.getOptionalParams())
                    .addDateRange()
                    .createQuery()
                    .build()
                    .getSingleResult();

            boolean hasMore = totalRecords > (long) queryRequest.getResultFilters().getPageSize() * (queryRequest.getResultFilters().getPage() + 1);
            page = new Page(totalRecords, hasMore);
        }

        if (queryRequest.isMapDetails() && queryRequest.isHasChildren()) {
            List<Long> matchingIds = (List<Long>) new QueryBuilder<>(em,
                                                                     queryRequest.getClazz(),
                                                                     QueryHelper.getStringBuilder(StringBuilderType.RETRIEVE_IDS, queryRequest.getClazz().getSimpleName()),
                                                                     queryRequest.getUserId(),
                                                                     queryRequest.getResultFilters(),
                                                                     queryRequest.isMapDetails(),
                                                           true)
                    .addIds(queryRequest.getIds())
                    .addOptionalParam(queryRequest.getOptionalParams())
                    .addDateRange()
                    .createQuery()
                    .addPaginationParam(queryRequest.getResultFilters())
                    .build()
                    .getResultList();

            resultList = (List<E>) new QueryBuilder<>(em,
                                                      queryRequest.getClazz(),
                                                      QueryHelper.getStringBuilder(StringBuilderType.RETRIEVE_ENTITY_LIST, queryRequest.getClazz().getSimpleName()),
                                                      queryRequest.getUserId(),
                                            null,
                                                      queryRequest.isMapDetails(),
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
                                                      queryRequest.getClazz(),
                                                      QueryHelper.getStringBuilder(StringBuilderType.RETRIEVE_ENTITY_LIST, queryRequest.getClazz().getSimpleName()),
                                                      queryRequest.getUserId(),
                                                      queryRequest.getResultFilters(),
                                                      queryRequest.isMapDetails(),
                                            false)
                    .addEntityGraph()
                    .addIds(queryRequest.getIds())
                    .addOptionalParam(queryRequest.getOptionalParams())
                    .addDateRange()
                    .createQuery()
                    .addPaginationParam(queryRequest.getResultFilters())
                    .build()
                    .getResultList();
        }

        return new QueryResult<>(resultList, page);
    }

}
