package dev.boog.moneyloverdatamanager.repositories.utils.models;

import dev.boog.moneyloverdatamanager.utils.enums.SortingOrder;
import lombok.Builder;


@Builder
public record Sorting(String field, SortingOrder sortOrder) {
}
