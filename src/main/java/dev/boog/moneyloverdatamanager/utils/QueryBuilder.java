package dev.boog.moneyloverdatamanager.utils;

import dev.boog.moneyloverdatamanager.utils.models.ResultFilters;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public final class QueryBuilder {

    private EntityManager em;

    private StringBuilder sb;

    private Class<?> clazz;

    private String userId;

    private HashMap<String, String> remappedOptionalParams;

    private ResultFilters resultFilters;

    private TypedQuery<?> query;

    private EntityGraph<?> entityGraph;

    private List<String> ids;

    private boolean mapDetails;

    private boolean returnLong;

    private boolean pagination;

    public TypedQuery<?> buildQuery() {
        this.query = returnLong ? em.createQuery(sb.toString(), Long.class) : em.createQuery(sb.toString(), clazz);

        addUserIdParam();
        addHintParam();
        addIdsParam();
        addRemappedOptionalParams();
        addDateRangeParam();
        addPaginationParam();

        return this.query;
    }

    public QueryBuilder initializeQuery(StringBuilder sb) {
        this.sb = sb;
        return this;
    }

    public QueryBuilder addEntityGraph() {
        if (mapDetails) {
            entityGraph = QueryHelper.getEntityGraph(em, clazz.getSimpleName());
        }
        return this;
    }

    public QueryBuilder addOptionalParam(HashMap<String, String> optionalParams) {
        if (optionalParams != null && !optionalParams.isEmpty()) {
            remappedOptionalParams = new HashMap<>();
            sb.append(" AND ");
            QueryHelper.buildQueryAndCreateQueryParam(sb, optionalParams, remappedOptionalParams);
        }
        return this;
    }

    public QueryBuilder addDateRange() {
        if (resultFilters != null
                && resultFilters.getDateRange() != null
                && resultFilters.getDateRange().length == 2) {
            sb.append(" AND e.createdAt >= :startDate AND e.createdAt <= :endDate");
        }
        return this;
    }

    public QueryBuilder addIds(List<String> ids) {
        if (ids != null && !ids.isEmpty()) {
            sb.append(" AND e.id in (:ids)");
            this.ids = ids;
        }
        return this;
    }

    public QueryBuilder addOrderBy(ResultFilters resultFilters) {
        if (resultFilters != null && resultFilters.getSortingOrder() != null && StringUtils.isNotBlank(resultFilters.getSortingField())) {
            sb.append(" ORDER BY e.")
                    .append(resultFilters.getSortingField())
                    .append(" ")
                    .append(resultFilters.getSortingOrder().toString());
        }

        return this;
    }

    private void addUserIdParam() {
        this.query.setParameter("userId", userId);
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

    private void addPaginationParam() {
        if (pagination && resultFilters != null && resultFilters.getPage() != null && resultFilters.getPageSize() != null) {
            query.setFirstResult(resultFilters.getPage() * resultFilters.getPageSize());
            query.setMaxResults(resultFilters.getPageSize());
        }
    }

}
