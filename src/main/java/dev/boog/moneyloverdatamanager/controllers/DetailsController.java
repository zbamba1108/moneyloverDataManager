package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface DetailsController<I extends BaseRequestDto, O> {

    ResponseEntity<ResponseDto<O>> details(String userId, I req);
}
