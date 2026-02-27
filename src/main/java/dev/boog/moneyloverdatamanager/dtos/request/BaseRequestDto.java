package dev.boog.moneyloverdatamanager.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequestDto {

    private Long id;

    private OrderBy orderBy;

    private LocalDateTime searchStartDate;

    private LocalDateTime searchEndDate;

}
