package com.letgo.auth.domain.spi;

import com.letgo.auth.domain.user.UserWithPassword;
import com.letgo.auth.domain.user.User;
import java.util.Optional;

public interface UserDataService {

  User save(User user);
  User save(UserWithPassword user);
  Optional<User> findByUsername(String username);
  Optional<UserWithPassword> findWithPasswordByUsername(String username);
  Optional<User> findByProviderIdAndProvider(String providerId, String provider);
}
