package com.chat.chat_server.domain;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("message")
public class Message {
  @Id
  private Long id;

  private String content;
  private LocalDateTime timestamp;
  private Long userId;
  private Long chatRoomId;
}
