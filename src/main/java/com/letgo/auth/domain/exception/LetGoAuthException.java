package com.letgo.auth.domain.exception;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PUBLIC)
public class LetGoAuthException extends RuntimeException {

  private final ErrorDefinition messageDefinition;
  private final Object[] params;

  /**
   * Package private exception, not meant to be used!
   *
   * @param errorDefinition exception details code
   * @param cause           root cause
   * @param params          optional lit of parameters to use during formatting
   */
  protected LetGoAuthException(ErrorDefinition errorDefinition, Exception cause, Object... params) {
    super(LetGoAuthException.getMessage(errorDefinition.getMessageTemplate(), params), cause);
    this.messageDefinition = errorDefinition;
    this.params = params;
  }

  private static String getMessage(String messageTemplate, Object... params) {
    if (params.length > 0) {
      return String.format(messageTemplate, params);
    }
    return messageTemplate;
  }

  /**
   * Creates a new exception based on the errorDefinition and parameters.
   * If the error definition contains placeholders for parameters, then the params will be
   * used to replace the placeholders. E.g.
   * <p>
   * throw GlassyException.of(AuthErrDef.USER_DOES_NOT_EXIST, "tyler_durden");
   * </p>
   *
   * @param errorDefinition exception details code
   * @param params          optional parameters to use during formatting
   *
   * @return parametrized exception instance
   */
  public static LetGoAuthException of(ErrorDefinition errorDefinition, Object... params) {
    return new LetGoAuthException(errorDefinition, null, params);
  }

  /**
   * Creates a new exception based on the errorDefinition and parameters.
   * If the error definition contains placeholders for parameters, then the params will be
   * used to replace the placeholders. E.g.
   * <p>
   * Optionally, you can add a cause (Exception) as the last parameter:
   * </p>
   * throw GlassyException.of(AuthErrDef.USER_DOES_NOT_EXIST, "tyler_durden", e);
   *
   * @param errorDefinition exception details code
   * @param cause           existing exception to be rethrown
   * @param params          optional lit of parameters to use during formatting
   *
   * @return parametrized exception instance
   */
  public static LetGoAuthException of(ErrorDefinition errorDefinition, Exception cause, Object... params) {
    return new LetGoAuthException(errorDefinition, cause, params);
  }

}
