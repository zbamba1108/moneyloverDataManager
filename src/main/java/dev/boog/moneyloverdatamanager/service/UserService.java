package dev.boog.moneyloverdatamanager.service;

import dev.boog.moneyloverdatamanager.dtos.request.RequestUserDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseUserDto;
import dev.boog.moneyloverdatamanager.mappers.UserMapper;
import dev.boog.moneyloverdatamanager.repositories.BaseRepository;
import dev.boog.moneyloverdatamanager.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

public class UserService implements Service<RequestUserDto, ResponseUserDto, Long> {

    private UserRepository userRepository;

    @Override
    public void setRepository(BaseRepository<?, Long> repository) {
        userRepository = (UserRepository) repository;
    }

    @Override
    public ResponseEntity<String> create(String userId, RequestUserDto req) {
        try {
            userRepository.save(UserMapper.INSTANCE.toEntity(req));
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<ResponseUserDto>> get(String userId, Long id) {
        try {
            ResponseUserDto responseDto = UserMapper.INSTANCE.toResponseDto(userRepository.getUserById(id));
            return new ResponseEntity<>(Collections.singletonList(responseDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<ResponseUserDto>> getAll(String userId) {
        try {
            List<ResponseUserDto> responseDtoList = userRepository
                    .getUsers()
                    .stream()
                    .map(UserMapper.INSTANCE::toResponseDto)
                    .toList();

            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseUserDto> update(String userId, RequestUserDto req) {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(String userId, RequestUserDto dto) {
        return null;
    }
}
