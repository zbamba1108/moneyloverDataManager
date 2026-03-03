package dev.boog.moneyloverdatamanager.dtos.response;

import dev.boog.moneyloverdatamanager.dtos.response.models.BaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ResponseUserDto extends BaseEntityDto {

    private String email;
}
