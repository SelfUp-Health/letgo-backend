package com.letgo.auth.domain.provider.custom;

import com.letgo.auth.domain.provider.*;
import com.letgo.auth.domain.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CustomTokenHandler implements ProviderTokenHandler {

  private final JwtService jwtService;

  @Override
  public boolean supports(LetGoAuthProvider type) {
    return LetGoAuthProvider.CUSTOM.equals(type);
  }

  @Override
  public ProvidedUser getUserFromToken(String token) {
    Boolean validate = jwtService.validate(token);
    if (!validate) {
      return null;
    }

    String username = jwtService.getUsernameFromToken(token);

    return new ProvidedUser(username, LetGoAuthProvider.CUSTOM, null, null);
  }

  @Override
  public ProvidedUser getUserFromAuthHeader(Authentication auth) {
    String token = ((BearerTokenAuthenticationToken) auth).getToken();
    return getUserFromToken(token);
  }
}
