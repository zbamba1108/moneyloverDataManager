package dev.boog.moneyloverdatamanager.utils.models;

import lombok.Builder;

@Builder
public record Page(long records, boolean hasNext) {
}
