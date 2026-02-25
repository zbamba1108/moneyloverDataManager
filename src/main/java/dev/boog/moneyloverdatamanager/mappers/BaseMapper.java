package dev.boog.moneyloverdatamanager.mappers;

public interface BaseMapper<E, I, O> {

    E toEntity(I req);

    O toResponseDto(E entity);
}
