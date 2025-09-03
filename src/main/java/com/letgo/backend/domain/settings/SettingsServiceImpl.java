package com.letgo.backend.domain.settings;

import com.letgo.backend.domain.settings.api.SettingsService;
import com.letgo.backend.domain.settings.spi.SettingsDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class SettingsServiceImpl implements SettingsService {

  private final SettingsDataService dataService;

  @Override
  public Settings findByUserId(Long userId) {
    return dataService.findByUserId(userId);
  }
  public void save(Settings settings) {
    dataService.save(settings);
  }
}
