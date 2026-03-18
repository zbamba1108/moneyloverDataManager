package dev.boog.moneyloverdatamanager.dtos.request;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Getter
@SuperBuilder
@Jacksonized
public class RequestCategoryDto extends BaseRequestDto {

    private final String name;

    private final Integer type;

    private final Long parentId;
}
