package com.chat.chat_server.controller;

import com.chat.chat_server.dto.UserRequestDto;
import com.chat.chat_server.dto.UserResponseDto;
import com.chat.chat_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
    return userService.createUser(userRequestDto);
  }

  @GetMapping
  public Flux<UserResponseDto> getAllUsers() {
    return userService.getAllUsers();
  }
}
