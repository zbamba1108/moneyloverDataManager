package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.Transaction;
import dev.boog.moneyloverdatamanager.utils.Constants;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, Long>,
                                               CustomSearchQueryRepository<Transaction>,
                                               CustomDeleteQueryRepository<Transaction, Long> {

    @EntityGraph(value = Constants.EntityGraph.TRANSACTION_WALLET_CATEGORY)
    List<Transaction> findAllByIdIn(List<Long> ids);

}
