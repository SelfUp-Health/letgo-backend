package com.letgo.backend.domain.settings.spi;

import com.letgo.backend.domain.settings.Settings;

public interface SettingsDataService {
  Settings findByUserId(Long userId);
  void save(Settings domain);
}
