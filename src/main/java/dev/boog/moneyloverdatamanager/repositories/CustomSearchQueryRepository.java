package dev.boog.moneyloverdatamanager.repositories;


import dev.boog.moneyloverdatamanager.entities.BaseEntity;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryResult;


public interface CustomSearchQueryRepository<E extends BaseEntity> {

    QueryResult<E> findAll(QueryRequest<E> queryRequest);

    QueryResult<Long> findAllAndSelectIds(QueryRequest<E> queryRequest);
}
