package com.letgo.backend.infrastructure.rest.settings;

import com.letgo.backend.domain.settings.Settings;

public record SettingsResponse(
  Boolean notificationsEnabled,
  Boolean darkModeEnabled,
  String language
) {
  static SettingsResponse fromDomain(Settings settings) {
    return SettingsMapper.INSTANCE.toDto(settings);
  }
};
