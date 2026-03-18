package dev.boog.moneyloverdatamanager.utils.mappers;

import dev.boog.moneyloverdatamanager.dtos.response.models.PageDto;
import dev.boog.moneyloverdatamanager.repositories.utils.models.Page;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PageMapper {

    PageMapper INSTANCE = Mappers.getMapper(PageMapper.class);

    PageDto toDto(Page page);
}
