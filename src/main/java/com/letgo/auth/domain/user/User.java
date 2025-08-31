package com.letgo.auth.domain.user;

import com.letgo.auth.domain.provider.LetGoAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class User {

  private Long id;

  private String username;

  private String fullName;

  private LetGoAuthProvider provider;

  private String providerId;
  private String email;
}
