package com.letgo.auth.persistence.user;

import com.letgo.auth.domain.user.UserWithPassword;
import com.letgo.auth.domain.spi.UserDataService;
import com.letgo.auth.domain.user.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserPersistenceService implements UserDataService {

  private final UserRepository userRepository;
  private final UserConverter converter = UserConverter.INSTANCE;

  @Override
  public User save(User user) {
    var entity = converter.convert(user);
    var savedEntity = userRepository.save(entity);
    return converter.convert(savedEntity);
  }

  @Override
  public User save(UserWithPassword user) {
    var entity = converter.convert(user);
    var savedEntity = userRepository.save(entity);
    return converter.convert(savedEntity);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username).map(converter::convert);
  }

  @Override
  public Optional<UserWithPassword> findWithPasswordByUsername(String username) {
    return userRepository.findByUsername(username).map(converter::convertToUserWithPassword);
  }

  @Override
  public Optional<User> findByProviderIdAndProvider(String providerId, String provider) {
    return userRepository.findByProviderAndProviderId(provider, providerId).map(converter::convert);
  }
}
