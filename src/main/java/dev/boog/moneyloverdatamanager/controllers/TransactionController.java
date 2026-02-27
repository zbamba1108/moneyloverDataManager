package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.services.Service;
import dev.boog.moneyloverdatamanager.utils.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/transactions")
public record TransactionController(@Qualifier("transactionService") Service<RequestTransactionDto, ResponseTransactionDto, Long> service) implements Controller<RequestTransactionDto, ResponseTransactionDto> {

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
