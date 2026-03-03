package dev.boog.moneyloverdatamanager.controllers.impl;

import dev.boog.moneyloverdatamanager.controllers.CRUDController;
import dev.boog.moneyloverdatamanager.controllers.DetailsController;
import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseWalletDto;
import dev.boog.moneyloverdatamanager.services.WalletService;
import dev.boog.moneyloverdatamanager.utils.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallets")
public class WalletController implements CRUDController<RequestWalletDto, ResponseWalletDto>,
        DetailsController<RequestWalletDto, ResponseWalletDto> {

    private final WalletService<RequestWalletDto, ResponseWalletDto> service;

    public WalletController(@Qualifier("walletService") WalletService<RequestWalletDto, ResponseWalletDto> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestWalletDto req) {
        return service.create(userId, req);
    }

    @PostMapping("/search")
    public ResponseEntity<ResponseDto<ResponseWalletDto>> get(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                              @RequestBody(required = false) RequestWalletDto req) {
        return service.get(userId, req);
    }

    @PostMapping("/search/details")
    public ResponseEntity<ResponseDto<ResponseWalletDto>> details(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                                  @RequestBody(required = false) RequestWalletDto req) {
        return service.details(userId, req);
    }

    @PutMapping
    public ResponseEntity<ResponseWalletDto> update(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                    @RequestBody RequestWalletDto req) {
        return service.update(userId, req);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestWalletDto req) {
        return service.delete(userId, req);
    }
}
