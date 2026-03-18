package dev.boog.moneyloverdatamanager.services;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.models.BaseEntityDto;

public interface DetailsService<I extends BaseRequestDto, O extends BaseEntityDto> {

    ResponseDto<O> details(Long userId, I requestDto);
}
