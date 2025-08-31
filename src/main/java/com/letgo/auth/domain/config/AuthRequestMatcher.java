package com.letgo.auth.domain.config;

import lombok.experimental.Delegate;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;


@Component
class AuthRequestMatcher implements PublicRequestMatcher {

  @Delegate
  private final RequestMatcher delegate = new OrRequestMatcher(
    antMatcher("/login", HttpMethod.GET),
    antMatcher("/register", HttpMethod.POST),
    antMatcher("/register/provider", HttpMethod.POST)
  );
}
