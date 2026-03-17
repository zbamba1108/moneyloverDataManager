package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.entities.Category;
import dev.boog.moneyloverdatamanager.repositories.CategoryRepository;
import dev.boog.moneyloverdatamanager.services.CategoryService;
import dev.boog.moneyloverdatamanager.utils.ServiceHelper;
import dev.boog.moneyloverdatamanager.utils.mappers.CategoryMapper;
import dev.boog.moneyloverdatamanager.utils.mappers.PageMapper;
import dev.boog.moneyloverdatamanager.utils.models.QueryResult;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.logging.Logger;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String create(String userId, RequestCategoryDto req) {
        Category category = CategoryMapper.INSTANCE
                .toEntity(req)
                .userId(userId);
        categoryRepository.save(category);
        return "Category created successfully";
    }

    @Override
    public ResponseDto<ResponseCategoryDto> get(String userId, RequestCategoryDto req) {
        final QueryResult<Category> queryResult = categoryRepository
                .search(ServiceHelper
                        .getQueryRequest(Category.class, req, userId, false, false));

        return ResponseDto.<ResponseCategoryDto>builder()
                .data(queryResult
                        .getResults()
                        .stream()
                        .map(CategoryMapper.INSTANCE::toResponseDto)
                        .toList())
                .page(PageMapper.INSTANCE.toDto(queryResult.getPage()))
                .build();
    }

    @Override
    public ResponseCategoryDto update(String userId, RequestCategoryDto req) {
        return CategoryMapper.INSTANCE
                .toResponseDto(categoryRepository.save(CategoryMapper.INSTANCE
                        .toEntity(req)));
    }

    @Override
    public String delete(String userId, RequestCategoryDto req) {
        categoryRepository.deleteByIds(
                Category.class,
                Long.parseLong(userId),
                req.getIds()
                        .stream()
                        .map(Long::parseLong)
                        .toList()
        );
        return "Category(s) deleted successfully";
    }

}
