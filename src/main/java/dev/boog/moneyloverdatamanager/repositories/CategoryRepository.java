package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.Category;

public interface CategoryRepository extends UserRelatedEntitiesRepository<Category, Long>, CustomSearchQueryRepository<Category> {
}
