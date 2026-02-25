package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseWalletDto;
import dev.boog.moneyloverdatamanager.service.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public record WalletController(@Qualifier("walletService") Service<RequestWalletDto, ResponseWalletDto, Long> service) implements Controller<RequestWalletDto, ResponseWalletDto, Long> {


    @PostMapping
    public ResponseEntity<String> create(@RequestHeader("User-ID") String userId, @RequestBody RequestWalletDto req) {
        return service.create(userId, req);
    }

    @GetMapping
    public ResponseEntity<List<ResponseWalletDto>> get(@RequestHeader("User-ID") String userId, @RequestParam("id") Long id) {
        return service.get(userId, id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseWalletDto>> getAll(@RequestHeader("User-ID") String userId) {
        return service.getAll(userId);
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
