package dev.boog.moneyloverdatamanager.dtos.response;

import lombok.*;
import lombok.experimental.*;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseDto {

    private Long id;

    private Long createdAt;

}
