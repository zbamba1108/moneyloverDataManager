package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.utils.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.*;

public class CustomSearchQueryRepositoryImpl<E> implements CustomSearchQueryRepository<E> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<E> searchByUserId(Class<E> clazz, String userId, ResultFilters resultFilters, boolean mapDetails) {
        return search(clazz, userId, null, null, resultFilters, mapDetails);
    }

    @Override
    public List<E> searchByUserIdAndIds(Class<E> clazz, String userId, List<String> ids, ResultFilters resultFilters, boolean mapDetails) {
        return search(clazz, userId, ids, null, resultFilters, mapDetails);
    }

    @Override
    public List<E> searchByUserIdAndOptionalParams(Class<E> clazz, String userId, HashMap<String, String> optionalParams, ResultFilters resultFilters, boolean mapDetails) {
        return search(clazz, userId, null, optionalParams, resultFilters, mapDetails);
    }

    @Override
    public List<E> searchByUserIdAndIdsAndOptionalParams(Class<E> clazz, String userId, List<String> ids, HashMap<String, String> optionalParams, ResultFilters resultFilters, boolean mapDetails) {
        return search(clazz, userId, ids, optionalParams, resultFilters, mapDetails);
    }

    @Override
    public List<E> search(Class<E> clazz, String userId, List<String> ids, HashMap<String, String> optionalParams, ResultFilters resultFilters, boolean mapDetails) {
        return new QueryBuilder<>(em, clazz, userId, mapDetails)
                .addEntityGraph()
                .addIds(ids)
                .addOptionalParam(optionalParams)
                .addDateRange(resultFilters)
                .build()
                .getResultList();
    }

}
