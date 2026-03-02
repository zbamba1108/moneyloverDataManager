package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Controller<I extends BaseRequestDto, O> {

    ResponseEntity<String> create(String userId, I req);

    ResponseEntity<List<O>> get(String userId, I req);

    ResponseEntity<List<O>> details(String userId, I req);

    ResponseEntity<O> update(String userId, I req);

    ResponseEntity<String> delete(String userId, I req);
}
