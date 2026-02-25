package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseCategoryDto;
import dev.boog.moneyloverdatamanager.services.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public record CategoryController(@Qualifier("categoryService") Service<RequestCategoryDto, ResponseCategoryDto, Long> service) implements Controller<RequestCategoryDto, ResponseCategoryDto> {

    @PostMapping
    public ResponseEntity<String> create(@RequestHeader("User-ID") String userId, @RequestBody RequestCategoryDto dto) {
        return service.create(userId, dto);
    }

    @Override
    public ResponseEntity<List<ResponseCategoryDto>> get(String userId, String query) {
        return null;
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
