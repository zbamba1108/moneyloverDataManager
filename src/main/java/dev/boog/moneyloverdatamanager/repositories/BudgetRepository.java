package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.Budget;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BudgetRepository extends BaseRepository<Budget, Long>,
                                          CustomSearchQueryRepository<Budget> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Budget WHERE id=:id AND user_id=:userId", nativeQuery = true)
    void deleteByIdAndUserId(Long id, Long userId);
}
