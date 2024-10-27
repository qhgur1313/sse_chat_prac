package com.chat.chat_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageResponseDto {
  private Long id;
  private Long userId;
  private String content;
  private String nickname;
  private LocalDateTime timestamp;
}
