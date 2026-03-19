package dev.boog.moneyloverdatamanager.repositories;


import dev.boog.moneyloverdatamanager.entities.Wallet;
import dev.boog.moneyloverdatamanager.utils.Constants;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends BaseRepository<Wallet, Long>,
                                          CustomSearchQueryRepository<Wallet>,
                                          CustomDeleteQueryRepository<Wallet, Long> {

    @EntityGraph(value = Constants.EntityGraph.WALLET_TRANSACTION)
    List<Wallet> findAllByIdIn(List<Long> ids);
}
