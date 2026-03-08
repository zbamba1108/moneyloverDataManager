package dev.boog.moneyloverdatamanager.controllers.impl;

import dev.boog.moneyloverdatamanager.controllers.CRUDController;
import dev.boog.moneyloverdatamanager.controllers.DetailsController;
import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.services.TransactionService;
import dev.boog.moneyloverdatamanager.utils.Constants;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Transaction API")
@RestController
@RequestMapping(path = "/api/transactions")
public class TransactionController implements CRUDController<RequestTransactionDto, ResponseTransactionDto>,
        DetailsController<RequestTransactionDto, ResponseTransactionDto> {

    private final TransactionService<RequestTransactionDto, ResponseTransactionDto> service;

    public TransactionController(@Qualifier("transactionService") TransactionService<RequestTransactionDto, ResponseTransactionDto> service) {
        this.service = service;
    }

    @Operation(description = "create a new transaction")
    @PostMapping
    public ResponseEntity<String> create(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestTransactionDto transaction) {
        return service.create(userId, transaction);
    }

    @Operation(description = "search one or more transactions based on input request")
    @PostMapping("/search")
    public ResponseEntity<ResponseDto<ResponseTransactionDto>> get(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                                   @RequestBody(required = false) RequestTransactionDto req) {
        return service.get(userId, req);
    }

    @Operation(description = "search one or more wallets based on input request, get the details of children")
    @PostMapping("/search/details")
    public ResponseEntity<ResponseDto<ResponseTransactionDto>> details(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                                       @RequestBody(required = false) RequestTransactionDto req) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return service.details(userId, req);
    }

    @Operation(description = "update an existing transaction")
    @PutMapping
    public ResponseEntity<ResponseTransactionDto> update(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                         @RequestBody RequestTransactionDto dto) {
        return service.update(userId, dto);
    }

    @Operation(description = "delete an existing transaction")
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestTransactionDto dto) {
        return service.delete(userId, dto);
    }

}
