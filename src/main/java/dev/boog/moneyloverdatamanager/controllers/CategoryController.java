package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.services.CategoryService;
import dev.boog.moneyloverdatamanager.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Category API")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @Operation(description = "create a new category")
    @PostMapping
    public ResponseEntity<String> create(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestCategoryDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(userId, dto));
    }

    @Operation(description = "search one or more categories based on input request")
    @PostMapping("/search")
    public ResponseEntity<ResponseDto<ResponseCategoryDto>> get(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                                @RequestBody(required = false) RequestCategoryDto req) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.get(userId, req));
    }

    @Operation(description = "update an existing category")
    @PutMapping
    public ResponseEntity<ResponseCategoryDto> update(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                      @RequestBody RequestCategoryDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(userId, dto));
    }

    @Operation(description = "delete an existing category")
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestCategoryDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.delete(userId, dto));
    }
}
