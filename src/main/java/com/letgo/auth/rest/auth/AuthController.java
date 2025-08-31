package com.letgo.auth.rest.auth;

import com.letgo.auth.domain.service.LoginService;
import com.letgo.auth.domain.service.jwt.JwtResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final LoginService loginService;

  @PostMapping("/login")
  public JwtResponse<?> login(@Valid @RequestBody LoginRequest loginRequest) {
    return loginService.login(loginRequest);
  }
}
