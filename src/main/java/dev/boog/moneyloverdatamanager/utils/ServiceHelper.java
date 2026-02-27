package dev.boog.moneyloverdatamanager.utils;


import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestBudgetDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestEventDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestUserDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import org.springframework.util.StringUtils;

import java.util.HashMap;

public class ServiceHelper {

    public static HashMap<String, String> mapQueryParams(String userId, BaseRequestDto req) {

        if (req instanceof RequestTransactionDto) {
            return mapTransactionQueryParams((RequestTransactionDto) req, userId);
        } else if (req instanceof RequestWalletDto) {
            return null;
        } else if (req instanceof RequestCategoryDto) {
            return mapCategoryQueryParams((RequestCategoryDto) req, userId);
        } else if (req instanceof RequestEventDto) {
            return null;
        } else if (req instanceof RequestUserDto) {
            return null;
        } else if (req instanceof RequestBudgetDto) {
            return null;
        } else {
            return mapUserId(userId);
        }
    }

    private static HashMap<String, String> mapUserId(String userId) {
        HashMap<String, String> params = new HashMap<>();
        if (StringUtils.hasText(userId)) {
            params.put("user.id", userId);
        }

        return params;
    }

    private static HashMap<String, String> mapTransactionQueryParams(RequestTransactionDto req, String userId) {
        HashMap<String, String> params = mapUserId(userId);

        if (req.getId() != null) {
            params.put("id", String.valueOf(req.getId()));
        }
        if (req.getWalletId() != null) {
            params.put("wallet.id", String.valueOf(req.getWalletId()));
        }
        if (req.getCategoryId() != null) {
            params.put("category.id", String.valueOf(req.getCategoryId()));
        }

        return params;
    }

    private static HashMap<String, String> mapCategoryQueryParams(RequestCategoryDto req, String userId) {
        HashMap<String, String> params = mapUserId(userId);

        if (req.getId() != null) {
            params.put("id", String.valueOf(req.getId()));
        }
        if (req.getType() != null) {
            params.put("type", String.valueOf(req.getType()));
        }
        if (req.getParentId() != null) {
            params.put("parent.id", String.valueOf(req.getParentId()));
        }

        return params;
    }
}
