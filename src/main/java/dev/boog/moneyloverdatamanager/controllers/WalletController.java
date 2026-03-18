package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseWalletDto;
import dev.boog.moneyloverdatamanager.exceptions.validations.Read;
import dev.boog.moneyloverdatamanager.exceptions.validations.Write;
import dev.boog.moneyloverdatamanager.services.WalletService;
import dev.boog.moneyloverdatamanager.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag( name = "Wallet API")
@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService service;

    public WalletController(WalletService service) {
        this.service = service;
    }

    @Operation(description = "create a new wallet")
    @PostMapping
    public ResponseEntity<String> create(@Parameter(
                                            name = "USER-ID",
                                            description = "the userid of the calling customer",
                                            required = true)
                                         @RequestHeader(Constants.Headers.USER_ID) Long userId,
                                         @Parameter(
                                                 name = "request",
                                                 description = "the input request")
                                         @RequestBody
                                         @Validated(Write.class)
                                         RequestWalletDto req) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(userId, req));
    }

    @Operation(description = "search one or more wallets based on input request")
    @PostMapping("/search")
    public ResponseEntity<ResponseDto<ResponseWalletDto>> get(@Parameter(
                                                                  name = "USER-ID",
                                                                  description = "the userid of the calling customer",
                                                                  required = true)
                                                              @RequestHeader(Constants.Headers.USER_ID) Long userId,
                                                              @Parameter(
                                                                  name = "request",
                                                                  description = "the input request")
                                                              @RequestBody(required = false)
                                                              @Validated(Read.class)
                                                              RequestWalletDto req) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.get(userId, req));
    }

    @Operation(description = "search one or more wallets based on input request, get the details of children")
    @PostMapping("/search/details")
    public ResponseEntity<ResponseDto<ResponseWalletDto>> details(@Parameter(
                                                                      name = "USER-ID",
                                                                      description = "the userid of the calling customer",
                                                                      required = true)
                                                                  @RequestHeader(Constants.Headers.USER_ID) Long userId,
                                                                  @Parameter(
                                                                      name = "request",
                                                                      description = "the input request")
                                                                  @RequestBody
                                                                  @Validated(Read.class)
                                                                  RequestWalletDto req) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.details(userId, req));
    }

    @Operation(description = "update an existing wallet")
    @PutMapping
    public ResponseEntity<ResponseWalletDto> update(@Parameter(
                                                        name = "USER-ID",
                                                        description = "the userid of the calling customer",
                                                        required = true)
                                                    @RequestHeader(Constants.Headers.USER_ID) Long userId,
                                                    @Parameter(
                                                        name = "request",
                                                        description = "the input request")
                                                    @RequestBody
                                                    @Validated(Write.class)
                                                    RequestWalletDto req) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(userId, req));
    }

    @Operation(description = "delete an existing wallet")
    @DeleteMapping
    public ResponseEntity<String> delete(@Parameter(
                                             name = "USER-ID",
                                             description = "the userid of the calling customer",
                                             required = true)
                                         @RequestHeader(Constants.Headers.USER_ID) Long userId,
                                         @Parameter(
                                             name = "request",
                                             description = "the input request")
                                         @RequestBody
                                         @Validated(Write.class)
                                         RequestWalletDto req) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.delete(userId, req));
    }
}
