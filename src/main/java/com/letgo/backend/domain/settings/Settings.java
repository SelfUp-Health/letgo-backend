package com.letgo.backend.domain.settings;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Settings {

  private Long userId;
  private Boolean notificationsEnabled;
  private Boolean darkModeEnabled;
  private Language language;
}
