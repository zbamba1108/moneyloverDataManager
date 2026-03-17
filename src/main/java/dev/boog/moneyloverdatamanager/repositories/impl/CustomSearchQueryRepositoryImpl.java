package dev.boog.moneyloverdatamanager.repositories.impl;

import dev.boog.moneyloverdatamanager.repositories.CustomSearchQueryRepository;
import dev.boog.moneyloverdatamanager.utils.QueryBuilder;
import dev.boog.moneyloverdatamanager.utils.QueryHelper;
import dev.boog.moneyloverdatamanager.utils.enums.QueryType;
import dev.boog.moneyloverdatamanager.utils.models.Page;
import dev.boog.moneyloverdatamanager.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.utils.models.QueryResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class CustomSearchQueryRepositoryImpl<E> implements CustomSearchQueryRepository<E> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public QueryResult<E> search(QueryRequest queryRequest) {
        final List<E> resultList;

        QueryBuilder queryBuilder = QueryBuilder.builder()
                .em(em)
                .clazz(queryRequest.getClazz())
                .userId(queryRequest.getUserId())
                .resultFilters(queryRequest.getResultFilters())
                .ids(queryRequest.getIds())
                .mapDetails(queryRequest.isMapDetails())
                .build();

        final boolean pagination = queryRequest.getResultFilters() != null
                && queryRequest.getResultFilters().getPageSize() != null
                && queryRequest.getResultFilters().getPage() != null;

        Page page = pagination ? getPage(queryRequest, queryBuilder) : null;

        queryBuilder.setPagination(pagination);

        boolean executeQueryToRetrieveIdsFirst = queryRequest.isMapDetails() && queryRequest.isHasChildren();

        if (executeQueryToRetrieveIdsFirst) {
            resultList = getResultListFromMatchingIds(queryRequest, queryBuilder);
        } else {
            resultList = getResultList(queryRequest, queryBuilder);
        }

        return new QueryResult<>(resultList, page);
    }

    @SuppressWarnings("unchecked")
    private static <E> List<E> getResultList(QueryRequest queryRequest, QueryBuilder queryBuilder) {
        queryBuilder.setReturnLong(false);
        return (List<E>) queryBuilder
                .initializeQuery(QueryHelper
                        .getStringBuilder(QueryType.RETRIEVE_ENTITY_LIST, queryRequest.getClazz().getSimpleName()))
                .addEntityGraph()
                .addIds(queryRequest.getIds())
                .addOptionalParam(queryRequest.getOptionalParams())
                .addDateRange()
                .addOrderBy(queryRequest.getResultFilters())
                .buildQuery()
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    private static <E> List<E> getResultListFromMatchingIds(QueryRequest queryRequest, QueryBuilder queryBuilder) {
        queryBuilder.setReturnLong(true);

        List<Long> matchingIds = (List<Long>) queryBuilder
                .initializeQuery(QueryHelper
                        .getStringBuilder(QueryType.RETRIEVE_IDS, queryRequest.getClazz().getSimpleName()))
                .addIds(queryRequest.getIds())
                .addOptionalParam(queryRequest.getOptionalParams())
                .addDateRange()
                .buildQuery()
                .getResultList();

        queryBuilder.setResultFilters(null);
        queryBuilder.setReturnLong(false);
        queryBuilder.setPagination(false);

        return (List<E>) queryBuilder
                .initializeQuery(QueryHelper
                        .getStringBuilder(QueryType.RETRIEVE_ENTITY_LIST, queryRequest.getClazz().getSimpleName()))
                .addEntityGraph()
                .addIds(matchingIds
                        .stream()
                        .map(Object::toString)
                        .toList())
                .addOrderBy(queryRequest.getResultFilters())
                .buildQuery()
                .getResultList();
    }

    private static Page getPage(QueryRequest queryRequest, QueryBuilder queryBuilder) {
        queryBuilder.setReturnLong(true);
        Long totalRecords = (Long) queryBuilder
                .initializeQuery(QueryHelper
                        .getStringBuilder(QueryType.COUNT, queryRequest.getClazz().getSimpleName()))
                .addIds(queryRequest.getIds())
                .addOptionalParam(queryRequest.getOptionalParams())
                .addDateRange()
                .buildQuery()
                .getSingleResult();

        boolean hasMore = totalRecords > (long) queryRequest.getResultFilters().getPageSize() * (queryRequest.getResultFilters().getPage() + 1);
        return new Page(totalRecords, hasMore);
    }

}
