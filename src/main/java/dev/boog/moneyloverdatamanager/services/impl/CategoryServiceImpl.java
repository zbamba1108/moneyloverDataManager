package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.entities.Category;
import dev.boog.moneyloverdatamanager.exceptions.customexceptions.ResourceNotFoundException;
import dev.boog.moneyloverdatamanager.repositories.CategoryRepository;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryResult;
import dev.boog.moneyloverdatamanager.services.CategoryService;
import dev.boog.moneyloverdatamanager.services.utils.CategoryQueryHelper;
import dev.boog.moneyloverdatamanager.services.utils.QueryRequestBuilder;
import dev.boog.moneyloverdatamanager.utils.mappers.CategoryMapper;
import dev.boog.moneyloverdatamanager.utils.mappers.PageMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryQueryHelper queryHelper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,  CategoryQueryHelper queryHelper) {
        this.categoryRepository = categoryRepository;
        this.queryHelper = queryHelper;
    }

    @Override
    public ResponseCategoryDto create(Long userId, RequestCategoryDto req) {
        Category category = CategoryMapper.INSTANCE
                .toEntity(req);
        category.setUserId(userId);

        return CategoryMapper.INSTANCE
                .toResponseDto(categoryRepository.save(category));
    }

    @Override
    public ResponseDto<ResponseCategoryDto> get(Long userId, RequestCategoryDto req) {
        QueryRequest<Category> queryRequest = QueryRequestBuilder.build(queryHelper, req, userId);
        QueryResult<Category> queryResult = categoryRepository
                .findAll(queryRequest);

        return ResponseDto.<ResponseCategoryDto>builder()
                .data(queryResult
                        .results()
                        .stream()
                        .map(CategoryMapper.INSTANCE::toResponseDto)
                        .toList())
                .page(PageMapper.INSTANCE.toDto(queryResult.page()))
                .build();
    }

    @Override
    public ResponseCategoryDto update(Long userId, Long id, RequestCategoryDto req) {
        categoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(ResourceNotFoundException::new);
        return CategoryMapper.INSTANCE
                .toResponseDto(categoryRepository.save(CategoryMapper.INSTANCE
                        .toEntity(req)));
    }

    @Override
    public void delete(Long id, Long userId) {
        categoryRepository.deleteById(id);
    }

}
