package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseWalletDto;
import dev.boog.moneyloverdatamanager.entities.Wallet;
import dev.boog.moneyloverdatamanager.repositories.WalletRepository;
import dev.boog.moneyloverdatamanager.services.WalletService;
import dev.boog.moneyloverdatamanager.utils.ServiceHelper;
import dev.boog.moneyloverdatamanager.utils.mappers.PageMapper;
import dev.boog.moneyloverdatamanager.utils.mappers.WalletMapper;
import dev.boog.moneyloverdatamanager.utils.models.QueryResult;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.logging.Logger;


public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public String create(String userId, RequestWalletDto req) {
        Wallet entity = WalletMapper.INSTANCE
                .toEntity(req)
                .userId(userId);
        walletRepository.save(entity);
        return "Wallet created successfully";
    }

    public ResponseDto<ResponseWalletDto> get(String userId, RequestWalletDto req) {
        final QueryResult<Wallet> queryResult = walletRepository
                .search(ServiceHelper
                        .getQueryRequest(Wallet.class, req, userId, false, false));

        return ResponseDto
                .<ResponseWalletDto>builder()
                .data(queryResult
                        .getResults()
                        .stream()
                        .map(WalletMapper.INSTANCE::toResponseDto)
                        .toList())
                .page(PageMapper.INSTANCE.toDto(queryResult.getPage()))
                .build();
    }

    @Override
    public ResponseWalletDto update(String userId, RequestWalletDto req) {
        return WalletMapper.INSTANCE
                .toResponseDto(walletRepository
                        .save(WalletMapper.INSTANCE
                                .toEntity(req)));
    }

    @Override
    public String delete(String userId, RequestWalletDto req) {
        walletRepository.deleteByIds(
                Wallet.class,
                Long.parseLong(userId),
                req.getIds()
                        .stream()
                        .map(Long::parseLong)
                        .toList());
        return "Wallet(s) deleted successfully";
    }

    @Override
    public ResponseDto<ResponseWalletDto> details(String userId, RequestWalletDto req) {
        final QueryResult<Wallet> queryResult = walletRepository
                .search(ServiceHelper
                        .getQueryRequest(Wallet.class, req, userId, true, true));

        return ResponseDto
                .<ResponseWalletDto>builder()
                .data(queryResult
                        .getResults()
                        .stream()
                        .map(WalletMapper.INSTANCE::toResponseDtoDetails)
                        .toList())
                .page(PageMapper.INSTANCE.toDto(queryResult.getPage()))
                .build();
    }
}
