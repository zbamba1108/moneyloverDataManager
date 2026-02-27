package dev.boog.moneyloverdatamanager.services;

import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseCategoryDto;
import dev.boog.moneyloverdatamanager.entities.Category;
import dev.boog.moneyloverdatamanager.mappers.CategoryMapper;
import dev.boog.moneyloverdatamanager.repositories.BaseRepository;
import dev.boog.moneyloverdatamanager.repositories.CategoryRepository;
import dev.boog.moneyloverdatamanager.utils.ServiceHelper;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.logging.Logger;

public class CategoryService implements Service<RequestCategoryDto, ResponseCategoryDto, Long> {

    private static final Logger LOGGER = Logger.getLogger(CategoryService.class.getName());

    private CategoryRepository categoryRepository;

    @Override
    public void setRepository(BaseRepository<?, Long> repository) {
        categoryRepository = (CategoryRepository) repository;
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
    public ResponseEntity<List<ResponseCategoryDto>> get(String userId, RequestCategoryDto req) {
        try {
            final List<ResponseCategoryDto> responseDtoList = categoryRepository
                    .searchByUserIdAndIdsAndOptionalParams(
                            Category.class,
                            userId,
                            req != null ? req.getIds() : null,
                            ServiceHelper.mapQueryParams(userId, req),
                            ServiceHelper.filter(req)
                    )
                    .stream()
                    .map(CategoryMapper.INSTANCE::toResponseDto)
                    .toList();
            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
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
        return null;
    }

    @Override
    public ResponseEntity<String> delete(String userId, RequestCategoryDto dto) {
        return null;
    }
}
