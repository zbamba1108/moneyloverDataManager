package dev.boog.moneyloverdatamanager.services;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.models.BaseEntityDto;
import org.springframework.http.ResponseEntity;

public interface CRUDService<I extends BaseRequestDto, O extends BaseEntityDto> {

    ResponseEntity<String> create(String userId, I req);

    ResponseEntity<ResponseDto<O>> get(String userId, I req);

    ResponseEntity<O> update(String userId, I req);

    ResponseEntity<String> delete(String userId, I req);
}
