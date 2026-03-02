package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DetailsController<I extends BaseRequestDto, O> {

    ResponseEntity<List<O>> details(String userId, I req);
}
