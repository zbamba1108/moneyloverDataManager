package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.models.PageDto;
import dev.boog.moneyloverdatamanager.entities.Wallet;
import dev.boog.moneyloverdatamanager.repositories.WalletRepository;
import dev.boog.moneyloverdatamanager.services.WalletService;
import dev.boog.moneyloverdatamanager.utils.ServiceHelper;
import dev.boog.moneyloverdatamanager.utils.mappers.WalletMapper;
import dev.boog.moneyloverdatamanager.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.utils.models.QueryResult;

import java.util.List;


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
        QueryRequest<Wallet> queryRequest = ServiceHelper.getQueryRequest2(req, userId);

        QueryResult<Wallet> queryResult = walletRepository
                .findAll(Wallet.class, queryRequest);

        return ResponseDto
                .<ResponseWalletDto>builder()
                .data(queryResult
                        .results()
                        .stream()
                        .map(WalletMapper.INSTANCE::toResponseDto)
                        .toList())
                .page(PageDto.builder()
                        .hasNext(queryResult.page().hasNext())
                        .records(queryResult.results().size())
                        .build())
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
        QueryRequest<Wallet> queryRequest = ServiceHelper.getQueryRequest2(req, userId);

        QueryResult<Long> pagedResults = walletRepository
                .findAllAndSelectIds(Wallet.class, queryRequest);

        List<Wallet> results = walletRepository
                .findAllByIdIn(pagedResults.results());

        return ResponseDto
                .<ResponseWalletDto>builder()
                .data(results
                        .stream()
                        .map(WalletMapper.INSTANCE::toResponseDtoDetails)
                        .toList())
                .page(PageDto.builder()
                        .hasNext(pagedResults.page().hasNext())
                        .records(results.size())
                        .build())
                .build();
    }
}
