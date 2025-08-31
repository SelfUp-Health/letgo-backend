package com.letgo.auth.domain.provider.facebook;

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
public class FacebookAuthenticationProvider implements AuthenticationProvider {

  private final FacebookTokenHandler facebookTokenHandler;
  private final UserDataService dataService;


  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    ProvidedUser providedUser = facebookTokenHandler.getuserFromAuthHeader(authentication);
    if (providedUser == null) {
      return null;
    }

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
