package dev.boog.moneyloverdatamanager.dtos.response;

import dev.boog.moneyloverdatamanager.dtos.response.models.BaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBudgetDto extends BaseEntityDto {

    private String budget;
}
