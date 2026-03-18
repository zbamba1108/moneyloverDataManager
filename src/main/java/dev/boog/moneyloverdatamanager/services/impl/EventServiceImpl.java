package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestEventDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseEventDto;
import dev.boog.moneyloverdatamanager.repositories.EventRepository;
import dev.boog.moneyloverdatamanager.services.EventService;

public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public String create(String userId, RequestEventDto req) {
        return null;
    }

    public ResponseDto<ResponseEventDto> get(String userId, RequestEventDto req) {
        return null;
    }

    @Override
    public ResponseEventDto update(String userId, RequestEventDto req) {
        return null;
    }

    @Override
    public String delete(String userId, RequestEventDto req) {
        return null;
    }
}
