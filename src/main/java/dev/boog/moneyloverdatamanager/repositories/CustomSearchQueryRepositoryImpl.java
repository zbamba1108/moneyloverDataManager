package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.HashMap;
import java.util.List;

public class CustomSearchQueryRepositoryImpl implements CustomSearchQueryRepository<Transaction, Long> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Transaction> searchWithMultipleOPtionalParams(Long userId, HashMap<String, Long> params) {
        return List.of(em.find(Transaction.class, params));
    }
}
