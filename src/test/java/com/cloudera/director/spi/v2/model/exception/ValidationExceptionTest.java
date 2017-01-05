package com.cloudera.director.spi.v2.model.exception;

/**
 * Tests {@link ValidationException}.
 */
public class ValidationExceptionTest extends AbstractPluginExceptionTest {

  @Override
  protected Class<? extends ValidationException> getExpectedExceptionClass() {
    return ValidationException.class;
  }
}
