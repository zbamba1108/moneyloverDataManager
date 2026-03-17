package dev.boog.moneyloverdatamanager.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.boog.moneyloverdatamanager.utils.enums.SortingOrder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequestDto {

    private List<String> ids;

    @NotNull
    private Integer page;

    @NotNull(message = "pageSize cannot be null")
    @Min(value = 1, message = "pageSize cannot be less than 1")
    private Integer pageSize;

    private String[] dateRange;

    private String sortingField;

    private SortingOrder sortingOrder;

}
