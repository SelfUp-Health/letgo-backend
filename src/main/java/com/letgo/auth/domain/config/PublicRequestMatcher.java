package com.letgo.auth.domain.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public interface PublicRequestMatcher extends RequestMatcher {

  default RequestMatcher antMatcher(String url, HttpMethod method) {
    String methodName = method == null ? null : method.name();
    // TODO FIX
    return new AntPathRequestMatcher(url, methodName);
  }
}
