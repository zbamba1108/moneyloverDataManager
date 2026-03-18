package dev.boog.moneyloverdatamanager.repositories;


import dev.boog.moneyloverdatamanager.entities.Wallet;
import dev.boog.moneyloverdatamanager.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;


public interface WalletRepository extends UserRelatedEntitiesRepository<Wallet, Long>,
                                          CustomSearchQueryRepository<Wallet>,
                                          CustomDeleteQueryRepository<Wallet, Long> {

    @EntityGraph(value = Constants.EntityGraph.WALLET_TRANSACTION)
    List<Wallet> findAllByIdIn(List<Long> ids);
}
