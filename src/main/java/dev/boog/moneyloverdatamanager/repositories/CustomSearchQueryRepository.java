package dev.boog.moneyloverdatamanager.repositories;


import dev.boog.moneyloverdatamanager.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.utils.models.QueryResult;


public interface CustomSearchQueryRepository<E> {

    QueryResult<E> findAll(Class<E> clazz, QueryRequest<E> queryRequest);

    QueryResult<Long> findAllAndSelectIds(Class<E> clazz, QueryRequest<E> queryRequest);
}
