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
    @Mapping( target="parent.id", source="dto.parentId")
    Category toEntity(RequestCategoryDto dto, Long userId);

    @Mappings({
            @Mapping( target="parent.id", source="dto.parentId")
    })
    void updateEntity(RequestCategoryDto dto, @MappingTarget Category category);

    @Mappings({
            @Mapping(target = "id", source = "c.id"),
            @Mapping(target = "userId", source = "c.userId"),
            @Mapping(target = "createdAt", source = "c.createdAt"),
            @Mapping( target="parent.id", source="dto.parentId"),
            @Mapping( target="name", source="dto.name"),
            @Mapping( target="type", source="dto.type")
    })
    Category toUpdatedEntity(Category c, RequestCategoryDto dto);

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
