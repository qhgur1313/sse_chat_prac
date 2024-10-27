package com.chat.chat_server.dto;

import lombok.Data;

@Data
public class MessageRequestDto {
  private Long userId;
  private String nickname;
  private String content;
}
