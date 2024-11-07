package com.chat.chat_server.controller;

import com.chat.chat_server.dto.LoginRequestDto;
import com.chat.chat_server.dto.LoginResponseDto;
import com.chat.chat_server.dto.UserCreateRequestDto;
import com.chat.chat_server.dto.UserResponseDto;
import com.chat.chat_server.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  private AuthService authService;

  @PostMapping("/login")
  public Mono<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
    return authService.login(loginRequest.getEmail(), loginRequest.getPassword());
  }

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<UserResponseDto> createUser(@RequestBody UserCreateRequestDto userRequestDto) {
    return authService.createUser(userRequestDto);
  }
}
