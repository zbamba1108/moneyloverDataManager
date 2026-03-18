package dev.boog.moneyloverdatamanager.dtos.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Getter
@SuperBuilder
@Jacksonized
public class RequestUserDto extends BaseRequestDto implements Serializable {

    private final String email;

    private final String password;
}
