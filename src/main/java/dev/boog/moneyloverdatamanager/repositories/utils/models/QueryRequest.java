package dev.boog.moneyloverdatamanager.repositories.utils.models;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record QueryRequest<E>(

        Class<E> entityClass,

        Long userId,

        List<Long> ids,

        Map<String, Object> optionalParams,

        Long[] dateRange,

        Pagination pagination,

        Sorting sorting) {

}
