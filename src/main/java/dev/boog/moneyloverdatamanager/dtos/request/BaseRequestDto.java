package dev.boog.moneyloverdatamanager.dtos.request;

import java.util.*;

import dev.boog.moneyloverdatamanager.utils.enums.SortingOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequestDto {

    private List<String> ids;

    private Integer page;

    private Integer pageSize;

    private String[] dateRange;

    private String sortingField;

    private SortingOrder sortingOrder;

}
