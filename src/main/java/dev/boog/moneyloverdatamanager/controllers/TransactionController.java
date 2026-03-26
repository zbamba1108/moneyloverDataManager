package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.exceptions.validations.Read;
import dev.boog.moneyloverdatamanager.exceptions.validations.Write;
import dev.boog.moneyloverdatamanager.services.TransactionService;
import dev.boog.moneyloverdatamanager.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Transaction API")
@RestController
@RequestMapping("/api/data/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @Operation(description = "create a new transaction")
    @PostMapping
    public ResponseEntity<String> create(@RequestHeader(Constants.Headers.USER_ID) Long userId,
                                         @RequestBody
                                         @Validated(Write.class)
                                         RequestTransactionDto transaction) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(userId, transaction));
    }

    @Operation(description = "search one or more transactions based on input request")
    @PostMapping("/search")
    public ResponseEntity<ResponseDto<ResponseTransactionDto>> get(@RequestHeader(Constants.Headers.USER_ID) Long userId,
                                                                   @RequestBody(required = false)
                                                                   @Validated(Read.class)
                                                                   RequestTransactionDto req) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.get(userId, req));
    }

    @Operation(description = "search one or more wallets based on input request, get the details of children")
    @PostMapping("/search/details")
    public ResponseEntity<ResponseDto<ResponseTransactionDto>> details(@RequestHeader(Constants.Headers.USER_ID) Long userId,
                                                                       @RequestBody(required = false)
                                                                       @Validated(Read.class)
                                                                       RequestTransactionDto req) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.details(userId, req));
    }

    @Operation(description = "update an existing transaction")
    @PutMapping
    public ResponseEntity<ResponseTransactionDto> update(@RequestHeader(Constants.Headers.USER_ID) Long userId,
                                                         @RequestBody
                                                         @Validated(Write.class)
                                                         RequestTransactionDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(userId, dto));
    }

    @Operation(description = "delete an existing transaction")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@RequestHeader(Constants.Headers.USER_ID) Long userId,
                                         @PathVariable Long id) {
        service.delete(id,userId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }

}
