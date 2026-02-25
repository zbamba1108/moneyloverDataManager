package dev.boog.moneyloverdatamanager.dtos.request;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserDto implements Serializable {

    private Long id;

    private String email;

    private String password;
}
