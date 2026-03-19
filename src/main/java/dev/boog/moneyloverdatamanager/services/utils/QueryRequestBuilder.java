package dev.boog.moneyloverdatamanager.services.utils;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.entities.BaseEntity;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.repositories.utils.models.ResultFilters;
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
                .resultFilters(mapResultFilter(req))
                .build();
    }

    private static <R extends BaseRequestDto> ResultFilters mapResultFilter(R request) {
        return request != null ?
                ResultFilters.builder()
                        .page(request.getPage())
                        .pageSize(request.getPageSize())
                        .dateRange(mapDateRange(request))
                        .sortingField(request.getSortingField())
                        .sortingOrder(request.getSortingOrder())
                        .build()
                : null;
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
}
