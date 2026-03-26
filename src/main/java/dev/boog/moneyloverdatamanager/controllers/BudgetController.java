package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestBudgetDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseBudgetDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.exceptions.validations.Read;
import dev.boog.moneyloverdatamanager.exceptions.validations.Write;
import dev.boog.moneyloverdatamanager.services.BudgetService;
import dev.boog.moneyloverdatamanager.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag( name = "Budget API")
@RestController
@RequestMapping("/api/data/budgets")
public class BudgetController {

    private final BudgetService service;

    public BudgetController(BudgetService service) {
        this.service = service;
    }

    @Operation(description = "create a new budget")
    @PostMapping
    public ResponseEntity<String> create(@RequestHeader(Constants.Headers.USER_ID) Long userId,
                                         @RequestBody
                                         @Validated(Write.class)
                                         RequestBudgetDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(userId, dto));
    }

    @Operation(description = "search one or more budgets based on input request")
    @PostMapping("/search")
    public ResponseEntity<ResponseDto<ResponseBudgetDto>> get(@RequestHeader(Constants.Headers.USER_ID) Long userId,
                                                              @RequestBody(required = false)
                                                              @Validated(Read.class)
                                                              RequestBudgetDto req) {
        return null;
    }

    @Operation(description = "update an existing budget")
    @PutMapping
    public ResponseEntity<ResponseBudgetDto> update(@RequestHeader(Constants.Headers.USER_ID) Long userId,
                                                    @RequestBody
                                                    @Validated(Write.class)
                                                    RequestBudgetDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(userId, dto));
    }

    @Operation(description = "delete an existing budget")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@RequestHeader(Constants.Headers.USER_ID) Long userId,
                                         @PathVariable Long id) {
        service.delete(id, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }
}
