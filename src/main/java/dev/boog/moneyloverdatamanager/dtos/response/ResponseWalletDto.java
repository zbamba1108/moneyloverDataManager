package dev.boog.moneyloverdatamanager.dtos.response;

import dev.boog.moneyloverdatamanager.dtos.response.models.TransactionDto;
import lombok.*;

import java.util.List;
import lombok.experimental.*;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWalletDto extends BaseResponseDto {

    private Long id;

    private String walletName;

    private String userId;

    private List<TransactionDto> transactionList;
}
