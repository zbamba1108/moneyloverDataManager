package dev.boog.moneyloverdatamanager.repositories.utils.models;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record QueryRequest<E>( // do not remove E

        Class<E> entityClass,

        Long userId,

        List<Long> ids,

        Map<String, Object> optionalParams,

        ResultFilters resultFilters) {

}
