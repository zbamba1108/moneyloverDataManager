package dev.boog.moneyloverdatamanager.controllers.impl;

import dev.boog.moneyloverdatamanager.controllers.CRUDController;
import dev.boog.moneyloverdatamanager.dtos.request.RequestUserDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseUserDto;
import dev.boog.moneyloverdatamanager.services.UserService;
import dev.boog.moneyloverdatamanager.utils.Constants;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User API")
@RestController
@RequestMapping(path = "/api/users")
public class UserController implements CRUDController<RequestUserDto, ResponseUserDto> {

    private final UserService<RequestUserDto, ResponseUserDto> service;

    public UserController(@Qualifier("userService") UserService<RequestUserDto, ResponseUserDto> service) {
        this.service = service;
    }

    @Operation(description = "create a new user")
    @PostMapping
    public ResponseEntity<String> create(@RequestBody RequestUserDto dto) {
        return service.create(null, dto);
    }

    @Override
    public ResponseEntity<String> create(String userId, RequestUserDto dto) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(description = "search one or more transactions based on input request")
    @PostMapping("/search")
    public ResponseEntity<ResponseDto<ResponseUserDto>> get(@RequestHeader(value = Constants.Headers.USER_ID, required = false) String userId,
                                                            @RequestBody(required = false) RequestUserDto req) {
        return service.get(userId, req);
    }

    @Operation(description = "update an existing transaction")
    @PutMapping
    public ResponseEntity<ResponseUserDto> update(@RequestHeader(value = Constants.Headers.USER_ID, required = false) String userId,
                                                  @RequestBody RequestUserDto dto) {
        return service.update(userId, dto);
    }

    @Operation(description = "delete an existing transaction")
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestHeader(value = Constants.Headers.USER_ID, required = false) String userId,
                                         @RequestBody RequestUserDto dto) {
        return service.delete(userId, dto);
    }
}
