package com.chat.chat_server.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class LoginResponseDto {
  private Long id;
  private String nickname;
  private String token;
}
