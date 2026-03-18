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

    public static <E extends BaseEntity> QueryRequest<E> build(QueryHelper<E> queryHelper, BaseRequestDto req, String userId) {
        return QueryRequest.<E>builder()
                .entityClass(queryHelper.getEntityClass())
                .userId(userId)
                .ids(req != null ? req.getIds() : null)
                .optionalParams(queryHelper.mapOptionalParams(req))
                .resultFilters(mapResultFilter(req))
                .build();
    }

    private static ResultFilters mapResultFilter(BaseRequestDto request) {
        return request != null ?
                ResultFilters.builder()
                        .page(request.getPage())
                        .pageSize(request.getPageSize())
                        .dateRange(request.getDateRange())
                        .sortingField(request.getSortingField())
                        .sortingOrder(request.getSortingOrder())
                        .build()
                : null;
    }
}
