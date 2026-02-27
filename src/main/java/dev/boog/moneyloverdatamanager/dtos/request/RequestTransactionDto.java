package dev.boog.moneyloverdatamanager.dtos.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RequestTransactionDto extends BaseRequestDto {

    private Long walletId;

    private Long eventId;

    private Long categoryId;

    private BigDecimal amount;

    private String comment;

}
