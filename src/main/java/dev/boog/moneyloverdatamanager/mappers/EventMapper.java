package dev.boog.moneyloverdatamanager.mappers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestEventDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseEventDto;
import dev.boog.moneyloverdatamanager.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventMapper /*extends BaseMapper<Event, RequestEventDto, ResponseEventDto>*/ {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);
}
