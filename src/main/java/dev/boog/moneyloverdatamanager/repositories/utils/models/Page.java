package dev.boog.moneyloverdatamanager.repositories.utils.models;

import lombok.Builder;

@Builder
public record Page(long records, boolean hasNext) {
}
