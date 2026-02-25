package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseWalletDto;
import dev.boog.moneyloverdatamanager.services.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public record WalletController(@Qualifier("walletService") Service<RequestWalletDto, ResponseWalletDto, Long> service) implements Controller<RequestWalletDto, ResponseWalletDto> {


    @PostMapping
    public ResponseEntity<String> create(@RequestHeader("User-ID") String userId, @RequestBody RequestWalletDto req) {
        return service.create(userId, req);
    }

    @GetMapping
    public ResponseEntity<List<ResponseWalletDto>> get(@RequestHeader("User-ID") String userId, @RequestParam(value = "query", required = false) String query) {
        return service.get(userId, query);
    }

    @PutMapping
    public ResponseEntity<ResponseWalletDto> update(@RequestHeader("User-ID") String userId, @RequestBody RequestWalletDto req) {
        return service.update(userId, req);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestHeader("User-ID") String userId, @RequestBody RequestWalletDto req) {
        return service.delete(userId, req);
    }
}
