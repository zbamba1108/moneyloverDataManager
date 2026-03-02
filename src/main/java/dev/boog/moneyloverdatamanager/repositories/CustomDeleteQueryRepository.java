package dev.boog.moneyloverdatamanager.repositories;

import java.util.List;

public interface CustomDeleteQueryRepository<E, ID extends Number> {

    void deleteByIds(Class<E> entityClass, ID userId, List<ID> id);
}
