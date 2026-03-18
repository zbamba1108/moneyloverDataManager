package dev.boog.moneyloverdatamanager.dtos.request;


import dev.boog.moneyloverdatamanager.exceptions.validations.Read;
import dev.boog.moneyloverdatamanager.exceptions.validations.Write;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Getter
@SuperBuilder
@Jacksonized
public class RequestCategoryDto extends BaseRequestDto {

    @NotBlank(groups = Write.class)
    private final String name;

    @Min(value = 0, groups = {Write.class, Read.class})
    @Max(value = 1, groups = {Write.class, Read.class})
    @NotNull(groups = Write.class)
    private final Integer type;

    @Min(value = 1, groups = {Write.class, Read.class})
    private final Long parentId;
}
