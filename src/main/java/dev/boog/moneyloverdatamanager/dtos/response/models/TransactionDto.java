package dev.boog.moneyloverdatamanager.dtos.response.models;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private Long id;

    private CategoryDto category;

    private BigDecimal amount;

    private String comment;
}
