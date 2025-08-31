package com.letgo.auth.rest;

import com.letgo.auth.domain.provider.LetGoAuthProvider;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvidedUserInfo {
  private String email;
  @NotNull
  private String token;
  @NotNull
  private LetGoAuthProvider provider;
}
