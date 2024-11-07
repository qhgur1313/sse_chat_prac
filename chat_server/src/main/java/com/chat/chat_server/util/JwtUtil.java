package com.chat.chat_server.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimAccessor;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;

@Component
public class JwtUtil {
  public static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  // NimbusReactiveJwtDecoder를 이용하여 비동기 검증 및 디코딩
  private final ReactiveJwtDecoder jwtDecoder = NimbusReactiveJwtDecoder
      .withSecretKey(SECRET_KEY) // 비밀키 설정
      .build();

  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 10 * 1000)) // 10시간 유효
        .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
        .compact();
  }

  public Mono<Jwt> extractJwt(String token) {
    return jwtDecoder.decode(token);
  }

  public Mono<String> extractEmail(String token) {
    return extractJwt(token).map(JwtClaimAccessor::getSubject);
  }

  public Mono<Boolean> isTokenExpired(String token) {
    return extractJwt(token).map(jwt -> {
      Instant expiration = jwt.getExpiresAt(); // Instant 타입 반환
      if (expiration == null) {
        // 만료 시간이 없는 경우 false 반환 (유효하다고 간주)
        return false;
      }
      return expiration.isBefore(Instant.now()); // 현재 시간과 비교
    });
  }

  public Mono<Boolean> validateToken(String token, String email) {
    return extractJwt(token).flatMap(jwt -> {
      String subject = jwt.getSubject();
      return isTokenExpired(token).map(isExpired -> subject.equals(email) && !isExpired);
    }).onErrorResume(e -> Mono.error(new RuntimeException("Invalid token")));
  }

  public Mono<Authentication> getAuthentication(String token) {
    return extractEmail(token).map(email ->
        new UsernamePasswordAuthenticationToken(
            email,
            null,
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        )
    );
  }
}
