package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.Transaction;

public interface TransactionRepository extends UserRelatedEntitiesRepository<Transaction, Long>, CustomSearchQueryRepository<Transaction> {


}
