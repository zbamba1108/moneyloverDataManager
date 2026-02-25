package dev.boog.moneyloverdatamanager.mappers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestUserDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseUserDto;
import dev.boog.moneyloverdatamanager.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper extends BaseMapper<User, RequestUserDto, ResponseUserDto> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

}
