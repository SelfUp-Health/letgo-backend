package com.letgo.auth.domain.service.jwt;

import com.letgo.auth.domain.exception.ErrorDefinition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtErrDef implements ErrorDefinition {
  INVALID_REFRESH_TOKEN("Incorrect refresh token - invalid or already used", HttpStatus.FORBIDDEN),
  CANNOT_PARSE_TOKEN("Cannot parse token", HttpStatus.FORBIDDEN),
  INCORRECT_TOKEN_PAIR(
    "Incorrect token pair, refresh token invalidated. Please make a new signin request",
    HttpStatus.FORBIDDEN
  ),
  REFRESH_TOKEN_EXPIRED("Refresh token was expired. Please make a new signin request", HttpStatus.FORBIDDEN);

  private final String messageTemplate;
  private final HttpStatus httpStatus;

  @Override
  public String getCode() {
    return name();
  }
}
