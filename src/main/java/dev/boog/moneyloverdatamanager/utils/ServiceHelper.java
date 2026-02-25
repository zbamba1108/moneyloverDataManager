package dev.boog.moneyloverdatamanager.utils;


import java.util.Arrays;
import java.util.HashMap;

public class ServiceHelper {

    public static HashMap<String, Long> mapQueryParams(String query) {
        String[] params = query.split("\\-");

        HashMap<String, Long> map = new HashMap<>();

        Arrays.stream(params).forEach(param -> map.put(param.split("=")[0], Long.valueOf(param.split("=")[1])));

        return map;
    }

    /*public static HashMap<String, Long> mapTransactionQueryParams(String userId, Long id, Long walletId, Long categoryId) {
        HashMap<String, Long> params = new HashMap<>();

        if (id != null) {
            params.put("id", id);
        }
        if (walletId != null) {
            params.put("walletId", walletId);
        }
        if (categoryId != null) {
            params.put("categoryId", categoryId);
        }
        if (StringUtils.hasText(userId)) {
            params.put("userId", Long.parseLong(userId));
        }

        return params;
    }*/
}
