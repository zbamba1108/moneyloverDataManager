package dev.boog.moneyloverdatamanager.repositories.utils.models;

import lombok.Builder;

import java.util.List;

@Builder
public record QueryResult<E>(List<E> results, Page page) {

}
