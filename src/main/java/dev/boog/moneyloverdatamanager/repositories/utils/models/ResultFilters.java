package dev.boog.moneyloverdatamanager.repositories.utils.models;

import dev.boog.moneyloverdatamanager.utils.enums.SortingOrder;
import lombok.Builder;

@Builder
public record ResultFilters(

    Integer page,

    Integer pageSize,

    String[] dateRange,

    SortingOrder sortingOrder,

    String sortingField) {

}
