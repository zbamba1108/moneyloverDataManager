package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestUserDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseUserDto;
import dev.boog.moneyloverdatamanager.exceptions.validations.Read;
import dev.boog.moneyloverdatamanager.exceptions.validations.Write;
import dev.boog.moneyloverdatamanager.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User API")
@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(description = "create a new user")
    @PostMapping
    public ResponseEntity<String> create(@Validated(Write.class) @RequestBody RequestUserDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(null, dto));
    }

    @Operation(description = "search one or more transactions based on input request")
    @PostMapping("/search")
    public ResponseEntity<ResponseDto<ResponseUserDto>> get(@RequestBody(required = false)
                                                            @Validated(Read.class)
                                                            RequestUserDto req) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.get(null, req));
    }

    @Operation(description = "update an existing transaction")
    @PutMapping
    public ResponseEntity<ResponseUserDto> update(@RequestBody
                                                  @Validated(Write.class)
                                                  RequestUserDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(null, dto));
    }

    @Operation(description = "delete an existing transaction")
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody
                                         @Validated(Write.class)
                                         RequestUserDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.delete(null, dto));
    }
}
