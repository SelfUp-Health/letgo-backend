package com.letgo.backend;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Validated
public class TestController {

  @PostMapping
  public HttpStatus testEndpoint(@Valid @RequestBody TestObject obj) {
    return HttpStatus.OK;
  }
}
