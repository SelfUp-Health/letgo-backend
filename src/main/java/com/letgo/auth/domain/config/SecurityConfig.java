package com.letgo.auth.domain.config;

import com.letgo.auth.domain.provider.custom.CustomAuthenticationProvider;
import com.letgo.auth.domain.provider.facebook.FacebookAuthenticationProvider;
import com.letgo.auth.domain.provider.google.GoogleAuthenticationProvider;
import com.letgo.auth.domain.service.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Value("${spring.security.oauth2.resourceserver.google.jwt.issuer-uri}")
  private String googleIssuerUri;

  private final FacebookAuthenticationProvider facebookAuthenticationProvider;
  private final GoogleAuthenticationProvider googleAuthenticationProvider;
  private final CustomAuthenticationProvider customAuthenticationProvider;

  private final PublicRequestMatcher[] publicRequestMatchers;
//  private final CustomAuthenticationProvider customAuthenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(
    HttpSecurity http,
    AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver
  ) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(authz -> authz
        .requestMatchers(new AntPathRequestMatcher("/", HttpMethod.OPTIONS.name()))
        .permitAll()
        .requestMatchers(publicRequestMatchers)
        .permitAll()
        .anyRequest().authenticated()
      )
      .oauth2ResourceServer(oauth2 -> oauth2
        .authenticationManagerResolver(authenticationManagerResolver));
    return http.build();
  }

  @Bean
  public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver(JwtService jwtService) {
    Map<String, AuthenticationManager> authenticationManagers = new HashMap<>();

    authenticationManagers.put(googleIssuerUri, googleAuthenticationProvider::authenticate);
    authenticationManagers.put("custom", customAuthenticationProvider::authenticate);
    authenticationManagers.put("facebook", facebookAuthenticationProvider::authenticate); // Use a custom key

    return (request) -> {
      String token = extractBearerToken(request);
      if (token != null) {
        // Try to parse the token as a JWT to check for Google's issuer
        try {
          if (jwtService.validate(token)) {
            return authenticationManagers.get("custom");
          }

          String issuer = JwtDecoders.fromOidcIssuerLocation(googleIssuerUri).decode(token).getIssuer().toString();
          if (authenticationManagers.containsKey(issuer)) {
            return authenticationManagers.get(issuer);
          }
        } catch (Exception e) {
          // Token is not a valid JWT from Google, try Facebook
          return authenticationManagers.get("facebook");
        }
      }
      return null;
    };
  }

  private String extractBearerToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    return authHeader;
  }
}
