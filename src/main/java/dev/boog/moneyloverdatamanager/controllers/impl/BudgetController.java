package dev.boog.moneyloverdatamanager.controllers.impl;

import dev.boog.moneyloverdatamanager.controllers.CRUDController;
import dev.boog.moneyloverdatamanager.dtos.request.RequestBudgetDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseBudgetDto;
import dev.boog.moneyloverdatamanager.services.BudgetService;
import dev.boog.moneyloverdatamanager.utils.Constants;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag( name = "Budget API")
@RestController
@RequestMapping("/api/budgets")
public class BudgetController implements CRUDController<RequestBudgetDto, ResponseBudgetDto> {

    private final BudgetService<RequestBudgetDto, ResponseBudgetDto> service;

    public BudgetController(@Qualifier("budgetService") BudgetService<RequestBudgetDto, ResponseBudgetDto> service) {
        this.service = service;
    }

    @Operation(description = "create a new budget")
    @PostMapping
    public ResponseEntity<String> create(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestBudgetDto dto) {
        return service.create(userId, dto);
    }

    @Operation(description = "search one or more budgets based on input request")
    @PostMapping("/search")
    public ResponseEntity<ResponseDto<ResponseBudgetDto>> get(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                              @RequestBody(required = false) RequestBudgetDto req) {
        return null;
    }

    @Operation(description = "update an existing budget")
    @PutMapping
    public ResponseEntity<ResponseBudgetDto> update(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                    @RequestBody RequestBudgetDto dto) {
        return service.update(userId, dto);
    }

    @Operation(description = "delete an existing budget")
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestBudgetDto dto) {
        return service.delete(userId, dto);
    }
}
