package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseCategoryDto;
import dev.boog.moneyloverdatamanager.entities.Category;
import dev.boog.moneyloverdatamanager.utils.mappers.CategoryMapper;
import dev.boog.moneyloverdatamanager.repositories.CategoryRepository;
import dev.boog.moneyloverdatamanager.services.CategoryService;
import dev.boog.moneyloverdatamanager.utils.ServiceHelper;
import dev.boog.moneyloverdatamanager.utils.mappers.PageMapper;
import dev.boog.moneyloverdatamanager.utils.mappers.models.QueryResult;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.logging.Logger;

public class CategoryServiceImpl implements CategoryService<RequestCategoryDto, ResponseCategoryDto> {

    private static final Logger LOGGER = Logger.getLogger(CategoryServiceImpl.class.getName());

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<String> create(String userId, RequestCategoryDto req) {
        try {
            Category category = CategoryMapper.INSTANCE
                    .toEntity(req)
                    .userId(userId);
            categoryRepository.save(category);
            return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDto<ResponseCategoryDto>> get(String userId, RequestCategoryDto req) {
        try {
            final QueryResult<Category> queryResult = categoryRepository
                    .searchByUserIdAndIdsAndOptionalParams(
                            Category.class,
                            userId,
                            req != null ? req.getIds() : null,
                            ServiceHelper.mapQueryParams(userId, req),
                            ServiceHelper.filter(req),
                            false,
                            false
                    );

            final ResponseDto<ResponseCategoryDto> responseDto = ResponseDto.<ResponseCategoryDto>builder()
                    .data(queryResult
                            .getResults()
                            .stream()
                            .map(CategoryMapper.INSTANCE::toResponseDto)
                            .toList())
                    .page(PageMapper.INSTANCE.toDto(queryResult.getPage()))
                    .build();

            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseCategoryDto> update(String userId, RequestCategoryDto req) {
        try {
            ResponseCategoryDto responseCategoryDto = CategoryMapper.INSTANCE
                    .toResponseDto(categoryRepository.save(CategoryMapper.INSTANCE
                            .toEntity(req)));
            return new ResponseEntity<>(responseCategoryDto, HttpStatus.OK);
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> delete(String userId, RequestCategoryDto req) {
        try {
            categoryRepository.deleteByIds(
                    Category.class,
                    Long.parseLong(userId),
                    req.getIds()
                            .stream()
                            .map(Long::parseLong)
                            .toList()
            );
            return new ResponseEntity<>("Category(s) deleted successfully", HttpStatus.OK);
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
