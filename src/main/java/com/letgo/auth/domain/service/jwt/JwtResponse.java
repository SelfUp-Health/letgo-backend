package com.letgo.auth.domain.service.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse<T> {
  private String token;
  private String refreshToken;
  private T user;
}
