package com.letgo.auth.domain.spi;

import com.letgo.auth.domain.user.User;
import java.util.Optional;

public interface UserDataService {

  User save(User user);
  Optional<User> findByUsername(String username);
  Optional<User> findByProviderAndProviderId(String providerId, String provider);
}
