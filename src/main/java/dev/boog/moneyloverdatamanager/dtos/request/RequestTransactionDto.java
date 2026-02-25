package dev.boog.moneyloverdatamanager.dtos.request;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestTransactionDto {

    private Long id;

    private Long walletId;

    private Long userId;

    private Long eventId;

    private Long categoryId;

    private BigDecimal amount;

    private String comment;

}
