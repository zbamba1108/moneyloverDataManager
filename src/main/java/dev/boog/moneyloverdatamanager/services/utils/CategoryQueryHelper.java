package dev.boog.moneyloverdatamanager.services.utils;

import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.entities.Category;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CategoryQueryHelper extends QueryHelper<Category, RequestCategoryDto> {

    public CategoryQueryHelper() {
        super(Category.class);
    }

    @Override
    public Map<String, Object> mapOptionalParams(RequestCategoryDto req) {
        Map<String, Object> params = new HashMap<>();

        if (req == null) {
            return params;
        }

        if (req.getType() != null) {
            params.put("type", req.getType());
        }
        if (req.getParentId() != null) {
            params.put("parent.id", req.getParentId());
        }

        return params;
    }
}
