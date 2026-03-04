package dev.boog.moneyloverdatamanager.utils;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public final class QueryBuilder<E> {

    private final EntityManager em;

    private final StringBuilder sb;

    private final Class<E> clazz;

    private final String userId;

    private final boolean mapDetails;

    private final boolean returnLong;

    private HashMap<String, String> remappedOptionalParams;

    private final ResultFilters resultFilters;

    private TypedQuery<?> query;

    private EntityGraph<E> entityGraph;

    private List<String> ids;

    public QueryBuilder(EntityManager em, Class<E> clazz, StringBuilder sb, String userId, ResultFilters resultFilters, boolean mapDetails, boolean returnLong) {
        this.em = em;
        this.clazz = clazz;
        this.userId = userId;
        this.resultFilters = resultFilters;
        this.mapDetails = mapDetails;
        this.returnLong = returnLong;
        this.sb = sb;
    }

    public QueryBuilder<E> createQuery() {
        if (!returnLong) {
            sb.append(" ORDER BY e.user.id ASC");
        }
        this.query = returnLong ? em.createQuery(sb.toString(), Long.class) : em.createQuery(sb.toString(), clazz);

        return this;
    }

    public TypedQuery<?> build() {
        this.query.setParameter("userId", userId);
        addHintParam();
        addIdsParam();
        addRemappedOptionalParams();
        addDateRangeParam();

        return this.query;
    }

    public QueryBuilder<E> addEntityGraph() {
        if (mapDetails) {
            entityGraph = (EntityGraph<E>) QueryHelper.getEntityGraph(em, clazz.getSimpleName());
        }
        return this;
    }

    public QueryBuilder<E> addOptionalParam(HashMap<String, String> optionalParams) {
        if (optionalParams != null && !optionalParams.isEmpty()) {
            remappedOptionalParams = new HashMap<>();
            sb.append(" AND ");
            QueryHelper.buildQueryAndCreateQueryParam(sb, optionalParams, remappedOptionalParams);
        }
        return this;
    }

    public QueryBuilder<E> addDateRange() {
        if (resultFilters != null
                && resultFilters.getDateRange() != null
                && resultFilters.getDateRange().length == 2) {
            sb.append(" AND e.createdAt >= :startDate AND e.createdAt <= :endDate");
        }
        return this;
    }

    public QueryBuilder<E> addIds(List<String> ids) {
        if (ids != null && !ids.isEmpty()) {
            sb.append(" AND e.id in (:ids)");
            this.ids = ids;
        }
        return this;
    }

    private void addHintParam() {
        if (entityGraph != null) {
            query.setHint(Constants.EntityGraph.HINT_NAME_FETCHGRAPH, entityGraph);
        }
    }

    private void addIdsParam() {
        if (ids != null && !ids.isEmpty()) {
            query.setParameter(
                    "ids",
                    ids.stream()
                            .map(Long::parseLong)
                            .toList());
        }
    }

    private void addRemappedOptionalParams() {
        if (remappedOptionalParams != null && !remappedOptionalParams.isEmpty()) {
            remappedOptionalParams.forEach(query::setParameter);
        }
    }

    private void addDateRangeParam() {
        if (resultFilters != null && resultFilters.getDateRange() != null) {
            String[] dateRange = resultFilters.getDateRange();
            query.setParameter(
                    "startDate",
                    new Timestamp(Long
                            .parseLong(dateRange[0])));
            query.setParameter(
                    "endDate",
                    new Timestamp(Long
                            .parseLong(dateRange[1])));
        }
    }

    public QueryBuilder<E> addPaginationParam(ResultFilters resultFilters) {
        if (resultFilters != null && resultFilters.getPage() != null && resultFilters.getPageSize() != null) {
            query.setFirstResult(resultFilters.getPage() * resultFilters.getPageSize());
            query.setMaxResults(resultFilters.getPageSize());
        }
        return this;
    }

}
