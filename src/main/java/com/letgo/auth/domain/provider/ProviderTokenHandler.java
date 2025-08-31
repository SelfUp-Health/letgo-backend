package com.letgo.auth.domain.provider;

import org.springframework.security.core.Authentication;

public interface ProviderTokenHandler {

  boolean supports(LetGoAuthProvider type);
  ProvidedUser getUserFromToken(String token);
  ProvidedUser getUserFromAuthHeader(Authentication auth);
}
