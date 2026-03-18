package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.Transaction;
import dev.boog.moneyloverdatamanager.utils.Constants;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface TransactionRepository extends UserRelatedEntitiesRepository<Transaction, Long>, CustomSearchQueryRepository<Transaction> {

    @EntityGraph(value = Constants.EntityGraph.TRANSACTION_WALLET_CATEGORY)
    List<Transaction> findAllByIdIn(List<Long> ids);

}
