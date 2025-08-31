package com.letgo.auth.rest.auth;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(@NotEmpty String username, @NotEmpty String password) {}
