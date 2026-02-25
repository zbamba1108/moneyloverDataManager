package dev.boog.moneyloverdatamanager.dtos.response.models;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {

    private Long id;

    private String walletName;

}
