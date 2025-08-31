package com.letgo.auth.domain.provider.custom;

import com.letgo.auth.domain.provider.ProvidedUser;
import com.letgo.auth.domain.spi.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final CustomTokenHandler tokenHandler;
  private final UserDataService dataService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // Authenticate and get the JWT token
    ProvidedUser providedUser = tokenHandler.getUserFromAuthHeader(authentication);

    return dataService.findByUsername(providedUser.getProviderId())
      .map(user -> new UsernamePasswordAuthenticationToken(user, null, null))
      .orElseThrow(() -> new UsernameNotFoundException(providedUser.getProvider() + ":" + providedUser.getProviderId()));
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(BearerTokenAuthenticationToken.class);
  }
}
