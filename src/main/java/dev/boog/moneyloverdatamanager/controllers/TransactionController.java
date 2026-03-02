package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.services.TransactionService;
import dev.boog.moneyloverdatamanager.utils.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/transactions")
public class TransactionController implements Controller<RequestTransactionDto, ResponseTransactionDto> {

    private final TransactionService<RequestTransactionDto, ResponseTransactionDto> service;

    public TransactionController(@Qualifier("transactionService") TransactionService<RequestTransactionDto, ResponseTransactionDto> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestTransactionDto transaction) {
        return service.create(userId, transaction);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ResponseTransactionDto>> get(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                            @RequestBody(required = false) RequestTransactionDto req) {
        return service.get(userId, req);
    }

    @Override
    public ResponseEntity<List<ResponseTransactionDto>> details(String userId, RequestTransactionDto req) {
        return null;
    }

    @PutMapping
    public ResponseEntity<ResponseTransactionDto> update(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                         @RequestBody RequestTransactionDto dto) {
        return service.update(userId, dto);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestTransactionDto dto) {
        return service.delete(userId, dto);
    }

}
