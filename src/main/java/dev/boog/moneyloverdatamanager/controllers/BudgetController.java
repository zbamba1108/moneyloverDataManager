package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestBudgetDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseBudgetDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.service.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public record BudgetController(@Qualifier("budgetService") Service<RequestBudgetDto, ResponseBudgetDto, Long> service) implements Controller<RequestBudgetDto, ResponseBudgetDto, Long> {

    @PostMapping
    public ResponseEntity<String> create(@RequestHeader("User-ID") String userId, RequestBudgetDto dto) {
        return service.create(userId, dto);
    }

    @GetMapping
    public ResponseEntity<List<ResponseBudgetDto>> get(@RequestHeader("User-ID") String userId, @RequestParam("id") Long id) {
        return service.get(userId, id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseBudgetDto>> getAll(String userId) {
        return service.getAll(userId);
    }

    @PutMapping
    public ResponseEntity<ResponseBudgetDto> update(@RequestHeader("User-ID") String userId, RequestBudgetDto dto) {
        return service.update(userId, dto);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestHeader("User-ID") String userId, RequestBudgetDto dto) {
        return service.delete(userId, dto);
    }
}
