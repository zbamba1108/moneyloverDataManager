package dev.boog.moneyloverdatamanager.dtos.request;

import java.util.*;
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

}
