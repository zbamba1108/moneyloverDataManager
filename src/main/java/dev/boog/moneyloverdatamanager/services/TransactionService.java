package dev.boog.moneyloverdatamanager.services;

import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.entities.Transaction;
import dev.boog.moneyloverdatamanager.mappers.TransactionMapper;
import dev.boog.moneyloverdatamanager.repositories.BaseRepository;
import dev.boog.moneyloverdatamanager.repositories.TransactionRepository;

import dev.boog.moneyloverdatamanager.utils.ServiceHelper;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.logging.Logger;

public class TransactionService implements Service<RequestTransactionDto, ResponseTransactionDto, Long> {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(TransactionService.class));

    private TransactionRepository transactionRepository;

    @Override
    public void setRepository(BaseRepository<?, Long> repository) {
        this.transactionRepository = (TransactionRepository) repository;
    }

    @Override
    public ResponseEntity<String> create(String userId, RequestTransactionDto req) {
        try {
            Transaction entity = TransactionMapper.INSTANCE
                    .toEntity(req)
                    .userId(userId);
            transactionRepository.save(entity);
            return new ResponseEntity<>("Transaction created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ResponseTransactionDto>> get(String userId, RequestTransactionDto req) {
        try {
            final List<ResponseTransactionDto> responseDtoList = transactionRepository
                    .searchByUserIdAndOptionalParams(
                            Transaction.class,
                            userId,
                            ServiceHelper.mapQueryParams(userId, req),
                            ServiceHelper.filter(req)
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
        return null;
    }

    @Override
    public ResponseEntity<String> delete(String userId, RequestTransactionDto dto) {
        return null;
    }
}
