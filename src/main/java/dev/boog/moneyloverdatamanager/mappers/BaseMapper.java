package dev.boog.moneyloverdatamanager.mappers;

import dev.boog.moneyloverdatamanager.dtos.request.*;
import dev.boog.moneyloverdatamanager.dtos.response.*;
import dev.boog.moneyloverdatamanager.entities.*;
import java.sql.*;
import org.mapstruct.*;

public interface BaseMapper<E extends BaseEntity,
                            I extends BaseRequestDto,
                            O extends BaseResponseDto> {

    E toEntity(I req);


    @Mappings(
            {
                    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "timestampToLong")
            }
    )
    O toResponseDto(E entity);

    @Named("timestampToLong")
    default long timestampToLong(Timestamp timestamp) {
        return timestamp.getTime();
    }
}
