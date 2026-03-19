package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.BaseEntity;

import java.util.List;

public interface CustomDeleteQueryRepository<E extends BaseEntity, ID extends Number> {

    void deleteByIds(Class<E> entityClass, ID userId, List<ID> id);
}
