package dev.boog.moneyloverdatamanager.services;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.models.BaseEntityDto;

public interface CRUDService<I extends BaseRequestDto, O extends BaseEntityDto> {

    String create(String userId, I req);

    ResponseDto<O> get(String userId, I req);

    O update(String userId, I req);

    String delete(String userId, I req);
}
