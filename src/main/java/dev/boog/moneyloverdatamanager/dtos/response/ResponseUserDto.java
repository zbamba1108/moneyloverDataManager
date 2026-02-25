package dev.boog.moneyloverdatamanager.dtos.response;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserDto implements Serializable {

    private Long id;

}
