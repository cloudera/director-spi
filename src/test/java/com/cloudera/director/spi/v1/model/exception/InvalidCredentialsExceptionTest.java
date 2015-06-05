package com.cloudera.director.spi.v1.model.exception;

/**
 * Tests {@link InvalidCredentialsException}.
 */
public class InvalidCredentialsExceptionTest extends AbstractPluginExceptionTest {

  @Override
  protected Class<? extends InvalidCredentialsException> getExpectedExceptionClass() {
    return InvalidCredentialsException.class;
  }
}
