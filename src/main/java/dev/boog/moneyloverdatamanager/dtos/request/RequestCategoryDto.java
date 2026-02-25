package dev.boog.moneyloverdatamanager.dtos.request;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestCategoryDto {

    private Long id;

    private String name;

    private Long userId;

    private Integer type;

    private Long parentId;
}
