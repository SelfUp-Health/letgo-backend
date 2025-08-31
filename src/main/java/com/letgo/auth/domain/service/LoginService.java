package com.letgo.auth.domain.service;

import com.letgo.auth.domain.provider.custom.CustomAuthenticationProvider;
import com.letgo.auth.domain.service.jwt.JwtResponse;
import com.letgo.auth.domain.service.jwt.JwtService;
import com.letgo.auth.domain.spi.UserDataService;
import com.letgo.auth.rest.auth.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoginService {

  private final JwtService jwtService;
  private final UserDataService dataService;
  private final PasswordEncoder passwordEncoder;

  public JwtResponse<?> login(LoginRequest loginRequest) {
    var userWithPassword = dataService.findWithPasswordByUsername(loginRequest.username())
      .orElseThrow(() -> new UsernameNotFoundException(loginRequest.username()));

    if (passwordEncoder.matches(loginRequest.password(), userWithPassword.getPassword())) {
      String accessToken = jwtService.generate(userWithPassword.getUsername());
      return JwtResponse.builder()
        .token(accessToken)
        .refreshToken("none")
        // TODO REMOVE USER WITH PASSWORD
        .user(userWithPassword.toUser())
        .build();
    }

    throw new BadCredentialsException(loginRequest.username());
  }
}
