package com.letgo.auth.domain.exception;

import org.springframework.http.HttpStatus;

public interface ErrorDefinition {

  String getCode();

  String getMessageTemplate();

  default HttpStatus getHttpStatus() {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}

