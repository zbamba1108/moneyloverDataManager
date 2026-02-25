package dev.boog.moneyloverdatamanager.mappers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseCategoryDto;
import dev.boog.moneyloverdatamanager.entities.Category;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper extends BaseMapper<Category, RequestCategoryDto, ResponseCategoryDto> {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Override
    @Mapping( target="parent.id", source="parentId", ignore = true)
    Category toEntity(RequestCategoryDto dto);

    @Override
    @Mapping( target="parentId", source="parent.id", ignore = true)
    ResponseCategoryDto toResponseDto(Category entity);

    @AfterMapping
    default Category postProcessing(@MappingTarget Category entity) {
        if (entity.getParent() != null && entity.getParent().getId() == null) {
            entity.setParent(null);
        }

        return entity;
    }

}
