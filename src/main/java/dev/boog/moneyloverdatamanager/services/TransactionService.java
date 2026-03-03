package dev.boog.moneyloverdatamanager.services;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.dtos.response.models.BaseEntityDto;

public interface TransactionService<I extends BaseRequestDto, O extends BaseEntityDto> extends CRUDService<I, O>, DetailsService<I, O> {
}
