package dev.boog.moneyloverdatamanager.controllers;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Controller<I, O, ID> {

    ResponseEntity<String> create(String userId, I req);

    ResponseEntity<List<O>> get(String userId, ID id);

    ResponseEntity<List<O>> getAll(String userId);

    ResponseEntity<O> update(String userId, I req);

    ResponseEntity<String> delete(String userId, I req);
}
