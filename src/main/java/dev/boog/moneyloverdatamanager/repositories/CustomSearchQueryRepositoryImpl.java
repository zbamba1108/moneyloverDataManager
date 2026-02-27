package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.utils.*;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.sql.*;
import java.util.*;

public class CustomSearchQueryRepositoryImpl<E> implements CustomSearchQueryRepository<E> {

    private static final String HINT_NAME_FETCHGRAPH = "jakarta.persistence.fetchgraph";

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<E> searchByUserId(Class<E> clazz, String userId, ResultFilters resultFilters) {
        return search(clazz, userId, null, null, resultFilters);
    }

    @Override
    public List<E> searchByUserIdAndIds(Class<E> clazz, String userId, List<String> ids, ResultFilters resultFilters) {
        return search(clazz, userId, ids, null, resultFilters);
    }

    @Override
    public List<E> searchByUserIdAndOptionalParams(Class<E> clazz, String userId, HashMap<String, String> optionalParams, ResultFilters resultFilters) {
        return search(clazz, userId, null, optionalParams, resultFilters);
    }

    @Override
    public List<E> searchByUserIdAndIdsAndOptionalParams(Class<E> clazz, String userId, List<String> ids, HashMap<String, String> optionalParams, ResultFilters resultFilters) {
        return search(clazz, userId, ids, optionalParams, resultFilters);
    }

    @Override
    public List<E> search(Class<E> clazz, String userId, List<String> ids, HashMap<String, String> optionalParams, ResultFilters resultFilters) {
        String classSimpleName = clazz.getSimpleName();

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT e from ")
                .append(classSimpleName)
                .append(" e where e.user.id = :userId");

        HashMap<String, String> remappedOptionalParams = null;

        if (ids != null && !ids.isEmpty()) {
            sb.append(" AND e.id in (:ids)");
        }

        if (optionalParams != null && !optionalParams.isEmpty()) {
            remappedOptionalParams = new HashMap<>();
            sb.append(" AND ")
                    .append(QueryHelper
                            .buildQueryAndCreateQueryParam(
                                    sb,
                                    optionalParams,
                                    remappedOptionalParams
                            )
                    );
        }

        String[] dateRange = null;

        if (resultFilters != null
                && resultFilters.getDateRange() != null
                && resultFilters.getDateRange().length == 2) {
            sb.append(" AND e.createdAt >= :startDate AND e.createdAt <= :endDate");
            dateRange = resultFilters.getDateRange();
        }

        final EntityGraph<E> entityGraph = (EntityGraph<E>) QueryHelper.getEntityGraph(em, classSimpleName);

        final String sql = sb.toString();

        final TypedQuery<E> query = entityGraph != null ?
                em.createQuery(sql, clazz)
                        .setHint(HINT_NAME_FETCHGRAPH, entityGraph) :
                em.createQuery(sql, clazz);

        query.setParameter("userId", userId);
        if (ids != null && !ids.isEmpty()) {
            query.setParameter(
                    "ids",
                    ids.stream()
                    .map(Long::parseLong)
                    .toList());
        }

        if (remappedOptionalParams != null && !remappedOptionalParams.isEmpty()) {
            remappedOptionalParams.forEach(query::setParameter);
        }

        if (dateRange != null) {
            query.setParameter(
                    "startDate",
                    new Timestamp(Long
                            .parseLong(dateRange[0])));
            query.setParameter(
                    "endDate",
                    new Timestamp(Long
                            .parseLong(dateRange[1])));
        }

        return query.getResultList();
    }

}
