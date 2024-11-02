package com.chat.chat_server.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("user")
@NoArgsConstructor
public class User {
  @Id
  private Long id;

  private String nickname;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
