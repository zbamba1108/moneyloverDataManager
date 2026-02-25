package dev.boog.moneyloverdatamanager.dtos.response;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCategoryDto {

    private Long id;

    private String name;

    private Long userId;

    private Integer type;

    private Long parentId;
}
