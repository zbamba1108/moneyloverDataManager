package dev.boog.moneyloverdatamanager.services.utils;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.entities.BaseEntity;

import java.util.HashMap;

public abstract class QueryHelper<E extends BaseEntity> {

    private final Class<E> entityClass;

    public QueryHelper(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public abstract HashMap<String, String> mapOptionalParams(BaseRequestDto baseRequestDto);

    public final Class<E> getEntityClass() {
        return entityClass;
    }
}
