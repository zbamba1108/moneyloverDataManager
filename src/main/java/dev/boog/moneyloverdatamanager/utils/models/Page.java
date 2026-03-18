package dev.boog.moneyloverdatamanager.utils.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record Page(long records, boolean hasNext) {
}
