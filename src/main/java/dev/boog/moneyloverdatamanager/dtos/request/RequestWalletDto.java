package dev.boog.moneyloverdatamanager.dtos.request;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestWalletDto {

    private Long id;

    private String walletName;

    private Long userId;
}
