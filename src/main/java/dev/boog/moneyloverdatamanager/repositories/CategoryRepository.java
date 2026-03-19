package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long>,
                                            CustomSearchQueryRepository<Category> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Category WHERE id=:id AND user_id=:userId", nativeQuery = true)
    void deleteByIdAndUserId(Long id, Long userId);
}
