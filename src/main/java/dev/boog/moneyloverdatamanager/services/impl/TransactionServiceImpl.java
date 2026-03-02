package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.entities.Transaction;
import dev.boog.moneyloverdatamanager.mappers.TransactionMapper;
import dev.boog.moneyloverdatamanager.repositories.BaseRepository;
import dev.boog.moneyloverdatamanager.repositories.TransactionRepository;

import dev.boog.moneyloverdatamanager.services.CRUDService;
import dev.boog.moneyloverdatamanager.services.TransactionService;
import dev.boog.moneyloverdatamanager.utils.ServiceHelper;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.logging.Logger;

public class TransactionServiceImpl implements TransactionService<RequestTransactionDto, ResponseTransactionDto> {

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

    public ResponseEntity<List<ResponseTransactionDto>> get(String userId, RequestTransactionDto req) {
        try {
            final List<ResponseTransactionDto> responseDtoList = transactionRepository
                    .searchByUserIdAndOptionalParams(
                            Transaction.class,
                            userId,
                            ServiceHelper.mapQueryParams(userId, req),
                            ServiceHelper.filter(req),
                            false
                    )
                    .stream()
                    .map(TransactionMapper.INSTANCE::toResponseDto)
                    .toList();
            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
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
    public ResponseEntity<List<ResponseTransactionDto>> details(String userId, RequestTransactionDto req) {
        try {
            final List<ResponseTransactionDto> responseDtoList = transactionRepository
                    .searchByUserIdAndOptionalParams(
                            Transaction.class,
                            userId,
                            ServiceHelper.mapQueryParams(userId, req),
                            ServiceHelper.filter(req),
                            true
                    )
                    .stream()
                    .map(TransactionMapper.INSTANCE::toResponseDtoDetails)
                    .toList();
            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
