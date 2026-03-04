package dev.boog.moneyloverdatamanager.repositories;


import dev.boog.moneyloverdatamanager.utils.mappers.models.QueryRequest;
import dev.boog.moneyloverdatamanager.utils.mappers.models.QueryResult;


public interface CustomSearchQueryRepository<E> {

    QueryResult<E> search(QueryRequest queryRequest);

}
