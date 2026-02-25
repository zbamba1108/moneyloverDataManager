package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestUserDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseUserDto;
import dev.boog.moneyloverdatamanager.services.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public record UserController(@Qualifier("userService") Service<RequestUserDto, ResponseUserDto, Long> service) implements Controller<RequestUserDto, ResponseUserDto> {


    @PostMapping
    public ResponseEntity<String> create(@RequestHeader("User-ID") String userId, @RequestBody RequestUserDto dto) {
        return service.create(userId, dto);
    }

    @GetMapping
    public ResponseEntity<List<ResponseUserDto>> get(@RequestHeader("User-ID") String userId, @RequestParam(value = "query", required = false) String query) {
        return service.get(userId, query);
    }

    @PutMapping
    public ResponseEntity<ResponseUserDto> update(@RequestHeader("User-ID") String userId, RequestUserDto dto) {
        return service.update(userId, dto);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestHeader("User-ID") String userId, RequestUserDto dto) {
        return service.delete(userId, dto);
    }
}
