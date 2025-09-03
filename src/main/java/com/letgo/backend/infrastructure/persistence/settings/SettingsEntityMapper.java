package com.letgo.backend.infrastructure.persistence.settings;

import com.letgo.backend.domain.settings.Settings;
import com.letgo.backend.infrastructure.rest.settings.SettingsResponse;
import com.letgo.backend.infrastructure.rest.settings.UpdateSettingsRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
interface SettingsEntityMapper {
  SettingsEntityMapper INSTANCE = Mappers.getMapper(SettingsEntityMapper.class);

  SettingsEntity toEntity(Settings settings);

  Settings toDomain(SettingsEntity request);
}
