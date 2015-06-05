package com.cloudera.director.spi.v1.model.exception;

/**
 * Tests {@link ValidationException}.
 */
public class ValidationExceptionTest extends AbstractPluginExceptionTest {

  @Override
  protected Class<? extends ValidationException> getExpectedExceptionClass() {
    return ValidationException.class;
  }
}
