package dev.boog.moneyloverdatamanager.utils.models;

import lombok.Builder;

import java.util.HashMap;
import java.util.List;

@Builder
public record QueryRequest<E>( // do not remove E

        String userId,

        List<String> ids,

        HashMap<String, String> optionalParams,

        ResultFilters resultFilters) {

}
