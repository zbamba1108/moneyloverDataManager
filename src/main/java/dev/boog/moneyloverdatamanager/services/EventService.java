package dev.boog.moneyloverdatamanager.services;

import dev.boog.moneyloverdatamanager.dtos.request.RequestEventDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseEventDto;

public interface EventService extends CRUDService<RequestEventDto, ResponseEventDto> {
}
