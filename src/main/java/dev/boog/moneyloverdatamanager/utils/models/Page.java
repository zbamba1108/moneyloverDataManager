package dev.boog.moneyloverdatamanager.utils.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Page {

    private long totalRecords;

    private boolean hasMore;
}
