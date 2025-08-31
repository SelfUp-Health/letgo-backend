
package com.letgo.auth.rest;

import com.letgo.auth.domain.provider.LetGoAuthProvider;
import com.letgo.auth.domain.user.UserWithPassword;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWithPasswordDto {

  @NotNull
  private String username;

  @NotNull
  private String password;

  private String email;

  UserWithPassword toDomain() {
    return UserWithPassword.builder()
      .username(username)
      .password(password)
      .email(email)
      .provider(LetGoAuthProvider.CUSTOM)
      .build();
  }
}
