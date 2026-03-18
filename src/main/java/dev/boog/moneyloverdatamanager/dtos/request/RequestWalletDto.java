package dev.boog.moneyloverdatamanager.dtos.request;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Getter
@SuperBuilder
@Jacksonized
public class RequestWalletDto extends BaseRequestDto {

    private final String name;
}
