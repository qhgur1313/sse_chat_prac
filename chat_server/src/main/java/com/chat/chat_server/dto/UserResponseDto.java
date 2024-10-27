package com.chat.chat_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserResponseDto {
  private Long id;
  private String nickname;
  private LocalDateTime createdAt;
}
