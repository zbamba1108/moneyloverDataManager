package dev.boog.moneyloverdatamanager.dtos.request;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Getter
@SuperBuilder
@Jacksonized
public class RequestTransactionDto extends BaseRequestDto {

    private final Long walletId;

    private final Long eventId;

    private final Long categoryId;

    private final BigDecimal amount;

    private final String comment;

}
