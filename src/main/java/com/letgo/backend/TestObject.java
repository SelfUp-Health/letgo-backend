package com.letgo.backend;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
public class TestObject {

  private long id;

  @NotNull
  @NotBlank
  @Size(max = 8)
  private String name;
}
