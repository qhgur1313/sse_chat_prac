package com.chat.chat_server.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("message")
public class Message {
  @Id
  private Long id;

  private String content;
  private LocalDateTime timestamp;
  @Column("user_id")
  private Long userId;

  @Column("chat_room_id")
  private Long chatRoomId;
}
