package dev.boog.moneyloverdatamanager.utils.mappers;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.dtos.response.models.BaseEntityDto;
import dev.boog.moneyloverdatamanager.entities.BaseEntity;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.sql.Timestamp;
import java.util.List;

public interface BaseEntityMapper<E extends BaseEntity,
                            I extends BaseRequestDto,
                            O extends BaseEntityDto> {

    E toEntity(I req, Long userId);

    @Mappings(
            {
                    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "timestampToLong")
            }
    )
    O toResponseDto(E entity);

    @Mappings(
            {
                    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "timestampToLong")
            }
    )
    O toResponseDtoDetails(E entity);

    @Named("timestampToLong")
    default Long timestampToLong(Timestamp timestamp) {
        return timestamp.getTime();
    }
}
