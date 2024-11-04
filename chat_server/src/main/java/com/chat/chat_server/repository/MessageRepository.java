package com.chat.chat_server.repository;

import com.chat.chat_server.domain.Message;
import com.chat.chat_server.dto.MessageResponseDto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MessageRepository extends ReactiveCrudRepository<Message, Long> {
  @Query("SELECT m.id, m.content, m.timestamp, m.user_id as user_id, u.nickname as nickname FROM message m JOIN user u ON m.user_id = u.id")
  Flux<MessageResponseDto> findAllWithUserNickname();
}
