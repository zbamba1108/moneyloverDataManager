package dev.boog.moneyloverdatamanager.utils;

import dev.boog.moneyloverdatamanager.repositories.specifications.SearchSpecification;
import dev.boog.moneyloverdatamanager.utils.models.Page;
import dev.boog.moneyloverdatamanager.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.utils.models.QueryResult;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public final class SearchQueryHelper {

    private SearchQueryHelper() {
        throw new UnsupportedOperationException(Constants.Messages.UTILITY_CLASS);
    }

    public static Path<?> resolvePath(Path<?> path, String key) {
        String[] parts = key.split("\\.");

        for (String part : parts) {
            path = path.get(part);
        }

        return path;
    }

    public static <E> Specification<E> buildSpecification(QueryRequest<E> request) {
        Specification<E> specification = Specification.where(
                SearchSpecification.hasUserId(request.userId()));

        if (request.ids() != null && !request.ids().isEmpty()) {
            specification = specification.and(
                    SearchSpecification.fieldIn("id", request.ids()));
        }

        if (request.optionalParams() != null && !request.optionalParams().isEmpty()) {
            for (String key : request.optionalParams().keySet()) {
                specification = specification.and(
                        SearchSpecification.withFieldEquals(key, request.optionalParams().get(key)));
            }
        }

        if (request.resultFilters() != null
                && request.resultFilters().dateRange() != null
                && request.resultFilters().dateRange().length == 2) {
            specification = specification.and(
                    SearchSpecification.withDateBetween(request.resultFilters().dateRange()));
        }

        return specification;
    }

    public static <E> void paginateQuery(TypedQuery<E> typedQuery, QueryRequest<?> queryRequest) {
        typedQuery.setFirstResult(queryRequest.resultFilters().page() * queryRequest.resultFilters().pageSize());
        typedQuery.setMaxResults(queryRequest.resultFilters().pageSize() + 1);
    }

    public static <E> void addPredicate(CriteriaQuery<?> query, QueryRequest<E> queryRequest, Root<E> root, CriteriaBuilder cb) {
        Predicate predicate = SearchQueryHelper
                .buildSpecification(queryRequest)
                .toPredicate(root, query, cb);

        if (predicate != null) {
            query.where(predicate);
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
}
