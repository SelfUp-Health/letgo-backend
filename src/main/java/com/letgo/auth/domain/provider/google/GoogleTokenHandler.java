package com.letgo.auth.domain.provider.google;

import com.letgo.auth.domain.provider.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
class GoogleTokenHandler implements ProviderTokenHandler {

  private final JwtDecoder googleDecoder;
  private final JwtAuthenticationProvider jwtAuthProvider;

  public GoogleTokenHandler(
    @Value("${spring.security.oauth2.resourceserver.google.jwt.issuer-uri}") String googleIssuerUri
  ) {
    this.googleDecoder = JwtDecoders.fromIssuerLocation(googleIssuerUri);
    this.jwtAuthProvider = new JwtAuthenticationProvider(googleDecoder);
  }

  @Override
  public boolean supports(LetGoAuthProvider type) {
    return LetGoAuthProvider.GOOGLE.equals(type);
  }

  @Override
  public ProvidedUser getUserFromToken(String token) {
    return getUserJwtToken(googleDecoder.decode(token));
  }

  private ProvidedUser getUserJwtToken(Jwt token) {

    // Map JWT claims to SocialUser
    String id = token.getClaimAsString("sub");
    String name = token.getClaimAsString("name");
    String email = token.getClaimAsString("email");

    return new ProvidedUser(
      id,
      LetGoAuthProvider.GOOGLE,
      name,
      email
    );
  }

  @Override
  public ProvidedUser getuserFromAuthHeader(Authentication auth) {
    JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) jwtAuthProvider.authenticate(auth);
    return getUserJwtToken(jwtAuth.getToken());
  }
}
