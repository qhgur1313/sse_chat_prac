package com.chat.chat_server.filter;

import com.chat.chat_server.util.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class CustomJwtAuthFilter implements WebFilter {
  private final JwtUtil jwtUtil;

  public CustomJwtAuthFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    String token = exchange.getRequest().getQueryParams().getFirst("token");
    if (token != null) {
      // Todo. email 추출을 해서 token 검증을 하는건, email과 token의 상관관계가 중요할 경우 불합리하므로, 추후 수정이 필요함
      return jwtUtil.extractEmail(token)
          .flatMap(email -> jwtUtil.validateToken(token, email))
          .flatMap(isValid -> {
            if (isValid) {
              return jwtUtil.getAuthentication(token)
                  .map(SecurityContextImpl::new)
                  .flatMap(context -> chain.filter(exchange)
                      .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context))));
            } else {
              return chain.filter(exchange);
            }
          });

    }
    return chain.filter(exchange);
  }
}
