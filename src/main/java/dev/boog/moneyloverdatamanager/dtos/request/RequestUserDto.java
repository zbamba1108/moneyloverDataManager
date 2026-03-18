package dev.boog.moneyloverdatamanager.dtos.request;

import dev.boog.moneyloverdatamanager.exceptions.validations.Write;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @Email(groups = Write.class)
    private final String email;

    @NotBlank(groups = Write.class)
    private final String password;
}
