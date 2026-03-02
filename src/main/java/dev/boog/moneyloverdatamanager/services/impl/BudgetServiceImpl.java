package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestBudgetDto;

import dev.boog.moneyloverdatamanager.dtos.response.ResponseBudgetDto;
import dev.boog.moneyloverdatamanager.repositories.BudgetRepository;
import dev.boog.moneyloverdatamanager.services.BudgetService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class BudgetServiceImpl implements BudgetService<RequestBudgetDto, ResponseBudgetDto> {

    private final BudgetRepository budgetRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public ResponseEntity<String> create(String userId, RequestBudgetDto req) {
        return null;
    }

    public ResponseEntity<List<ResponseBudgetDto>> get(String userId, RequestBudgetDto req) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseBudgetDto> update(String userId, RequestBudgetDto req) {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(String userId, RequestBudgetDto req) {
        return null;
    }
}
