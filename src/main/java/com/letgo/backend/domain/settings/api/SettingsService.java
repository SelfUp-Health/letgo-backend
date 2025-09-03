package com.letgo.backend.domain.settings.api;

import com.letgo.backend.domain.settings.Settings;

public interface SettingsService {
  Settings findByUserId(Long userId);
  void save(Settings domain);
}
