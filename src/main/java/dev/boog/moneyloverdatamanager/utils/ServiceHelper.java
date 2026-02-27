package dev.boog.moneyloverdatamanager.utils;


import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestBudgetDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestEventDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestUserDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;

import java.util.HashMap;

public class ServiceHelper {

    public static HashMap<String, String> mapQueryParams(String userId, BaseRequestDto req) {

        if (req instanceof RequestTransactionDto) {
            return mapTransactionQueryParams((RequestTransactionDto) req, userId);
        } else if (req instanceof RequestWalletDto) {
            return mapWalletQueryParams((RequestWalletDto) req, userId);
        } else if (req instanceof RequestCategoryDto) {
            return mapCategoryQueryParams((RequestCategoryDto) req, userId);
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

    public static ResultFilters filter(BaseRequestDto req) {
        return req != null ?
                ResultFilters.builder()
                    .sort(null)
                    .pageable(null)
                    .dateRange(req.getDateRange())
                    .build()
                : null;
    }

    private static HashMap<String, String> mapTransactionQueryParams(RequestTransactionDto req, String userId) {
        HashMap<String, String> params = new HashMap<>();

        if (req.getWalletId() != null) {
            params.put("wallet.id", String.valueOf(req.getWalletId()));
        }
        if (req.getCategoryId() != null) {
            params.put("category.id", String.valueOf(req.getCategoryId()));
        }

        return params;
    }

    private static HashMap<String, String> mapCategoryQueryParams(RequestCategoryDto req, String userId) {
        HashMap<String, String> params = new HashMap<>();

        if (req.getType() != null) {
            params.put("type", String.valueOf(req.getType()));
        }
        if (req.getParentId() != null) {
            params.put("parent.id", String.valueOf(req.getParentId()));
        }

        return params;
    }

    private static HashMap<String, String> mapWalletQueryParams(RequestWalletDto req, String userId) {
        return null;
    }
}
