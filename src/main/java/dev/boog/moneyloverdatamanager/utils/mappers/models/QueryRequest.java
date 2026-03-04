package dev.boog.moneyloverdatamanager.utils.mappers.models;

import dev.boog.moneyloverdatamanager.utils.ResultFilters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryRequest {

    private Class<?> clazz;

    private String userId;

    private List<String> ids;

    private HashMap<String, String> optionalParams;

    private ResultFilters resultFilters;

    private boolean mapDetails;

    private boolean hasChildren;

}
