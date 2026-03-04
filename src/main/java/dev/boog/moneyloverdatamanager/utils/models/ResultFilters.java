package dev.boog.moneyloverdatamanager.utils.models;

import dev.boog.moneyloverdatamanager.utils.enums.SortingOrder;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultFilters {

    private Integer page;

    private Integer pageSize;

    private String[] dateRange;

    private SortingOrder sortingOrder;

    private String sortingField;
}
