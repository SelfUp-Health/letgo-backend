package com.letgo.backend.infrastructure.rest.settings;

import com.letgo.backend.domain.settings.Settings;
import jakarta.validation.constraints.NotNull;

public record UpdateSettingsRequest(
  @NotNull
  Boolean notificationsEnabled,
  @NotNull
  Boolean darkModeEnabled,
  @NotNull
  String language
) {
  Settings toDomain(Long userId) {
    Settings domain = SettingsMapper.INSTANCE.toDomain(this);
    domain.setUserId(userId);
    return domain;
  }
};
