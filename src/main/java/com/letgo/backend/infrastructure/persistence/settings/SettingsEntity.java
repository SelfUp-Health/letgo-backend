package com.letgo.backend.infrastructure.persistence.settings;

import com.letgo.backend.domain.settings.Language;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "settings", schema = "mng")
public class SettingsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "user_id", unique = true, nullable = false)
  private Long userId;

  @Column(name = "notifications_enabled")
  private Boolean notificationsEnabled;

  @Column(name = "dark_mode_enabled")
  private Boolean darkModeEnabled;

  @Enumerated(EnumType.STRING)
  private Language language;
}
