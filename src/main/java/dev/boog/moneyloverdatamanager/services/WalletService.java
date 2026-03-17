package dev.boog.moneyloverdatamanager.services;

import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseWalletDto;

public interface WalletService extends CRUDService<RequestWalletDto, ResponseWalletDto>, DetailsService<RequestWalletDto, ResponseWalletDto> {
}
