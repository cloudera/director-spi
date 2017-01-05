package com.cloudera.director.spi.v2.model.exception;

/**
 * Tests {@link UnrecoverableProviderException}.
 */
public class UnrecoverableProviderExceptionTest extends AbstractPluginExceptionTest {

  @Override
  protected Class<? extends UnrecoverableProviderException> getExpectedExceptionClass() {
    return UnrecoverableProviderException.class;
  }
}
