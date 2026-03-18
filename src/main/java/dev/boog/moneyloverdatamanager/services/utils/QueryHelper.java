package dev.boog.moneyloverdatamanager.services.utils;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.entities.BaseEntity;

import java.util.HashMap;
import java.util.Map;

public abstract class QueryHelper<E extends BaseEntity, R extends BaseRequestDto> {

    private final Class<E> entityClass;

    public QueryHelper(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public abstract Map<String, Object> mapOptionalParams(R req);

    public final Class<E> getEntityClass() {
        return entityClass;
    }
}
