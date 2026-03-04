package dev.boog.moneyloverdatamanager.utils;


import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestBudgetDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestEventDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestUserDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.utils.models.ResultFilters;

import java.util.HashMap;

public final class ServiceHelper {

    public static QueryRequest getQueryRequest(Class<?> clazz, BaseRequestDto req, String userId, boolean mapDetails, boolean hasChildren) {
        return QueryRequest.builder()
                .clazz(clazz)
                .userId(userId)
                .ids(req != null ? req.getIds() : null)
                .optionalParams(mapQueryParams(req))
                .resultFilters(mapResultFilter(req))
                .mapDetails(mapDetails)
                .hasChildren(hasChildren)
                .build();
    }

    private static HashMap<String, String> mapQueryParams(BaseRequestDto req) {
        if (req instanceof RequestTransactionDto) {
            return mapTransactionQueryParams((RequestTransactionDto) req);
        } else if (req instanceof RequestWalletDto) {
            return mapWalletQueryParams((RequestWalletDto) req);
        } else if (req instanceof RequestCategoryDto) {
            return mapCategoryQueryParams((RequestCategoryDto) req);
        } else if (req instanceof RequestEventDto) {
            return null;
        } else if (req instanceof RequestUserDto) {
            return null;
        } else if (req instanceof RequestBudgetDto) {
            return null;
        } else {
            return null;
        }
    }

    private static ResultFilters mapResultFilter(BaseRequestDto req) {
        return req != null ?
                ResultFilters.builder()
                    .page(req.getPage())
                    .pageSize(req.getPageSize())
                    .dateRange(req.getDateRange())
                    .sortingField(req.getSortingField())
                    .sortingOrder(req.getSortingOrder())
                    .build()
                : null;
    }

    private static HashMap<String, String> mapTransactionQueryParams(RequestTransactionDto req) {
        HashMap<String, String> params = new HashMap<>();

        if (req.getWalletId() != null) {
            params.put("wallet.id", String.valueOf(req.getWalletId()));
        }
        if (req.getCategoryId() != null) {
            params.put("category.id", String.valueOf(req.getCategoryId()));
        }

        return params;
    }

    private static HashMap<String, String> mapCategoryQueryParams(RequestCategoryDto req) {
        HashMap<String, String> params = new HashMap<>();

        if (req.getType() != null) {
            params.put("type", String.valueOf(req.getType()));
        }
        if (req.getParentId() != null) {
            params.put("parent.id", String.valueOf(req.getParentId()));
        }

        return params;
    }

    private static HashMap<String, String> mapWalletQueryParams(RequestWalletDto req) {
        return null;
    }
}
