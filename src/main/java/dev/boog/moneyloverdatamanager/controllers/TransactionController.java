package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.service.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/transactions")
public record TransactionController(@Qualifier("transactionService") Service<RequestTransactionDto, ResponseTransactionDto, Long> service) /* TODO restore Controller implementation */ {

    @PostMapping
    public ResponseEntity<String> create(@RequestHeader("User-ID") String userId, @RequestBody RequestTransactionDto transaction) {
        return service.create(userId, transaction);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTransactionDto>> get(@RequestHeader("User-ID") String userId,
                                                            @RequestParam(value = "id", required = false) Long id,
                                                            @RequestParam(value = "walletId", required = false) Long walletId,
                                                            @RequestParam(value = "categoryId", required = false) Long categoryId) {
        //return service.get(userId, id, walletId, categoryId);
        return service.get(userId, id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseTransactionDto>> getAll(@RequestHeader("User-ID") String userId) {
        return service.getAll(userId);
    }

    @PutMapping
    public ResponseEntity<ResponseTransactionDto> update(@RequestHeader("User-ID") String userId, RequestTransactionDto dto) {
        return service.update(userId, dto);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestHeader("User-ID") String userId, RequestTransactionDto dto) {
        return service.delete(userId, dto);
    }

}
