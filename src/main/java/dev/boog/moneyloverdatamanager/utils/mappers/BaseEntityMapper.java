package dev.boog.moneyloverdatamanager.utils.mappers;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.dtos.response.models.BaseEntityDto;
import dev.boog.moneyloverdatamanager.entities.BaseEntity;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public interface BaseEntityMapper<E extends BaseEntity,
                            I extends BaseRequestDto,
                            O extends BaseEntityDto> {
    @Mappings(
            {
                    @Mapping(target = "id", source = "ids", qualifiedByName = "getFirstId")
            }
    )
    E toEntity(I req);


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

    @Named("getFirstId")
    default Long getFirstId(List<Long> ids) {
        return ids.stream().findFirst().orElse(null);
    }
}
