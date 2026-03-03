package dev.boog.moneyloverdatamanager.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventMapper /*extends BaseMapper<Event, RequestEventDto, ResponseEventDto>*/ {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);
}
