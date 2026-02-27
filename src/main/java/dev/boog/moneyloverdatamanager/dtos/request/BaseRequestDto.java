package dev.boog.moneyloverdatamanager.dtos.request;

import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequestDto {

    private List<String> ids;

    private OrderBy orderBy;

    private String[] dateRange;

}
