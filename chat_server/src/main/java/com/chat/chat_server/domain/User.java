package com.chat.chat_server.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("user")
@NoArgsConstructor
public class User {
  @Id
  private Long id;
  private String email;
  private String password;
  private String nickname;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
