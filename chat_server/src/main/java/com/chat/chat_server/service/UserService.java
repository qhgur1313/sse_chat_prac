package com.chat.chat_server.service;

import com.chat.chat_server.domain.User;
import com.chat.chat_server.dto.UserRequestDto;
import com.chat.chat_server.dto.UserResponseDto;
import com.chat.chat_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public Mono<UserResponseDto> createUser(UserRequestDto userRequestDto) {
    User user = new User();
    user.setNickname(userRequestDto.getNickname());
    user.setCreatedAt(LocalDateTime.now());
    return userRepository.save(user)
        .map(savedUser -> new UserResponseDto(savedUser.getId(), savedUser.getNickname(), savedUser.getCreatedAt()));
  }

  public Flux<UserResponseDto> getAllUsers() {
    return userRepository.findAll()
        .map(user -> new UserResponseDto(user.getId(), user.getNickname(), user.getCreatedAt()));
  }
}
