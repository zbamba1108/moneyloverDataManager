package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.services.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/transactions")
public record TransactionController(@Qualifier("transactionService") Service<RequestTransactionDto, ResponseTransactionDto, Long> service) implements Controller<RequestTransactionDto, ResponseTransactionDto> {

    @PostMapping
    public ResponseEntity<String> create(@RequestHeader("User-ID") String userId, @RequestBody RequestTransactionDto transaction) {
        return service.create(userId, transaction);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTransactionDto>> get(@RequestHeader(value = "User-ID", required = false) String userId,
                                                            @RequestParam(value = "query", required = false) String query) {
        return service.get(userId,query);
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
