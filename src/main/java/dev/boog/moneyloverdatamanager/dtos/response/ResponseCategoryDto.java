package dev.boog.moneyloverdatamanager.dtos.response;


import lombok.*;
import lombok.experimental.*;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCategoryDto extends BaseResponseDto {

    private Long id;

    private String name;

    private Integer type;

    private Long parentId;
}
