package com.letgo.backend.infrastructure.persistence.settings;

import com.letgo.backend.domain.settings.Settings;
import com.letgo.backend.domain.settings.spi.SettingsDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SettingsDataServiceImpl implements SettingsDataService {

  private final SettingsRepository settingsRepository;
  private final SettingsEntityMapper entityMapper;

  @Override
  @Transactional(readOnly = true)
  public Settings findByUserId(Long userId) {
    return settingsRepository.findByUserId(userId)
      .map(entityMapper::toDomain)
      .orElse(null); // Or return a default settings object
  }

  @Override
  @Transactional
  public void save(Settings settings) {
    SettingsEntity entity = entityMapper.toEntity(settings);
    settingsRepository.save(entity);
  }
}
