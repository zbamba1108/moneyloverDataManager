package dev.boog.moneyloverdatamanager.service;

import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.entities.Transaction;
import dev.boog.moneyloverdatamanager.mappers.TransactionMapper;
import dev.boog.moneyloverdatamanager.repositories.BaseRepository;
import dev.boog.moneyloverdatamanager.repositories.TransactionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public class TransactionService implements Service<RequestTransactionDto, ResponseTransactionDto, Long> {

    private TransactionRepository transactionRepository;

    @Override
    public void setRepository(BaseRepository<?, Long> repository) {
        this.transactionRepository = (TransactionRepository) repository;
    }

    @Override
    public ResponseEntity<String> create(String userId, RequestTransactionDto req) {
        try {
            Transaction entity = TransactionMapper.INSTANCE.toEntity(req);
            transactionRepository.save(entity);
            return new ResponseEntity<>("Transaction created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ResponseTransactionDto>> get(String userId, Long id, Long walletId, Long categoryId) {
        try {

            // TODO fix

            HashMap<String, Long> params = new HashMap<>();
            params.put("id", id);
            params.put("walletId", walletId);
            params.put("categoryId", categoryId);
            params.put("userId", Long.parseLong(userId));
            List<ResponseTransactionDto> responseDtoList = transactionRepository
                    .searchWithMultipleOPtionalParams(Long.parseLong(userId), params)
                    .stream()
                    .map(TransactionMapper.INSTANCE::toResponseDto)
                    .toList();
            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<ResponseTransactionDto>> get(String userId, Long id) {
        return null;
    }


    @Override
    public ResponseEntity<List<ResponseTransactionDto>> getAll(String userId) {
        try {
            List<Transaction> transactions = transactionRepository.getAllByUserId(Long.parseLong(userId));
            List<ResponseTransactionDto> responseDtoList = transactionRepository
                    .getAllByUserId(Long.parseLong(userId))
                    .stream()
                    .map(TransactionMapper.INSTANCE::toResponseDto)
                    .toList();

            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
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
