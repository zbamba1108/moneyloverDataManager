package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.models.PageDto;
import dev.boog.moneyloverdatamanager.entities.Transaction;
import dev.boog.moneyloverdatamanager.repositories.TransactionRepository;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryResult;
import dev.boog.moneyloverdatamanager.services.TransactionService;
import dev.boog.moneyloverdatamanager.services.utils.QueryRequestBuilder;
import dev.boog.moneyloverdatamanager.services.utils.TransactionQueryHelper;
import dev.boog.moneyloverdatamanager.utils.mappers.TransactionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final TransactionQueryHelper queryHelper;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  TransactionQueryHelper queryHelper) {
        this.transactionRepository = transactionRepository;
        this.queryHelper = queryHelper;
    }

    @Override
    public String create(Long userId, RequestTransactionDto req) {
        Transaction entity = TransactionMapper.INSTANCE
                .toEntity(req)
                .userId(userId);
        transactionRepository.save(entity);
        return "Transaction created successfully";
    }

    public ResponseDto<ResponseTransactionDto> get(Long userId, RequestTransactionDto req) {
        QueryRequest<Transaction> queryRequest = QueryRequestBuilder
                .build(queryHelper, req, userId);

        QueryResult<Transaction> queryResult = transactionRepository
                .findAll(queryRequest);

        return ResponseDto
                .<ResponseTransactionDto>builder()
                .data(queryResult
                        .results()
                        .stream()
                        .map(TransactionMapper.INSTANCE::toResponseDto)
                        .toList())
                .page(PageDto
                        .builder()
                        .hasNext(queryResult.page().hasNext())
                        .records(queryResult.page().records())
                        .build())
                .build();
    }

    @Override
    public ResponseTransactionDto update(Long userId, RequestTransactionDto req) {
        return TransactionMapper.INSTANCE
                .toResponseDto(transactionRepository
                        .save(TransactionMapper.INSTANCE
                                .toEntity(req)));
    }

    @Override
    public String delete(Long userId, RequestTransactionDto req) {
        transactionRepository.delete(TransactionMapper.INSTANCE.toEntity(req));
        return "Transaction deleted successfully";
    }

    @Override
    public ResponseDto<ResponseTransactionDto> details(Long userId, RequestTransactionDto req) {
        QueryRequest<Transaction> queryRequest = QueryRequestBuilder
                .build(queryHelper, req, userId);

        QueryResult<Long> queryResult = transactionRepository
                .findAllAndSelectIds(queryRequest);

        List<Transaction> results = transactionRepository
                .findAllByIdIn(queryResult.results());

        return ResponseDto.
                <ResponseTransactionDto>builder()
                .data(results
                        .stream()
                        .map(TransactionMapper.INSTANCE::toResponseDtoDetails)
                        .toList())
                .page(PageDto
                        .builder()
                        .hasNext(queryResult.page().hasNext())
                        .records(queryResult.page().records())
                        .build())
                .build();
    }
}
