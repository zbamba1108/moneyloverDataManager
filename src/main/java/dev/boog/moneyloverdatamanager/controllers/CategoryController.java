package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.service.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public record CategoryController(@Qualifier("categoryService") Service<RequestCategoryDto, ResponseCategoryDto, Long> service) implements Controller<RequestCategoryDto, ResponseCategoryDto, Long> {

    @PostMapping
    public ResponseEntity<String> create(@RequestHeader("User-ID") String userId, @RequestBody RequestCategoryDto dto) {
        return service.create(userId, dto);
    }

    @GetMapping
    public ResponseEntity<List<ResponseCategoryDto>> get(@RequestHeader("User-ID") String userId, @RequestParam("id") Long id) {
        return null;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseCategoryDto>> getAll(@RequestHeader("User-ID") String userId) {
        return service.getAll(userId);
    }

    @PutMapping
    public ResponseEntity<ResponseCategoryDto> update(@RequestHeader("User-ID") String userId, RequestCategoryDto dto) {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestHeader("User-ID") String userId, RequestCategoryDto dto) {
        return null;
    }
}
