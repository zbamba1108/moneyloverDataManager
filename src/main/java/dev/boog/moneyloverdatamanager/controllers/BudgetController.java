package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestBudgetDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseBudgetDto;
import dev.boog.moneyloverdatamanager.services.Service;
import dev.boog.moneyloverdatamanager.utils.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public record BudgetController(@Qualifier("budgetService") Service<RequestBudgetDto, ResponseBudgetDto, Long> service) implements Controller<RequestBudgetDto, ResponseBudgetDto> {

    @PostMapping
    public ResponseEntity<String> create(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestBudgetDto dto) {
        return service.create(userId, dto);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ResponseBudgetDto>> get(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                       @RequestBody(required = false) RequestBudgetDto req) {
        return null;
    }

    @PutMapping
    public ResponseEntity<ResponseBudgetDto> update(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                    @RequestBody RequestBudgetDto dto) {
        return service.update(userId, dto);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestBudgetDto dto) {
        return service.delete(userId, dto);
    }
}
