package com.letgo.backend.infrastructure.rest.settings;

import com.letgo.backend.domain.settings.Settings;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
interface SettingsMapper {
  SettingsMapper INSTANCE = Mappers.getMapper(SettingsMapper.class);

  SettingsResponse toDto(Settings settings);

  Settings toDomain(UpdateSettingsRequest request);
}
