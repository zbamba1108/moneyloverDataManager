package dev.boog.moneyloverdatamanager.dtos.request;


import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RequestCategoryDto extends BaseRequestDto {

    private String name;

    private Integer type;

    private Long parentId;
}
