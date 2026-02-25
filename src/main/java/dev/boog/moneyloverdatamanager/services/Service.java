package dev.boog.moneyloverdatamanager.services;

import dev.boog.moneyloverdatamanager.repositories.BaseRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Service<I, O, ID extends Number> {

    void setRepository(BaseRepository<?, ID> repository);

    ResponseEntity<String> create(String userId, I req);

    ResponseEntity<List<O>> get(String userId, String query);

    ResponseEntity<O> update(String userId, I req);

    ResponseEntity<String> delete(String userId, I req);
}
