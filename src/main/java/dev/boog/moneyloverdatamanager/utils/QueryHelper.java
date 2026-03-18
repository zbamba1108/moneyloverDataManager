package dev.boog.moneyloverdatamanager.utils;

import dev.boog.moneyloverdatamanager.repositories.specifications.SpecificationBuilder;
import dev.boog.moneyloverdatamanager.utils.models.QueryRequest;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

public final class QueryHelper {

    public static Path<?> resolvePath(Path<?> path, String key) {
        String[] parts = key.split("\\.");

        for (String part : parts) {
            path = path.get(part);
        }

        return path;
    }

    public static <E> Specification<E> buildSpecification(QueryRequest<E> request) {
        Specification<E> specification = Specification.where(
                SpecificationBuilder.hasUserId(request.userId()));

        if (request.ids() != null && !request.ids().isEmpty()) {
            specification = specification.and(
                    SpecificationBuilder.fieldIn("id", request.ids()));
        }

        if (request.optionalParams() != null && !request.optionalParams().isEmpty()) {
            for (String key : request.optionalParams().keySet()) {
                specification = specification.and(
                        SpecificationBuilder.withFieldEquals(key, request.optionalParams().get(key)));
            }
        }

        if (request.resultFilters() != null
                && request.resultFilters().dateRange() != null
                && request.resultFilters().dateRange().length == 2) {
            specification = specification.and(
                    SpecificationBuilder.withDateBetween(request.resultFilters().dateRange()));
        }

        return specification;
    }
}
