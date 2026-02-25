package dev.boog.moneyloverdatamanager.service;

import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseCategoryDto;
import dev.boog.moneyloverdatamanager.entities.Category;
import dev.boog.moneyloverdatamanager.mappers.CategoryMapper;
import dev.boog.moneyloverdatamanager.repositories.BaseRepository;
import dev.boog.moneyloverdatamanager.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class CategoryService implements Service<RequestCategoryDto, ResponseCategoryDto, Long> {

    private CategoryRepository categoryRepository;

    @Override
    public void setRepository(BaseRepository<?, Long> repository) {
        categoryRepository = (CategoryRepository) repository;
    }

    @Override
    public ResponseEntity<String> create(String userId, RequestCategoryDto req) {
        try {
            Category category = CategoryMapper.INSTANCE.toEntity(req);
            categoryRepository.save(category);
            return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<ResponseCategoryDto>> get(String userId, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<ResponseCategoryDto>> getAll(String userId) {
        try {
            List<ResponseCategoryDto> responseDtoList = categoryRepository
                    .getAllByUserId(Long.parseLong(userId))
                    .stream()
                    .map(CategoryMapper.INSTANCE::toResponseDto)
                    .toList();

            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
        } catch (Exception e) {
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
