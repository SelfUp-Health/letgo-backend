package com.letgo.auth.rest;

import com.letgo.auth.domain.service.RegisterService;
import com.letgo.auth.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

  private final RegisterService registrationService;

  @PostMapping("/provider")
  @Operation(description = "Register with provider token")
  public User registerWithProvider(
    @Valid @RequestBody ProvidedUserInfo providerInfo
  ) {
//    final String token = jwtService.getTokenFromAuthHeader(authHeader);
    return registrationService.registerWithProvider(providerInfo.getToken(), providerInfo.getProvider());
  }
}
