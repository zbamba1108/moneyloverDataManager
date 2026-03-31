package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.exceptions.validations.Read;
import dev.boog.moneyloverdatamanager.exceptions.validations.Write;
import dev.boog.moneyloverdatamanager.services.CategoryService;
import dev.boog.moneyloverdatamanager.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Category API")
@RestController
@RequestMapping("/api/data/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @Operation(description = "create a new category")
    @PostMapping
    public ResponseEntity<ResponseCategoryDto> create(@RequestHeader(Constants.Headers.USER_ID) Long userId,
                                         @RequestBody
                                         @Validated(Write.class)
                                         RequestCategoryDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(userId, dto));
    }

    @Operation(description = "search one or more categories based on input request")
    @PostMapping("/search")
    public ResponseEntity<ResponseDto<ResponseCategoryDto>> get(@RequestHeader(Constants.Headers.USER_ID) Long userId,
                                                                @RequestBody(required = false)
                                                                @Validated(Read.class)
                                                                RequestCategoryDto req) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.get(userId, req));
    }

    @Operation(description = "update an existing category")
    @PutMapping
    public ResponseEntity<ResponseCategoryDto> update(@RequestHeader(Constants.Headers.USER_ID) Long userId,
                                                      @RequestBody
                                                      @Validated(Write.class)
                                                      RequestCategoryDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(userId, dto));
    }

    @Operation(description = "delete an existing category")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@RequestHeader(Constants.Headers.USER_ID) Long userId,
                                         @PathVariable Long id) {
        service.delete(id, userId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
