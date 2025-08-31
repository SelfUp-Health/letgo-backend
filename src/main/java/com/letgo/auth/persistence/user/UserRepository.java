package com.letgo.auth.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByProviderAndProviderId(String provider, String providerId);

    Optional<UserEntity> findByUsername(String username);
}
