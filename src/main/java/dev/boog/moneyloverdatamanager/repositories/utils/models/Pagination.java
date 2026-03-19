package dev.boog.moneyloverdatamanager.repositories.utils.models;

import lombok.Builder;

@Builder
public record Pagination(Integer page, Integer pageSize) {
}
