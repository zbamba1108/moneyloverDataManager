package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestBudgetDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseBudgetDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.repositories.BudgetRepository;
import dev.boog.moneyloverdatamanager.services.BudgetService;
import org.springframework.stereotype.Service;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public ResponseBudgetDto create(Long userId, RequestBudgetDto req) {
        return null;
    }

    @Override
    public ResponseDto<ResponseBudgetDto> get(Long userId, RequestBudgetDto req) {
        return null;
    }

    @Override
    public ResponseBudgetDto update(Long userId, RequestBudgetDto req) {
        return null;
    }

    @Override
    public void delete(Long id, Long userId) {
    }
}
