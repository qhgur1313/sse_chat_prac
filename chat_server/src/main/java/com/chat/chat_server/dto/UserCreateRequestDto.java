package com.chat.chat_server.dto;

import lombok.Data;

@Data
public class UserCreateRequestDto {
  private String email;
  private String nickname;
  private String password;
}
