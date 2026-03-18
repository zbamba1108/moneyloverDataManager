package dev.boog.moneyloverdatamanager.dtos.request;

import dev.boog.moneyloverdatamanager.utils.enums.SortingOrder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@SuperBuilder
@Jacksonized
public class BaseRequestDto {

    private final List<String> ids;

    @NotNull
    @Builder.Default
    private final Integer page = 0;

    @NotNull(message = "pageSize cannot be null")
    @Min(value = 1, message = "pageSize cannot be less than 1")
    @Builder.Default
    private final Integer pageSize = 5;

    @Size(min = 2, max = 2)
    private final Long[] dateRange;

    @Builder.Default
    private final String sortingField = "id";

    @Builder.Default
    private final SortingOrder sortingOrder = SortingOrder.ASC;

}
