package com.chat.chat_server.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
  private String email;
  private String password;
}
