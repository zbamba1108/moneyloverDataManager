package dev.boog.moneyloverdatamanager.services;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DetailsService<I extends BaseRequestDto, O> {

    ResponseEntity<List<O>> details(String userId, I requestDto);
}
