package dev.boog.moneyloverdatamanager.repositories;


import dev.boog.moneyloverdatamanager.utils.*;
import dev.boog.moneyloverdatamanager.utils.mappers.models.QueryResult;

import java.util.*;

public interface CustomSearchQueryRepository<E> {

    QueryResult<E> searchByUserId(Class<E> clazz, String userId, ResultFilters resultFilters, boolean mapDetails);

    QueryResult<E> searchByUserIdAndIds(Class<E> clazz, String userId, List<String> ids, ResultFilters resultFilters, boolean mapDetails);

    QueryResult<E> searchByUserIdAndOptionalParams(Class<E> clazz, String userId, HashMap<String, String> optionalParams, ResultFilters resultFilters, boolean mapDetails);

    QueryResult<E> searchByUserIdAndIdsAndOptionalParams(Class<E> clazz, String userId, List<String> ids, HashMap<String, String> optionalParams, ResultFilters resultFilters, boolean mapDetails);

    QueryResult<E> search(Class<E> clazz , String userId, List<String> ids, HashMap<String, String> optionalParams, ResultFilters resultFilters, boolean mapDetails);

}
