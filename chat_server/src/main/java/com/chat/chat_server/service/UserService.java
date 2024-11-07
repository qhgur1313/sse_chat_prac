package com.chat.chat_server.service;

import com.chat.chat_server.domain.User;
import com.chat.chat_server.dto.UserCreateRequestDto;
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



  public Flux<UserResponseDto> getAllUsers() {
    return userRepository.findAll()
        .map(user -> new UserResponseDto(user.getId(), user.getNickname(), user.getCreatedAt()));
  }
}
