package dev.boog.moneyloverdatamanager.service;

import dev.boog.moneyloverdatamanager.dtos.request.RequestBudgetDto;

import dev.boog.moneyloverdatamanager.dtos.response.ResponseBudgetDto;
import dev.boog.moneyloverdatamanager.entities.Budget;
import dev.boog.moneyloverdatamanager.repositories.BaseRepository;
import dev.boog.moneyloverdatamanager.repositories.BudgetRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class BudgetService implements Service<RequestBudgetDto, ResponseBudgetDto, Long>{

    private BudgetRepository budgetRepository;

    @Override
    public void setRepository(BaseRepository<?, Long> repository) {
        this.budgetRepository = (BudgetRepository) repository;
    }

    @Override
    public ResponseEntity<String> create(String userId, RequestBudgetDto req) {
        return null;
    }

    @Override
    public ResponseEntity<List<ResponseBudgetDto>> get(String userId, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<ResponseBudgetDto>> getAll(String userId) {
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
