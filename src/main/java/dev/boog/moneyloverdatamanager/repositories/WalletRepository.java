package dev.boog.moneyloverdatamanager.repositories;


import dev.boog.moneyloverdatamanager.entities.Wallet;
import dev.boog.moneyloverdatamanager.utils.Constants;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WalletRepository extends BaseRepository<Wallet, Long>,
                                          CustomSearchQueryRepository<Wallet> {

    @EntityGraph(value = Constants.EntityGraph.WALLET_TRANSACTION)
    List<Wallet> findAllByIdIn(List<Long> ids);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Wallet WHERE id=:id AND user_id=:userId", nativeQuery = true)
    void deleteByIdAndUserId(Long id, Long userId);
}
