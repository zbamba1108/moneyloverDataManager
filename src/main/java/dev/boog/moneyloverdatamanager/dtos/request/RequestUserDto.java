package dev.boog.moneyloverdatamanager.dtos.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserDto extends BaseRequestDto implements Serializable {

    private Long id;

    private String email;

    private String password;
}
