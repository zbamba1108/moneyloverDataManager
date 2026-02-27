package dev.boog.moneyloverdatamanager.dtos.response;

import dev.boog.moneyloverdatamanager.dtos.response.models.CategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.models.WalletDto;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTransactionDto {

    private Long id;

    private WalletDto wallet;

    private CategoryDto category;

    private BigDecimal amount;

    private String comment;

}
