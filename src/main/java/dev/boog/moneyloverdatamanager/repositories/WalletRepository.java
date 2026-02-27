package dev.boog.moneyloverdatamanager.repositories;


import dev.boog.moneyloverdatamanager.entities.*;


public interface WalletRepository extends UserRelatedEntitiesRepository<Wallet, Long>, CustomSearchQueryRepository<Wallet> {

}
