package dev.boog.moneyloverdatamanager.mappers;

import dev.boog.moneyloverdatamanager.dtos.request.*;
import dev.boog.moneyloverdatamanager.dtos.response.*;
import dev.boog.moneyloverdatamanager.entities.*;
import java.sql.*;
import java.util.List;
import java.util.Objects;

import org.mapstruct.*;

public interface BaseMapper<E extends BaseEntity,
                            I extends BaseRequestDto,
                            O extends BaseResponseDto> {
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
    default long timestampToLong(Timestamp timestamp) {
        return timestamp.getTime();
    }

    @Named("getFirstId")
    default long getFirstId(List<String> ids) {
        return Long.parseLong(Objects.requireNonNull(ids.stream().findFirst().orElse(null)));
    }
}
