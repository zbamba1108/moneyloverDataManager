package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.Transaction;
import dev.boog.moneyloverdatamanager.utils.Constants;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, Long>,
                                               CustomSearchQueryRepository<Transaction> {

    @EntityGraph(value = Constants.EntityGraph.TRANSACTION_WALLET_CATEGORY)
    List<Transaction> findAllByIdIn(List<Long> ids);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Transaction WHERE id=:id AND user_id=:userId", nativeQuery = true)
    void deleteByIdAndUserId(Long id, Long userId);
}
