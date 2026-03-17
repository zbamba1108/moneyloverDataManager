package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestUserDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseUserDto;
import dev.boog.moneyloverdatamanager.services.UserService;
import dev.boog.moneyloverdatamanager.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User API")
@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService service;

    public UserController(@Qualifier("userService") UserService service) {
        this.service = service;
    }

    @Operation(description = "create a new user")
    @PostMapping
    public ResponseEntity<String> create(@RequestBody RequestUserDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(null, dto));
    }

    @Operation(description = "search one or more transactions based on input request")
    @PostMapping("/search")
    public ResponseEntity<ResponseDto<ResponseUserDto>> get(@RequestHeader(value = Constants.Headers.USER_ID, required = false) String userId,
                                                            @RequestBody(required = false) RequestUserDto req) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.get(userId, req));
    }

    @Operation(description = "update an existing transaction")
    @PutMapping
    public ResponseEntity<ResponseUserDto> update(@RequestHeader(value = Constants.Headers.USER_ID, required = false) String userId,
                                                  @RequestBody RequestUserDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(userId, dto));
    }

    @Operation(description = "delete an existing transaction")
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestHeader(value = Constants.Headers.USER_ID, required = false) String userId,
                                         @RequestBody RequestUserDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.delete(userId, dto));
    }
}
