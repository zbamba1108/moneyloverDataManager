package dev.boog.moneyloverdatamanager.utils.models;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
public record QueryResult<E>(List<E> results, Page page) {

}
