package com.letgo.backend.infrastructure.rest.settings;

import com.letgo.backend.domain.settings.api.SettingsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/settings")
@RequiredArgsConstructor
public class SettingsController {

  private final SettingsService settingsService;

  @GetMapping
  @PreAuthorize("#userId == authentication.principal.id")
  public SettingsResponse get(@PathVariable final Long userId) {
    return SettingsResponse.fromDomain(settingsService.findByUserId(userId));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("#userId == authentication.principal.id")
  public void save(@PathVariable final Long userId, @Valid @RequestBody final UpdateSettingsRequest request) {
    settingsService.save(request.toDomain(userId));
  }
}
