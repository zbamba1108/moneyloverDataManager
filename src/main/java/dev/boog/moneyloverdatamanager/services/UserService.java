package dev.boog.moneyloverdatamanager.services;

import dev.boog.moneyloverdatamanager.dtos.request.RequestUserDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseUserDto;
import dev.boog.moneyloverdatamanager.entities.User;
import dev.boog.moneyloverdatamanager.mappers.UserMapper;
import dev.boog.moneyloverdatamanager.repositories.BaseRepository;
import dev.boog.moneyloverdatamanager.repositories.UserRepository;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.logging.Logger;

public class UserService implements Service<RequestUserDto, ResponseUserDto, Long> {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(UserService.class));

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
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ResponseUserDto>> get(String userId, RequestUserDto req) {
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
