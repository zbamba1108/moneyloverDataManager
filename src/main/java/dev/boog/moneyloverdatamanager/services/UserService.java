package dev.boog.moneyloverdatamanager.services;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;

public interface UserService<I extends BaseRequestDto, O> extends CRUDService<I, O> {
}
