package com.letgo.auth.domain.provider.google;

import com.letgo.auth.domain.provider.ProvidedUser;
import com.letgo.auth.domain.spi.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleAuthenticationProvider implements AuthenticationProvider {

  private final GoogleTokenHandler tokenHandler;
  private final UserDataService dataService;

  @Value("${spring.security.oauth2.resourceserver.google.jwt.issuer-uri}")
  private String googleIssuerUri;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // Authenticate and get the JWT token
    ProvidedUser providedUser = tokenHandler.getuserFromAuthHeader(authentication);

    return dataService.findByProviderAndProviderId(
        providedUser.getProviderId(),
        providedUser.getProvider().toString()
      ).map(user -> new UsernamePasswordAuthenticationToken(user, null, null))
      .orElseThrow(() -> new UsernameNotFoundException(providedUser.getProviderId() + ":" + providedUser.getProviderId()));
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(BearerTokenAuthenticationToken.class);
  }
}
