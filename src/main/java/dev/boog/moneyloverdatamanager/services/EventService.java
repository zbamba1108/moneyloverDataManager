package dev.boog.moneyloverdatamanager.services;

import dev.boog.moneyloverdatamanager.dtos.request.RequestEventDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseEventDto;
import dev.boog.moneyloverdatamanager.repositories.BaseRepository;
import dev.boog.moneyloverdatamanager.repositories.EventRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class EventService implements Service<RequestEventDto, ResponseEventDto, Long> {

    private EventRepository eventRepository;

    @Override
    public void setRepository(BaseRepository<?, Long> repository) {
        this.eventRepository = (EventRepository) repository;
    }

    @Override
    public ResponseEntity<String> create(String userId, RequestEventDto req) {
        return null;
    }

    public ResponseEntity<List<ResponseEventDto>> get(String userId, RequestEventDto req) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseEventDto> update(String userId, RequestEventDto req) {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(String userId, RequestEventDto req) {
        return null;
    }
}
