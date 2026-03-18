package dev.boog.moneyloverdatamanager.repositories.utils;

import dev.boog.moneyloverdatamanager.repositories.utils.models.Page;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryResult;
import dev.boog.moneyloverdatamanager.utils.Constants;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class SearchQueryHelper {

    private SearchQueryHelper() {
        throw new UnsupportedOperationException(Constants.Messages.UTILITY_CLASS);
    }

    public static <E> void paginateQuery(TypedQuery<E> typedQuery, QueryRequest<?> queryRequest) {
        typedQuery.setFirstResult(queryRequest.resultFilters().page() * queryRequest.resultFilters().pageSize());
        typedQuery.setMaxResults(queryRequest.resultFilters().pageSize() + 1);
    }

    public static <E> void addPredicate(CriteriaQuery<?> query, QueryRequest<E> queryRequest, Root<E> root, CriteriaBuilder cb) {
        List<Predicate> predicates = buildPredicates(queryRequest, root, cb);

        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }
    }

    public static <E> QueryResult<E> buildQueryResult(List<E> results, QueryRequest<?> queryRequest) {
        boolean hasNext = results != null && results.size() > queryRequest.resultFilters().pageSize();;

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

    private static Path<?> resolvePath(Path<?> path, String key) {
        String[] parts = key.split("\\.");

        for (String part : parts) {
            path = path.get(part);
        }

        return path;
    }

    private static <E> List<Predicate> buildPredicates(QueryRequest<E> request, Root<E> root, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(request.userId())){
            predicates.add(cb.equal(root.get("user").get("id"), request.userId()));
        }

        if (request.ids() != null && !request.ids().isEmpty()) {
            predicates.add(root.get("id").in(request.ids()));
        }

        if (request.optionalParams() != null && !request.optionalParams().isEmpty()) {
            request.optionalParams().keySet().forEach(field -> {
                predicates.add(
                        cb.equal(resolvePath(root, field),
                        request.optionalParams().get(field)));
            });
        }

        if (request.resultFilters() != null
                && request.resultFilters().dateRange() != null
                && request.resultFilters().dateRange().length == 2) {
            String startDate = request.resultFilters().dateRange()[0];
            String endDate = request.resultFilters().dateRange()[1];
            predicates.add(cb.between(root.get("createdAt"),
                    new Timestamp(Long.parseLong(startDate)),
                    new Timestamp(Long.parseLong(endDate))
                    )
            );
        }

        return predicates;
    }
}
