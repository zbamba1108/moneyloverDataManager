package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestUserDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseUserDto;
import dev.boog.moneyloverdatamanager.entities.User;
import dev.boog.moneyloverdatamanager.utils.mappers.UserMapper;
import dev.boog.moneyloverdatamanager.repositories.UserRepository;

import dev.boog.moneyloverdatamanager.services.UserService;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService<RequestUserDto, ResponseUserDto> {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(UserServiceImpl.class));

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<String> create(String userId, RequestUserDto req) {
        try {
            userRepository.save(UserMapper.INSTANCE.toEntity(req));
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseDto<ResponseUserDto>> get(String userId, RequestUserDto req) {
        try {
            List<ResponseUserDto> responseDtoList;

            if (req != null && req.getIds() != null && !req.getIds().isEmpty()) {
                responseDtoList = userRepository.getUserById(req.getIds()
                                .stream()
                                .map(Long::parseLong)
                                .toList())
                        .stream()
                        .map(UserMapper.INSTANCE::toResponseDto)
                        .toList();
            } else {
                responseDtoList = userRepository.getUsers()
                        .stream()
                        .map(UserMapper.INSTANCE::toResponseDto)
                        .toList();
            }

            // TODO implements paging

            ResponseDto<ResponseUserDto> responseDto = ResponseDto
                    .<ResponseUserDto>builder()
                    .data(responseDtoList)
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
    public ResponseEntity<ResponseUserDto> update(String userId, RequestUserDto req) {
        try {

            ResponseUserDto responseDto = UserMapper.INSTANCE
                    .toResponseDto(userRepository.save(UserMapper.INSTANCE
                            .toEntity(req)));
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
    public ResponseEntity<String> delete(String userId, RequestUserDto req) {
        try {
            userRepository.deleteByIds(
                    User.class,
                    null,
                    req.getIds()
                            .stream()
                            .map(Long::parseLong)
                            .toList());
            return new ResponseEntity<>("User(s) deleted successfully", HttpStatus.OK);
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
