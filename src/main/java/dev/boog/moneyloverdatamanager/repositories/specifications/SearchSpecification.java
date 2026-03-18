package dev.boog.moneyloverdatamanager.repositories.specifications;

import dev.boog.moneyloverdatamanager.utils.Constants;
import dev.boog.moneyloverdatamanager.utils.SearchQueryHelper;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.util.List;

public final class SearchSpecification {

    private SearchSpecification() {
        throw new UnsupportedOperationException(Constants.Messages.UTILITY_CLASS);
    }

    public static <E> Specification<E> hasUserId(String userId) {
        return (root, query, cb) -> cb.equal(root.get("user").get("id"), userId);
    }

    public static <E> Specification<E> fieldIn(String field, List<?> values) {
        return (root, query, cb) ->
                values == null || values.isEmpty()
                ? null
                : root.get(field).in(values);
    }

    public static <E> Specification<E> withFieldEquals(String field, String value) {
        return (root, query, cb) ->
                cb.equal(SearchQueryHelper.resolvePath(root, field), value);
    }

    public static <E> Specification<E> withDateBetween(String[] range) {
        if (range == null || range.length != 2) {
            return null;
        }
        return (root, query, cb) -> cb.between(
                root.get("createdAt"),
                new Timestamp(Long.parseLong(range[0])),
                new Timestamp(Long.parseLong(range[1]))
        );
    }
}
