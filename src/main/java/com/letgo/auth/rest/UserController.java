package com.letgo.auth.rest;

import com.letgo.auth.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class UserController {

  @GetMapping
  public User getMineUser(
    @AuthenticationPrincipal User userDetails
  ) {
    return userDetails;
  }
}
