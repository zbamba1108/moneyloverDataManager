package dev.boog.moneyloverdatamanager.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CustomSearchQueryRepositoryImpl<E, ID> implements CustomSearchQueryRepository<E, ID> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<E> searchWithMultipleOptionalParams(HashMap<String, ID> params, Class<E> clazz) {

        if (params == null || params.isEmpty()) {
            throw new IllegalArgumentException("params is null or empty");
        }

        String className = clazz.getSimpleName();

        String sql = "SELECT e FROM " + className + " e where";

        StringBuilder sb = new StringBuilder(sql);

        Iterator<String> iterator = params.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            sb.append(" e.").append(key).append(" = :").append(key.replace("\\.", ""));

            if (iterator.hasNext()) {
                sb.append(" AND ");
            }
        }

        sql = sb.toString();

        TypedQuery<E> query = em.createQuery(sql, clazz);

        params.forEach(query::setParameter);

        return query.getResultList();
    }
}
