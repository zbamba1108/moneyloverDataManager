package dev.boog.moneyloverdatamanager.services;

import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;

public interface TransactionService extends CRUDService<RequestTransactionDto, ResponseTransactionDto>, DetailsService<RequestTransactionDto, ResponseTransactionDto> {
}
