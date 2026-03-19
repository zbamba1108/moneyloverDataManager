package dev.boog.moneyloverdatamanager.repositories.utils;

import dev.boog.moneyloverdatamanager.repositories.utils.models.Page;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryResult;
import dev.boog.moneyloverdatamanager.utils.Constants;
import dev.boog.moneyloverdatamanager.utils.enums.SortingOrder;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class SearchQueryHelper {

    private SearchQueryHelper() {
        throw new UnsupportedOperationException(Constants.Messages.UTILITY_CLASS);
    }

    public static <E> void applyPagination(TypedQuery<E> typedQuery, QueryRequest<?> queryRequest) {
        typedQuery.setFirstResult(queryRequest.pagination().page() * queryRequest.pagination().pageSize());
        typedQuery.setMaxResults(queryRequest.pagination().pageSize() + 1);
    }

    public static <E> void applyPredicates(QueryRequest<E> queryRequest, CriteriaQuery<?> query, Root<E> root, CriteriaBuilder cb) {
        List<Predicate> predicates = buildPredicates(queryRequest, root, cb);

        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }
    } 
    
    public static <E> void applySorting(QueryRequest<E> queryRequest, CriteriaQuery<?> query, Root<E> root, CriteriaBuilder cb) {
        Order order = queryRequest.sorting().sortOrder() == SortingOrder.ASC ?
                cb.asc(root.get(queryRequest.sorting().field()))
                : cb.desc(root.get(queryRequest.sorting().field()));
        
        query.orderBy(order);
    }

    public static <E> void applySortingById(CriteriaQuery<?> query, Root<E> root, CriteriaBuilder cb) {
        Order order = cb.asc(root.get(Constants.Fields.ID));

        query.orderBy(order);
    }

    public static <E> QueryResult<E> buildQueryResult(List<E> results, QueryRequest<?> queryRequest) {
        Integer pageSize = queryRequest.pagination().pageSize();
        boolean hasNext = results != null && results.size() > pageSize;

        if (hasNext) {
            results = results.subList(0,  pageSize);
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

    private static <E> List<Predicate> buildPredicates(QueryRequest<E> request, Root<E> root, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        whereUserId(request, root, cb, predicates);

        whereIdIn(request, root, predicates);

        whereOptionalParams(request, root, cb, predicates);

        whereDateRange(request, root, cb, predicates);

        return predicates;
    }

    private static <E> void whereDateRange(QueryRequest<E> request, Root<E> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (request.dateRange() != null && request.dateRange().length == 2) {
            Long startDate = request.dateRange()[0];
            Long endDate = request.dateRange()[1];
            predicates.add(cb.between(root.get(Constants.Fields.CREATED_AT),
                    new Timestamp(startDate),
                    new Timestamp(endDate)
                    )
            );
        }
    }

    private static <E> void whereOptionalParams(QueryRequest<E> request, Root<E> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (request.optionalParams() != null && !request.optionalParams().isEmpty()) {
            request.optionalParams().keySet().forEach(field -> predicates.add(
                    cb.equal(resolvePath(root, field),
                    request.optionalParams().get(field))));
        }
    }

    private static <E> void whereIdIn(QueryRequest<E> request, Root<E> root, List<Predicate> predicates) {
        if (request.ids() != null && !request.ids().isEmpty()) {
            predicates.add(root.get(Constants.Fields.ID).in(request.ids()));
        }
    }

    private static <E> void whereUserId(QueryRequest<E> request, Root<E> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (request.userId() != null){
            predicates.add(cb.equal(root.get(Constants.Fields.USER).get(Constants.Fields.ID), request.userId()));
        }
    }

    private static Path<?> resolvePath(Path<?> path, String key) {
        String[] parts = key.split("\\.");

        for (String part : parts) {
            path = path.get(part);
        }

        return path;
    }
}
