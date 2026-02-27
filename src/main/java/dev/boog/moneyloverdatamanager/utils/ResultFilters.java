package dev.boog.moneyloverdatamanager.utils;

import lombok.*;
import org.springframework.data.domain.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultFilters {

    private Pageable pageable;

    private Sort sort;

    private String[] dateRange;
}
