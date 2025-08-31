package com.letgo.auth.rest;

import com.letgo.auth.domain.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping
  public String hello(@AuthenticationPrincipal User userDetails) {
    return "Hello World";
  }
}
