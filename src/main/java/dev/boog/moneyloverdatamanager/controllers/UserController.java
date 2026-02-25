package dev.boog.moneyloverdatamanager.controllers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestUserDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseUserDto;
import dev.boog.moneyloverdatamanager.service.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public record UserController(@Qualifier("userService") Service<RequestUserDto, ResponseUserDto, Long> service) implements Controller<RequestUserDto, ResponseUserDto, Long> {


    @PostMapping
    public ResponseEntity<String> create(@RequestHeader("User-ID") String userId, @RequestBody RequestUserDto dto) {
        return service.create(userId, dto);
    }

    @GetMapping
    public ResponseEntity<List<ResponseUserDto>> get(@RequestHeader("User-ID") String userId, @RequestParam("id") Long id) {
        return service.get(userId, id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseUserDto>> getAll(String userId) {
        return service.getAll(userId);
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
