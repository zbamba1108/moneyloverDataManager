package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.models.PageDto;
import dev.boog.moneyloverdatamanager.entities.Wallet;
import dev.boog.moneyloverdatamanager.repositories.WalletRepository;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryResult;
import dev.boog.moneyloverdatamanager.services.WalletService;
import dev.boog.moneyloverdatamanager.services.utils.QueryHelper;
import dev.boog.moneyloverdatamanager.services.utils.QueryRequestBuilder;
import dev.boog.moneyloverdatamanager.services.utils.WalletQueryHelper;
import dev.boog.moneyloverdatamanager.utils.mappers.WalletMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    private final WalletQueryHelper queryHelper;

    public WalletServiceImpl(WalletRepository walletRepository, WalletQueryHelper queryHelper) {
        this.walletRepository = walletRepository;
        this.queryHelper = queryHelper;
    }

    @Override
    public String create(Long userId, RequestWalletDto req) {
        Wallet entity = WalletMapper.INSTANCE
                .toEntity(req)
                .userId(userId);
        walletRepository.save(entity);
        return "Wallet created successfully";
    }

    public ResponseDto<ResponseWalletDto> get(Long userId, RequestWalletDto req) {
        QueryRequest<Wallet> queryRequest = QueryRequestBuilder.build(queryHelper, req, userId);

        QueryResult<Wallet> queryResult = walletRepository
                .findAll(queryRequest);

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
    public ResponseWalletDto update(Long userId, RequestWalletDto req) {
        return WalletMapper.INSTANCE
                .toResponseDto(walletRepository
                        .save(WalletMapper.INSTANCE
                                .toEntity(req)));
    }

    @Override
    public String delete(Long userId, RequestWalletDto req) {
        walletRepository.deleteByIds(
                Wallet.class,
                userId,
                req.getIds());
        return "Wallet(s) deleted successfully";
    }

    @Override
    public ResponseDto<ResponseWalletDto> details(Long userId, RequestWalletDto req) {
        QueryRequest<Wallet> queryRequest = QueryRequestBuilder.build(queryHelper, req, userId);

        QueryResult<Long> pagedResults = walletRepository
                .findAllAndSelectIds(queryRequest);

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
