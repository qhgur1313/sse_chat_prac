package com.chat.chat_server.service;

import com.chat.chat_server.domain.User;
import com.chat.chat_server.dto.LoginResponseDto;
import com.chat.chat_server.dto.UserCreateRequestDto;
import com.chat.chat_server.dto.UserResponseDto;
import com.chat.chat_server.repository.UserRepository;
import com.chat.chat_server.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class AuthService {
  @Autowired
  private UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  private final JwtUtil jwtUtil = new JwtUtil();

  public Mono<LoginResponseDto> login(String email, String password) {
    return userRepository.findByEmail(email)
        .flatMap(user -> {
          if (passwordEncoder.matches(password, user.getPassword())) {
            String token = jwtUtil.generateToken(user.getEmail());
            return Mono.just(new LoginResponseDto(user.getId(), user.getNickname(), token));
          } else {
            return Mono.error(new RuntimeException("Invalid password"));
          }
        });
  }

  public Mono<UserResponseDto> createUser(UserCreateRequestDto userRequestDto) {
    User user = new User();
    user.setEmail(userRequestDto.getEmail());
    user.setNickname(userRequestDto.getNickname());
    user.setCreatedAt(LocalDateTime.now());
    user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
    return userRepository.save(user)
        .map(savedUser -> new UserResponseDto(savedUser.getId(), savedUser.getNickname(), savedUser.getCreatedAt()));
  }
}
