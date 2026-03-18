package dev.boog.moneyloverdatamanager.dtos.request;

import dev.boog.moneyloverdatamanager.exceptions.validations.FieldsDependencies;
import dev.boog.moneyloverdatamanager.exceptions.validations.FieldsDependency;
import dev.boog.moneyloverdatamanager.exceptions.validations.Read;
import dev.boog.moneyloverdatamanager.utils.enums.SortingOrder;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@SuperBuilder
@Jacksonized
@FieldsDependencies({
        @FieldsDependency(message = "also endDate must exists", fieldName = "startDate", dependsOn = "endDate"),
        @FieldsDependency(message = "also startDate must exist", fieldName = "endDate", dependsOn = "startDate")
})

public class BaseRequestDto {

    @Size(min = 1, max = 10, groups = {Read.class})
    private final List<Long> ids;

    @NotNull(groups = Read.class)
    @Builder.Default
    private final Integer page = 0;

    @NotNull(message = "pageSize cannot be null",  groups = Read.class)
    @Min(value = 1, message = "pageSize cannot be less than 1",  groups = Read.class)
    @Builder.Default
    private final Integer pageSize = 5;

    @Positive(groups = Read.class)
    private final Long startDate;

    @Positive(groups = Read.class)
    private final Long endDate;

    @NotBlank(groups = {Read.class})
    @Builder.Default
    private final String sortingField = "id";

    @Builder.Default
    private final SortingOrder sortingOrder = SortingOrder.ASC;

}
