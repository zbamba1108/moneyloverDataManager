package dev.boog.moneyloverdatamanager.dtos.response.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntityDto {

    private Long id;

    private Long createdAt;

}
