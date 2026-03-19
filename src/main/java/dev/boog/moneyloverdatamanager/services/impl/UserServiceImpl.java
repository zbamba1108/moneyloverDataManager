package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestUserDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseUserDto;
import dev.boog.moneyloverdatamanager.dtos.response.models.PageDto;
import dev.boog.moneyloverdatamanager.entities.User;
import dev.boog.moneyloverdatamanager.repositories.UserRepository;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryResult;
import dev.boog.moneyloverdatamanager.services.UserService;
import dev.boog.moneyloverdatamanager.services.utils.QueryRequestBuilder;
import dev.boog.moneyloverdatamanager.services.utils.UserQueryHelper;
import dev.boog.moneyloverdatamanager.utils.mappers.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserQueryHelper queryHelper;

    public UserServiceImpl(UserRepository userRepository, UserQueryHelper queryHelper) {
        this.userRepository = userRepository;
        this.queryHelper = queryHelper;
    }

    @Override
    public String create(Long userId, RequestUserDto req) {
        userRepository.save(UserMapper.INSTANCE.toEntity(req));
        return "User created successfully";
    }

    public ResponseDto<ResponseUserDto> get(Long userId, RequestUserDto req) {
        QueryRequest<User> queryRequest = QueryRequestBuilder.build(queryHelper, req, userId);

        QueryResult<User> queryResult = userRepository.findAll(queryRequest);

        return ResponseDto
                .<ResponseUserDto>builder()
                .data(queryResult
                        .results()
                            .stream()
                            .map(UserMapper.INSTANCE::toResponseDto)
                            .toList())
                .page(PageDto.builder()
                        .hasNext(queryResult.page().hasNext())
                        .records(queryResult.page().records())
                        .build())
                .build();
    }

    @Override
    public ResponseUserDto update(Long userId, RequestUserDto req) {
        return UserMapper.INSTANCE
                .toResponseDto(userRepository.save(UserMapper.INSTANCE
                        .toEntity(req)));
    }

    @Override
    public void delete(Long id, Long userId) {
        userRepository.deleteById(id);
    }
}
