package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long>,
                                            CustomSearchQueryRepository<Category>,
                                            CustomDeleteQueryRepository<Category, Long> {
}
