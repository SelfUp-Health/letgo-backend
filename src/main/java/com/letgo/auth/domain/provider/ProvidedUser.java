package com.letgo.auth.domain.provider;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProvidedUser {

  private String providerId;

  private LetGoAuthProvider provider;

  private String fullName;

  private String email;

}
