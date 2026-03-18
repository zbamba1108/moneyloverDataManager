package dev.boog.moneyloverdatamanager.services.utils;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.entities.Category;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CategoryQueryHelper extends QueryHelper<Category, RequestCategoryDto> {

    public CategoryQueryHelper() {
        super(Category.class);
    }

    @Override
    public HashMap<String, String> mapOptionalParams(RequestCategoryDto req) {
        HashMap<String, String> params = new HashMap<>();

        if (req == null) {
            return params;
        }

        if (req.getType() != null) {
            params.put("type", String.valueOf(req.getType()));
        }
        if (req.getParentId() != null) {
            params.put("parent.id", String.valueOf(req.getParentId()));
        }

        return params;
    }
}
