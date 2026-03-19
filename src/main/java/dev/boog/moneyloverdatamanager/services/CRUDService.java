package dev.boog.moneyloverdatamanager.services;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.models.BaseEntityDto;

public interface CRUDService<I extends BaseRequestDto, O extends BaseEntityDto> {

    String create(Long userId, I req);

    ResponseDto<O> get(Long userId, I req);

    O update(Long userId, I req);

    void delete(Long id, Long userId);
}
