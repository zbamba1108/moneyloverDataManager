package dev.boog.moneyloverdatamanager.services.utils;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.entities.BaseEntity;
import dev.boog.moneyloverdatamanager.repositories.utils.models.Pagination;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.repositories.utils.models.Sorting;
import dev.boog.moneyloverdatamanager.utils.Constants;

public final class QueryRequestBuilder {

    private QueryRequestBuilder() {
        throw new IllegalStateException(Constants.Messages.UTILITY_CLASS);
    }

    public static <E extends BaseEntity, R extends BaseRequestDto> QueryRequest<E> build(QueryHelper<E, R> queryHelper, R req, Long userId) {
        return QueryRequest.<E>builder()
                .entityClass(queryHelper.getEntityClass())
                .userId(userId)
                .ids(req != null ? req.getIds() : null)
                .optionalParams(queryHelper.mapOptionalParams(req))
                .dateRange(mapDateRange(req))
                .pagination(mapPagination(req))
                .sorting(mapSorting(req))
                .build();
    }

    private static <R extends BaseRequestDto> Long[] mapDateRange(R request) {

        if (request.getStartDate() != null && request.getEndDate() != null) {
            Long[] dateRange = new Long[2];
            dateRange[0] = request.getStartDate();
            dateRange[1] = request.getEndDate();

            return dateRange;
        }

        return null;
    }

    private static <R extends BaseRequestDto> Pagination mapPagination(R request) {
        return Pagination
                .builder()
                .page(request.getPage())
                .pageSize(request.getPageSize())
                .build();
    }

    private static <R extends BaseRequestDto> Sorting mapSorting(R request) {
        return Sorting
                .builder()
                .field(request.getSortingField())
                .sortOrder(request.getSortingOrder())
                .build();
    }
}
