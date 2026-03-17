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

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(TransactionServiceImpl.class));

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public ResponseEntity<String> create(String userId, RequestTransactionDto req) {
        try {
            Transaction entity = TransactionMapper.INSTANCE
                    .toEntity(req)
                    .userId(userId);
            transactionRepository.save(entity);
            return new ResponseEntity<>("Transaction created successfully", HttpStatus.CREATED);
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseDto<ResponseTransactionDto>> get(String userId, RequestTransactionDto req) {
        try {
            final QueryResult<Transaction> queryResult = transactionRepository
                    .search(ServiceHelper
                            .getQueryRequest(Transaction.class, req, userId, false, false));

            final ResponseDto<ResponseTransactionDto> responseDto = ResponseDto
                    .<ResponseTransactionDto>builder()
                    .data(queryResult
                            .getResults()
                            .stream()
                            .map(TransactionMapper.INSTANCE::toResponseDto)
                            .toList())
                    .page(PageMapper.INSTANCE.toDto(queryResult.getPage()))
                    .build();

            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseTransactionDto> update(String userId, RequestTransactionDto req) {
        try {
            ResponseTransactionDto responseDto = TransactionMapper.INSTANCE
                    .toResponseDto(transactionRepository
                            .save(TransactionMapper.INSTANCE
                                    .toEntity(req)));
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> delete(String userId, RequestTransactionDto req) {
        try {
            transactionRepository.delete(TransactionMapper.INSTANCE.toEntity(req));
            return new ResponseEntity<>("Transaction deleted successfully", HttpStatus.OK);
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDto<ResponseTransactionDto>> details(String userId, RequestTransactionDto req) {
        try {
            final QueryResult<Transaction> queryResult = transactionRepository
                    .search(ServiceHelper
                            .getQueryRequest(Transaction.class, req, userId, true, false));

            final ResponseDto<ResponseTransactionDto> responseDto = ResponseDto
                    .<ResponseTransactionDto>builder()
                    .data(queryResult
                            .getResults()
                            .stream()
                            .map(TransactionMapper.INSTANCE::toResponseDtoDetails)
                            .toList())
                    .page(PageMapper.INSTANCE.toDto(queryResult.getPage()))
                    .build();

            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
