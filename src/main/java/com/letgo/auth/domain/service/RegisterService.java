package com.letgo.auth.domain.service;

import com.letgo.auth.domain.provider.*;
import com.letgo.auth.domain.spi.UserDataService;
import com.letgo.auth.domain.user.User;
import com.letgo.auth.domain.user.UserWithPassword;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegisterService {

  private final PasswordEncoder passwordEncoder;
  private final UserDataService dataService;
  private final List<ProviderTokenHandler> providerTokenHandlers;

  public User register(UserWithPassword userWithPassword) {

    dataService.findByUsername(userWithPassword.getUsername())
      .ifPresent(user -> {throw new IllegalArgumentException("User already exists");});

    userWithPassword.setPassword(passwordEncoder.encode(userWithPassword.getPassword()));

    return dataService.save(userWithPassword);
  }

  public User registerWithProvider(String authToken, LetGoAuthProvider authProvider) {

    ProviderTokenHandler tokenHandler = providerTokenHandlers.stream()
      .filter(providerTokenHandler -> providerTokenHandler.supports(authProvider))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("No such provider token"));

    ProvidedUser providedUser = tokenHandler.getUserFromToken(authToken);

    dataService.findByProviderIdAndProvider(providedUser.getProviderId(), authProvider.toString())
      .ifPresent(user -> {throw new IllegalArgumentException("User for given provider data already exists");});


    var user = User.builder()
      .provider(authProvider)
      .providerId(providedUser.getProviderId())
      .username(providedUser.getProviderId())
      .fullName(providedUser.getFullName())
      .email(providedUser.getEmail())
      .build();

    return dataService.save(user);
  }
}
