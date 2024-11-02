package com.chat.chat_server.service;

import com.chat.chat_server.domain.Message;
import com.chat.chat_server.dto.MessageRequestDto;
import com.chat.chat_server.dto.MessageResponseDto;
import com.chat.chat_server.repository.MessageRepository;
import com.chat.chat_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {
  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  private final Sinks.Many<MessageResponseDto> sink = Sinks.many().multicast().onBackpressureBuffer();

  public Mono<MessageResponseDto> sendMessage(MessageRequestDto messageRequestDto) {
    return userRepository.findById(messageRequestDto.getUserId())
        .flatMap(user -> {
          Message message = new Message();
          message.setUserId(user.getId());
          message.setContent(messageRequestDto.getContent());
          message.setTimestamp(LocalDateTime.now());

          return messageRepository.save(message)
              .map(savedMessage -> {
                MessageResponseDto messageResponseDto = new MessageResponseDto(
                    savedMessage.getId(),
                    savedMessage.getUserId(),
                    savedMessage.getContent(),
                    user.getNickname(),
                    savedMessage.getTimestamp()
                );
                sink.tryEmitNext(messageResponseDto);
                return messageResponseDto;
              });
        })
        .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
  }

  public Flux<MessageResponseDto> streamMessages() {
    return sink.asFlux().doOnCancel(() -> {
      sink.asFlux().blockLast();
    });
  }

  public Flux<MessageResponseDto> getAllMessages() {
    return messageRepository.findAll()
        .map(message -> {
          MessageResponseDto messageResponseDto = new MessageResponseDto(
              message.getId(),
              message.getUserId(),
              message.getContent(),
              null,
              message.getTimestamp()
          );
          userRepository.findById(message.getUserId())
              .doOnNext(user -> messageResponseDto.setNickname(user.getNickname()))
              .subscribe();
          return messageResponseDto;
        });
  }
}
