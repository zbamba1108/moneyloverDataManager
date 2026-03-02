package dev.boog.moneyloverdatamanager.utils;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class QueryBuilder<E> {

    private final EntityManager em;

    private final StringBuilder sb;

    private final Class<E> clazz;

    private final String userId;

    private final boolean mapDetails;

    private HashMap<String, String> remappedOptionalParams;

    private String[] dateRange;

    private EntityGraph<E> entityGraph;

    private List<String> ids;

    public QueryBuilder(EntityManager em, Class<E> clazz, String userId, boolean mapDetails) {
        this.em = em;
        this.clazz = clazz;
        this.userId = userId;
        sb = new StringBuilder()
                .append("SELECT e from ")
                .append(clazz.getSimpleName())
                .append(" e where e.user.id = :userId");
        this.mapDetails = mapDetails;
    }

    public TypedQuery<E> build() {
        TypedQuery<E> query = em.createQuery(sb.toString(), clazz)
                .setParameter("userId", userId);

        addHint(query);
        addIds(query);
        addRemappedOptionalParams(query);
        addDateRange(query);

        return query;
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

    public QueryBuilder<E> addDateRange(ResultFilters resultFilters) {
        if (resultFilters != null
                && resultFilters.getDateRange() != null
                && resultFilters.getDateRange().length == 2) {
            sb.append(" AND e.createdAt >= :startDate AND e.createdAt <= :endDate");
            dateRange = resultFilters.getDateRange();
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

    private void addHint(TypedQuery<E> query) {
        if (entityGraph != null) {
            query.setHint(Constants.EntityGraph.HINT_NAME_FETCHGRAPH, entityGraph);
        }
    }

    private void addIds(TypedQuery<E> query) {
        if (ids != null && !ids.isEmpty()) {
            query.setParameter(
                    "ids",
                    ids.stream()
                            .map(Long::parseLong)
                            .toList());
        }
    }

    private void addRemappedOptionalParams(TypedQuery<E> query) {
        if (remappedOptionalParams != null && !remappedOptionalParams.isEmpty()) {
            remappedOptionalParams.forEach(query::setParameter);
        }
    }

    private void addDateRange(TypedQuery<E> query) {
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
    }

}
