package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.models.PageDto;
import dev.boog.moneyloverdatamanager.entities.Transaction;
import dev.boog.moneyloverdatamanager.repositories.TransactionRepository;
import dev.boog.moneyloverdatamanager.services.TransactionService;
import dev.boog.moneyloverdatamanager.utils.ServiceHelper;
import dev.boog.moneyloverdatamanager.utils.mappers.TransactionMapper;
import dev.boog.moneyloverdatamanager.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.utils.models.QueryResult;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public String create(String userId, RequestTransactionDto req) {
        Transaction entity = TransactionMapper.INSTANCE
                .toEntity(req)
                .userId(userId);
        transactionRepository.save(entity);
        return "Transaction created successfully";
    }

    public ResponseDto<ResponseTransactionDto> get(String userId, RequestTransactionDto req) {
        QueryRequest<Transaction> queryRequest = ServiceHelper
                .getQueryRequest2(req, userId);

        QueryResult<Transaction> queryResult = transactionRepository
                .findAll(Transaction.class, queryRequest);

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
                        .records(queryResult.results().size())
                        .build())
                .build();
    }

    @Override
    public ResponseTransactionDto update(String userId, RequestTransactionDto req) {
        return TransactionMapper.INSTANCE
                .toResponseDto(transactionRepository
                        .save(TransactionMapper.INSTANCE
                                .toEntity(req)));
    }

    @Override
    public String delete(String userId, RequestTransactionDto req) {
        transactionRepository.delete(TransactionMapper.INSTANCE.toEntity(req));
        return "Transaction deleted successfully";
    }

    @Override
    public ResponseDto<ResponseTransactionDto> details(String userId, RequestTransactionDto req) {
        QueryRequest<Transaction> queryRequest = ServiceHelper.getQueryRequest2(req, userId);

        QueryResult<Long> queryResult = transactionRepository
                .findAllAndSelectIds(Transaction.class, queryRequest);

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
                        .records(queryResult.results().size())
                        .build())
                .build();
    }
}
