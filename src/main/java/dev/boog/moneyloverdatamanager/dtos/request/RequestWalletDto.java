package dev.boog.moneyloverdatamanager.dtos.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor()
@NoArgsConstructor
public class RequestWalletDto extends BaseRequestDto {

    private String name;
}
