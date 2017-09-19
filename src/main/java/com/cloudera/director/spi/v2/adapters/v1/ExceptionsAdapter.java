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

import com.cloudera.director.spi.v2.model.exception.InvalidCredentialsException;
import com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition;
import com.cloudera.director.spi.v2.model.exception.AbstractPluginException;
import com.cloudera.director.spi.v2.model.exception.PluginExceptionDetails;
import com.cloudera.director.spi.v2.model.exception.TransientProviderException;
import com.cloudera.director.spi.v2.model.exception.UnrecoverableProviderException;
import com.cloudera.director.spi.v2.model.exception.ValidationException;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Contains functions to convert V1 exception objects to V2 exception objects.
 */
public class ExceptionsAdapter {

  public static class ConvertedAbstractPluginException extends AbstractPluginException {
    public ConvertedAbstractPluginException(String message, Throwable cause, PluginExceptionDetails details) {
      super(message, cause, details);
    }
  }

  public static AbstractPluginException fromV1(
      final com.cloudera.director.spi.v1.model.exception.AbstractPluginException abstractPluginException) {

    if (com.cloudera.director.spi.v1.model.exception.TransientProviderException.class.isAssignableFrom(
        abstractPluginException.getClass())) {
      return fromV1((com.cloudera.director.spi.v1.model.exception.TransientProviderException) abstractPluginException);
    }

    if (com.cloudera.director.spi.v1.model.exception.UnrecoverableProviderException.class.isAssignableFrom(
        abstractPluginException.getClass())) {
      return fromV1(
          (com.cloudera.director.spi.v1.model.exception.UnrecoverableProviderException) abstractPluginException
      );
    }

    return new ConvertedAbstractPluginException(
        abstractPluginException.getMessage(),
        abstractPluginException.getCause(),
        fromV1(abstractPluginException.getDetails()));
  }

  private static TransientProviderException fromV1(
      com.cloudera.director.spi.v1.model.exception.TransientProviderException transientEx) {
    return new TransientProviderException(
        transientEx.getMessage(),
        transientEx.getCause(),
        fromV1(transientEx.getDetails())
    );
  }

  private static UnrecoverableProviderException fromV1(
      com.cloudera.director.spi.v1.model.exception.UnrecoverableProviderException unrecoverableEx) {

    if (com.cloudera.director.spi.v1.model.exception.ValidationException.class.isAssignableFrom(
        unrecoverableEx.getClass())) {
      return fromV1((com.cloudera.director.spi.v1.model.exception.ValidationException) unrecoverableEx);
    }

    if (com.cloudera.director.spi.v1.model.exception.InvalidCredentialsException.class.isAssignableFrom(
        unrecoverableEx.getClass())) {
      return fromV1((com.cloudera.director.spi.v1.model.exception.InvalidCredentialsException) unrecoverableEx);
    }

    return new UnrecoverableProviderException(
        unrecoverableEx.getMessage(),
        unrecoverableEx.getCause(),
        fromV1(unrecoverableEx.getDetails())
    );
  }

  private static ValidationException fromV1(
      com.cloudera.director.spi.v1.model.exception.ValidationException validationEx) {
    return new ValidationException(
        validationEx.getMessage(),
        validationEx.getCause(),
        fromV1(validationEx.getDetails())
    );
  }

  private static InvalidCredentialsException fromV1(
      com.cloudera.director.spi.v1.model.exception.InvalidCredentialsException invalidCredentialsEx) {
    return new InvalidCredentialsException(
        invalidCredentialsEx.getMessage(),
        invalidCredentialsEx.getCause(),
        fromV1(invalidCredentialsEx.getDetails())
    );
  }

  private static PluginExceptionDetails fromV1(
      com.cloudera.director.spi.v1.model.exception.PluginExceptionDetails details) {
    Map<String, SortedSet<com.cloudera.director.spi.v1.model.exception.PluginExceptionCondition>>
        conditionsByKey = details.getConditionsByKey();
    Map<String, SortedSet<PluginExceptionCondition>> converted = fromV1(conditionsByKey);
    return new PluginExceptionDetails(converted);
  }


  private static Map<String, SortedSet<PluginExceptionCondition>> fromV1(
      Map<String, SortedSet<com.cloudera.director.spi.v1.model.exception.PluginExceptionCondition>> conditionsByKey) {
    Map<String, SortedSet<PluginExceptionCondition>> converted =
        new HashMap<String, SortedSet<PluginExceptionCondition>>();

    for (Map.Entry<String, SortedSet<com.cloudera.director.spi.v1.model.exception.PluginExceptionCondition>> entry :
        conditionsByKey.entrySet()) {
      String key = entry.getKey();
      SortedSet<com.cloudera.director.spi.v1.model.exception.PluginExceptionCondition> value = entry.getValue();
      converted.put(key, fromV1(value));
    }
    return converted;
  }

  private static SortedSet<PluginExceptionCondition> fromV1(
      SortedSet<com.cloudera.director.spi.v1.model.exception.PluginExceptionCondition> exceptionConditions) {
    SortedSet<PluginExceptionCondition> converted = new TreeSet<PluginExceptionCondition>();
    for (com.cloudera.director.spi.v1.model.exception.PluginExceptionCondition condition : exceptionConditions) {
      converted.add(fromV1(condition));
    }
    return converted;
  }

  private static PluginExceptionCondition fromV1(
      com.cloudera.director.spi.v1.model.exception.PluginExceptionCondition condition) {
    return new PluginExceptionCondition(
        EnumAdapter.fromV1(condition.getType()),
        condition.getMessage()
    );
  }
}
