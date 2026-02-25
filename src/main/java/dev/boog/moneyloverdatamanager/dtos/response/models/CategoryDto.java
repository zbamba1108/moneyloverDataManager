package dev.boog.moneyloverdatamanager.dtos.response.models;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    private String name;

    private Integer type;
}
