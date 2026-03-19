package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.Budget;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends BaseRepository<Budget, Long>,
                                          CustomSearchQueryRepository<Budget>,
                                          CustomDeleteQueryRepository<Budget, Long> {
}
