package dev.boog.moneyloverdatamanager.dtos.response;

import dev.boog.moneyloverdatamanager.dtos.response.models.TransactionDto;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWalletDto {

    private Long id;

    private String walletName;

    private String userId;

    private List<TransactionDto> transactionList;
}
