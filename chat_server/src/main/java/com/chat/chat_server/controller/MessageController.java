package com.chat.chat_server.controller;

import com.chat.chat_server.dto.MessageRequestDto;
import com.chat.chat_server.dto.MessageResponseDto;
import com.chat.chat_server.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
  private final MessageService messageService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<MessageResponseDto> sendMessage(@RequestBody MessageRequestDto messageRequestDto) {
    return messageService.sendMessage(messageRequestDto);
  }

  @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<MessageResponseDto> streamMessages() {
    return messageService.streamMessages();
  }

  @GetMapping
  public Flux<MessageResponseDto> getAllMessages() {
    return messageService.getAllMessages();
  }
}
