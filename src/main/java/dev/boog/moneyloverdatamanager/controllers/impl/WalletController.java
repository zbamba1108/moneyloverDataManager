package dev.boog.moneyloverdatamanager.controllers.impl;

import dev.boog.moneyloverdatamanager.controllers.CRUDController;
import dev.boog.moneyloverdatamanager.controllers.DetailsController;
import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseWalletDto;
import dev.boog.moneyloverdatamanager.services.WalletService;
import dev.boog.moneyloverdatamanager.utils.Constants;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag( name = "Wallet API")
@RestController
@RequestMapping("/api/wallets")
public class WalletController implements CRUDController<RequestWalletDto, ResponseWalletDto>,
        DetailsController<RequestWalletDto, ResponseWalletDto> {

    private final WalletService<RequestWalletDto, ResponseWalletDto> service;

    public WalletController(@Qualifier("walletService") WalletService<RequestWalletDto, ResponseWalletDto> service) {
        this.service = service;
    }

    @Operation(description = "create a new wallet")
    @PostMapping
    public ResponseEntity<String> create(@Parameter(
                                            name = "USER-ID",
                                            description = "the userid of the calling customer",
                                            required = true)
                                         @RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @Parameter(
                                                 name = "request",
                                                 description = "the input request")
                                         @RequestBody RequestWalletDto req) {
        return service.create(userId, req);
    }

    @Operation(description = "search one or more wallets based on input request")
    @PostMapping("/search")
    public ResponseEntity<ResponseDto<ResponseWalletDto>> get(@Parameter(
                                                                  name = "USER-ID",
                                                                  description = "the userid of the calling customer",
                                                                  required = true)
                                                              @RequestHeader(Constants.Headers.USER_ID) String userId,
                                                              @Parameter(
                                                                  name = "request",
                                                                  description = "the input request")
                                                              @RequestBody(required = false) RequestWalletDto req) {
        return service.get(userId, req);
    }

    @Operation(description = "search one or more wallets based on input request, get the details of children")
    @PostMapping("/search/details")
    public ResponseEntity<ResponseDto<ResponseWalletDto>> details(@Parameter(
                                                                      name = "USER-ID",
                                                                      description = "the userid of the calling customer",
                                                                      required = true)
                                                                  @RequestHeader(Constants.Headers.USER_ID) String userId,
                                                                  @Parameter(
                                                                      name = "request",
                                                                      description = "the input request")
                                                                  @RequestBody(required = false) RequestWalletDto req) {
        return service.details(userId, req);
    }

    @Operation(description = "update an existing wallet")
    @PutMapping
    public ResponseEntity<ResponseWalletDto> update(@Parameter(
                                                        name = "USER-ID",
                                                        description = "the userid of the calling customer",
                                                        required = true)
                                                    @RequestHeader(Constants.Headers.USER_ID) String userId,
                                                    @Parameter(
                                                        name = "request",
                                                        description = "the input request")
                                                    @RequestBody RequestWalletDto req) {
        return service.update(userId, req);
    }

    @Operation(description = "delete an existing wallet")
    @DeleteMapping
    public ResponseEntity<String> delete(@Parameter(
                                             name = "USER-ID",
                                             description = "the userid of the calling customer",
                                             required = true)
                                         @RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @Parameter(
                                             name = "request",
                                             description = "the input request")
                                         @RequestBody RequestWalletDto req) {
        return service.delete(userId, req);
    }
}
