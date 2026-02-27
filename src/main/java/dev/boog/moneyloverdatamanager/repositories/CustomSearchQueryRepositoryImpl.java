package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.utils.QueryHelper;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.HashMap;
import java.util.List;

public class CustomSearchQueryRepositoryImpl<E> implements CustomSearchQueryRepository<E> {

    private static final String HINT_NAME_FETCHGRAPH = "jakarta.persistence.fetchgraph";

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<E> searchWithMultipleOptionalParams(HashMap<String, String> params, Class<E> clazz) {

        if (params == null || params.isEmpty()) {
            throw new IllegalArgumentException("params is null or empty");
        }

        final HashMap<String, String> queryParams = new HashMap<>();

        final String classSimpleName = clazz.getSimpleName();

        final EntityGraph<E> entityGraph = (EntityGraph<E>) QueryHelper.getEntityGraph(em, classSimpleName);

        String sql = "SELECT e from " + classSimpleName + " e where";
        sql = QueryHelper.buildQueryAndCreateQueryParam(sql, params, queryParams);

        TypedQuery<E> query = null;
        if (entityGraph != null) {
            em.createQuery(sql, clazz)
                    .setHint(HINT_NAME_FETCHGRAPH, entityGraph);
        } else {
            query = em.createQuery(sql, clazz);
        }

        queryParams.forEach(query::setParameter);

        return query.getResultList();
    }

}
