package dev.boog.moneyloverdatamanager.dtos.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
//@AllArgsConstructor
public class RequestBudgetDto extends BaseRequestDto {
}
