package dev.boog.moneyloverdatamanager.utils.mappers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseCategoryDto;
import dev.boog.moneyloverdatamanager.entities.Category;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper extends BaseEntityMapper<Category, RequestCategoryDto, ResponseCategoryDto> {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Override
    @Mapping( target="parent.id", source="parentId")
    Category toEntity(RequestCategoryDto dto);

    @Override
    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "timestampToLong"),
            @Mapping( target="parentId", source="parent.id")
    })
    ResponseCategoryDto toResponseDto(Category entity);

    @AfterMapping
    default Category postProcessing(@MappingTarget Category entity) {
        if (entity.getParent() != null && entity.getParent().getId() == null) {
            entity.setParent(null);
        }

        return entity;
    }

}
