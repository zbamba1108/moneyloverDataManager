package dev.boog.moneyloverdatamanager.dtos.response.models;

import lombok.*;
import lombok.experimental.*;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntityDto {

    private Long id;

    private Long createdAt;

}
