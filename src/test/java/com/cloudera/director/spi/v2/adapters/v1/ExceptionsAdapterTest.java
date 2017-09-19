// (c) Copyright 2017 Cloudera, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.cloudera.director.spi.v2.adapters.v1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import com.cloudera.director.spi.v1.model.exception.AbstractPluginException;
import com.cloudera.director.spi.v1.model.exception.PluginExceptionCondition;
import com.cloudera.director.spi.v1.model.exception.PluginExceptionDetails;
import com.cloudera.director.spi.v1.model.exception.TransientProviderException;
import com.cloudera.director.spi.v1.model.exception.UnrecoverableProviderException;
import com.cloudera.director.spi.v1.model.exception.ValidationException;
import com.cloudera.director.spi.v1.model.exception.InvalidCredentialsException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ExceptionsAdapterTest {

  private static final String EXCEPTION_MESSAGE = "Test detail message";
  private static final Throwable EXCEPTION_CAUSE = mock(Throwable.class);

  private static final PluginExceptionDetails PLUGIN_EXCEPTION_DETAILS_V1 = newPluginExceptionDetailsV1();
  private static final com.cloudera.director.spi.v2.model.exception.PluginExceptionDetails PLUGIN_EXCEPTION_DETAILS_V2
      = newPluginExceptionDetailsV2();

  private static PluginExceptionDetails newPluginExceptionDetailsV1() {
    Map<String, List<PluginExceptionCondition>> conditionsByKey = Maps.newHashMap();
    List<PluginExceptionCondition> conditions = Lists.newArrayList(
        new PluginExceptionCondition(PluginExceptionCondition.Type.ERROR, "condition 1"),
        new PluginExceptionCondition(PluginExceptionCondition.Type.WARNING, "condition 2")
    );
    conditionsByKey.put("key1", conditions);
    return new PluginExceptionDetails(conditionsByKey);
  }

  private static com.cloudera.director.spi.v2.model.exception.PluginExceptionDetails newPluginExceptionDetailsV2() {
    Map<String, List<com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition>> conditionsByKey =
        Maps.newHashMap();
    List<com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition> conditions = Lists.newArrayList(
        new com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition(
            com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition.Type.ERROR, "condition 1"
        ),
        new com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition(
            com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition.Type.WARNING, "condition 2"
        )
    );
    conditionsByKey.put("key1", conditions);
    return new com.cloudera.director.spi.v2.model.exception.PluginExceptionDetails(conditionsByKey);
  }


  public static class TestException extends AbstractPluginException {
    TestException(String message, Throwable cause, PluginExceptionDetails details) {
      super(message, cause, details);
    }
  }

  @Test
  public void testAbstractException() {
    TestException testException = new TestException(EXCEPTION_MESSAGE, EXCEPTION_CAUSE, PLUGIN_EXCEPTION_DETAILS_V1);
    com.cloudera.director.spi.v2.model.exception.AbstractPluginException converted =
        ExceptionsAdapter.fromV1(testException);
    verifyException(converted);
  }

  @Test
  public void testTransientProviderException() {
    TransientProviderException testException =
        new TransientProviderException(EXCEPTION_MESSAGE, EXCEPTION_CAUSE, PLUGIN_EXCEPTION_DETAILS_V1);

    verifyExceptionConversion(testException,
        com.cloudera.director.spi.v2.model.exception.TransientProviderException.class);
  }

  @Test
  public void testUnrecoverableProviderException() {
    UnrecoverableProviderException testException =
        new UnrecoverableProviderException(EXCEPTION_MESSAGE, EXCEPTION_CAUSE, PLUGIN_EXCEPTION_DETAILS_V1);

    verifyExceptionConversion(testException,
        com.cloudera.director.spi.v2.model.exception.UnrecoverableProviderException.class);
  }

  @Test
  public void testValidationException() {
    ValidationException testException =
        new ValidationException(EXCEPTION_MESSAGE, EXCEPTION_CAUSE, PLUGIN_EXCEPTION_DETAILS_V1);

    verifyExceptionConversion(testException,
        com.cloudera.director.spi.v2.model.exception.ValidationException.class);
  }

  @Test
  public void testInvalidCredentialsException() {
    InvalidCredentialsException testException =
        new InvalidCredentialsException(EXCEPTION_MESSAGE, EXCEPTION_CAUSE, PLUGIN_EXCEPTION_DETAILS_V1);

    verifyExceptionConversion(testException,
        com.cloudera.director.spi.v2.model.exception.InvalidCredentialsException.class);
  }

  private <T extends AbstractPluginException> void verifyExceptionConversion(T exception, Class expectedClass) {
    com.cloudera.director.spi.v2.model.exception.AbstractPluginException converted =
        ExceptionsAdapter.fromV1(exception);
    assertThat(converted.getClass()).isEqualTo(expectedClass);
    verifyException(converted);
  }

  private void verifyException(
      com.cloudera.director.spi.v2.model.exception.AbstractPluginException convertedException) {
    assertThat(convertedException.getMessage()).isEqualTo(EXCEPTION_MESSAGE);
    assertThat(convertedException.getCause()).isEqualTo(EXCEPTION_CAUSE);
    assertThat(convertedException.getDetails().getConditionsByKey()).isEqualTo(
        PLUGIN_EXCEPTION_DETAILS_V2.getConditionsByKey());
  }
}
