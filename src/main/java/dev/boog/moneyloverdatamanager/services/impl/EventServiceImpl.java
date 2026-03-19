package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestEventDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseEventDto;
import dev.boog.moneyloverdatamanager.repositories.EventRepository;
import dev.boog.moneyloverdatamanager.services.EventService;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public String create(Long userId, RequestEventDto req) {
        return null;
    }

    public ResponseDto<ResponseEventDto> get(Long userId, RequestEventDto req) {
        return null;
    }

    @Override
    public ResponseEventDto update(Long userId, RequestEventDto req) {
        return null;
    }

    @Override
    public void delete(Long id, Long userId) {

    }
}
