package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseCategoryDto;
import dev.boog.moneyloverdatamanager.services.Service;
import dev.boog.moneyloverdatamanager.utils.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public record CategoryController(@Qualifier("categoryService") Service<RequestCategoryDto, ResponseCategoryDto, Long> service) implements Controller<RequestCategoryDto, ResponseCategoryDto> {

    @PostMapping
    public ResponseEntity<String> create(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestCategoryDto dto) {
        return service.create(userId, dto);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ResponseCategoryDto>> get(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                         @RequestBody(required = false) RequestCategoryDto req) {
        return null;
    }

    @PutMapping
    public ResponseEntity<ResponseCategoryDto> update(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                      @RequestBody RequestCategoryDto dto) {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestCategoryDto dto) {
        return null;
    }
}
