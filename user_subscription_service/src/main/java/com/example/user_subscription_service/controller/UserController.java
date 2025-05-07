package com.example.user_subscription_service.controller;

import com.example.user_subscription_service.dto.UserDto;
import com.example.user_subscription_service.model.User;
import com.example.user_subscription_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    //
    @GetMapping
    public Collection<UserDto> getAll() {
        return userService.getAll();
    }

    // Создание пользователя
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserDto userDto) {
        userService.create(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Получение информации о пользователе по ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto user = userService.getById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Обновление данных пользователя
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        userService.update(userDto);
        return ResponseEntity.ok().build();
    }

    // Удаление пользователя
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
         userService.delete(id);
    }
}
