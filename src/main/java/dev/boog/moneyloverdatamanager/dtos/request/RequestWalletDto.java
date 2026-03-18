package dev.boog.moneyloverdatamanager.dtos.request;

import dev.boog.moneyloverdatamanager.exceptions.validations.Write;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Getter
@SuperBuilder
@Jacksonized
public class RequestWalletDto extends BaseRequestDto {

    @NotBlank(groups = Write.class)
    private final String name;
}
