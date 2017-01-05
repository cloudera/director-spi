package com.cloudera.director.spi.v2.model.exception;

/**
 * Tests {@link TransientProviderException}.
 */
public class TransientProviderExceptionTest extends AbstractPluginExceptionTest {

  @Override
  protected Class<? extends TransientProviderException> getExpectedExceptionClass() {
    return TransientProviderException.class;
  }
}
