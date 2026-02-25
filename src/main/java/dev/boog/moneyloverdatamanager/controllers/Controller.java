package dev.boog.moneyloverdatamanager.controllers;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Controller<I, O> {

    ResponseEntity<String> create(String userId, I req);

    ResponseEntity<List<O>> get(String userId, String query);

    ResponseEntity<O> update(String userId, I req);

    ResponseEntity<String> delete(String userId, I req);
}
