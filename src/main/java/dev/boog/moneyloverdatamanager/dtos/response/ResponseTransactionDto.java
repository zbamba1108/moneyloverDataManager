package dev.boog.moneyloverdatamanager.dtos.response;

import dev.boog.moneyloverdatamanager.dtos.response.models.CategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.models.WalletDto;
import lombok.*;

import java.math.BigDecimal;
import lombok.experimental.*;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTransactionDto extends BaseResponseDto {

    private Long id;

    private WalletDto wallet;

    private CategoryDto category;

    private BigDecimal amount;

    private String comment;

}
