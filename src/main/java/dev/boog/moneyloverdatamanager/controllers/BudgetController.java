package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestBudgetDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseBudgetDto;
import dev.boog.moneyloverdatamanager.services.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public record BudgetController(@Qualifier("budgetService") Service<RequestBudgetDto, ResponseBudgetDto, Long> service) implements Controller<RequestBudgetDto, ResponseBudgetDto> {

    @PostMapping
    public ResponseEntity<String> create(@RequestHeader("User-ID") String userId, RequestBudgetDto dto) {
        return service.create(userId, dto);
    }

    @Override
    public ResponseEntity<List<ResponseBudgetDto>> get(String userId, String query) {
        return null;
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
