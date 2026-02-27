package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseWalletDto;
import dev.boog.moneyloverdatamanager.services.Service;
import dev.boog.moneyloverdatamanager.utils.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public record WalletController(@Qualifier("walletService") Service<RequestWalletDto, ResponseWalletDto, Long> service) implements Controller<RequestWalletDto, ResponseWalletDto> {


    @PostMapping
    public ResponseEntity<String> create(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestWalletDto req) {
        return service.create(userId, req);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ResponseWalletDto>> get(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                       @RequestBody(required = false) RequestWalletDto req) {
        return service.get(userId, req);
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
