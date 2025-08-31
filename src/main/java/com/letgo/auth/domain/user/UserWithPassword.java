
package com.letgo.auth.domain.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class UserWithPassword extends User {

  private String password;

  public User toUser(){
    return User.builder()
      .username(getUsername())
      .email(getEmail())
      .fullName(getFullName())
      .provider(getProvider())
      .providerId(getProviderId())
      .build();
  }
}
