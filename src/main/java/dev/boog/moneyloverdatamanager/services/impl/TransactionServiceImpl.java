package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.entities.Transaction;
import dev.boog.moneyloverdatamanager.repositories.TransactionRepository;
import dev.boog.moneyloverdatamanager.services.TransactionService;
import dev.boog.moneyloverdatamanager.utils.ServiceHelper;
import dev.boog.moneyloverdatamanager.utils.mappers.PageMapper;
import dev.boog.moneyloverdatamanager.utils.mappers.TransactionMapper;
import dev.boog.moneyloverdatamanager.utils.models.QueryResult;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.logging.Logger;

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
        final QueryResult<Transaction> queryResult = transactionRepository
                .search(ServiceHelper
                        .getQueryRequest(Transaction.class, req, userId, false, false));

        return ResponseDto
                .<ResponseTransactionDto>builder()
                .data(queryResult
                        .getResults()
                        .stream()
                        .map(TransactionMapper.INSTANCE::toResponseDto)
                        .toList())
                .page(PageMapper.INSTANCE.toDto(queryResult.getPage()))
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
        final QueryResult<Transaction> queryResult = transactionRepository
                .search(ServiceHelper
                        .getQueryRequest(Transaction.class, req, userId, true, false));

        return ResponseDto
                .<ResponseTransactionDto>builder()
                .data(queryResult
                        .getResults()
                        .stream()
                        .map(TransactionMapper.INSTANCE::toResponseDtoDetails)
                        .toList())
                .page(PageMapper.INSTANCE.toDto(queryResult.getPage()))
                .build();
    }
}
