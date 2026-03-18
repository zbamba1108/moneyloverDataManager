package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestUserDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseUserDto;
import dev.boog.moneyloverdatamanager.entities.User;
import dev.boog.moneyloverdatamanager.repositories.UserRepository;
import dev.boog.moneyloverdatamanager.services.UserService;
import dev.boog.moneyloverdatamanager.utils.mappers.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String create(String userId, RequestUserDto req) {
        userRepository.save(UserMapper.INSTANCE.toEntity(req));
        return "User created successfully";
    }

    public ResponseDto<ResponseUserDto> get(String userId, RequestUserDto req) {
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

        return ResponseDto
                .<ResponseUserDto>builder()
                .data(responseDtoList)
                .build();
    }

    @Override
    public ResponseUserDto update(String userId, RequestUserDto req) {
        return UserMapper.INSTANCE
                .toResponseDto(userRepository.save(UserMapper.INSTANCE
                        .toEntity(req)));
    }

    @Override
    public String delete(String userId, RequestUserDto req) {
        userRepository.deleteByIds(
                User.class,
                null,
                req.getIds()
                        .stream()
                        .map(Long::parseLong)
                        .toList());
        return "User(s) deleted successfully";
    }
}
