package dev.boog.moneyloverdatamanager.dtos.response.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDto {

    private int totalRecords;

    private boolean hasMore;
}
