package dev.boog.moneyloverdatamanager.controllers.impl;

import dev.boog.moneyloverdatamanager.controllers.CRUDController;
import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseCategoryDto;
import dev.boog.moneyloverdatamanager.services.CategoryService;
import dev.boog.moneyloverdatamanager.utils.Constants;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Category API")
@RestController
@RequestMapping("/api/categories")
public class CategoryController implements CRUDController<RequestCategoryDto, ResponseCategoryDto> {

    private final CategoryService<RequestCategoryDto, ResponseCategoryDto> service;

    public CategoryController(@Qualifier("categoryService") CategoryService<RequestCategoryDto, ResponseCategoryDto> service) {
        this.service = service;
    }

    @Operation(description = "create a new category")
    @PostMapping
    public ResponseEntity<String> create(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestCategoryDto dto) {
        return service.create(userId, dto);
    }

    @Operation(description = "search one or more categories based on input request")
    @PostMapping("/search")
    public ResponseEntity<ResponseDto<ResponseCategoryDto>> get(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                                @RequestBody(required = false) RequestCategoryDto req) {
        return service.get(userId, req);
    }

    @Operation(description = "update an existing category")
    @PutMapping
    public ResponseEntity<ResponseCategoryDto> update(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                                      @RequestBody RequestCategoryDto dto) {
        return service.update(userId, dto);
    }

    @Operation(description = "delete an existing category")
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestHeader(Constants.Headers.USER_ID) String userId,
                                         @RequestBody RequestCategoryDto dto) {
        return service.delete(userId, dto);
    }
}
