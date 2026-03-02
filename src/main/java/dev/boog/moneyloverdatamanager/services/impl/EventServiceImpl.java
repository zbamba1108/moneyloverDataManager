package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestEventDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseEventDto;
import dev.boog.moneyloverdatamanager.repositories.EventRepository;
import dev.boog.moneyloverdatamanager.services.EventService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class EventServiceImpl implements EventService<RequestEventDto, ResponseEventDto> {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
