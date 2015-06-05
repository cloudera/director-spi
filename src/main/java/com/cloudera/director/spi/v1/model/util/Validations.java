// (c) Copyright 2015 Cloudera, Inc.
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

package com.cloudera.director.spi.v1.model.util;

import com.cloudera.director.spi.v1.model.ConfigurationPropertyToken;
import com.cloudera.director.spi.v1.model.LocalizationContext;
import com.cloudera.director.spi.v1.model.exception.PluginExceptionConditionAccumulator;

/**
 * Provides utilities for dealing with validation.
 */
public class Validations {

  /**
   * Adds an error to the specified accumulator.
   *
   * @param accumulator         the exception condition accumulator
   * @param propertyToken       the token representing the configuration property in error
   * @param localizationContext the localization context
   * @param msgKey              the optional key representing the specific condition message
   * @param defaultMsgFormat    the default format string to use if no message key is provided
   * @param args                the arguments for the condition message
   */
  public static void addError(PluginExceptionConditionAccumulator accumulator,
      ConfigurationPropertyToken propertyToken, LocalizationContext localizationContext,
      String msgKey, String defaultMsgFormat, Object... args) {
    String propertyConfigKey =
        (propertyToken == null) ? null : propertyToken.unwrap().getConfigKey();
    addError(accumulator, propertyConfigKey, localizationContext, msgKey, defaultMsgFormat, args);
  }

  /**
   * Adds an error to the specified accumulator.
   *
   * @param accumulator         the exception condition accumulator
   * @param propertyConfigKey   the configuration key of the configuration property in error
   * @param localizationContext the localization context
   * @param msgKey              the optional key representing the specific condition message
   * @param defaultMsgFormat    the default format string to use if no message key is provided
   * @param args                the arguments for the condition message
   */
  public static void addError(PluginExceptionConditionAccumulator accumulator,
      String propertyConfigKey, LocalizationContext localizationContext,
      String msgKey, String defaultMsgFormat, Object... args) {
    String[] keyComponents = (msgKey == null) ? new String[]{} : new String[]{msgKey};
    // TODO localization should support arguments
    accumulator.addError(propertyConfigKey,
        localizationContext.localize(String.format(defaultMsgFormat, args), keyComponents));
  }

  /**
   * Adds a warning to the specified accumulator.
   *
   * @param accumulator         the exception condition accumulator
   * @param propertyToken       the token representing the configuration property
   * @param localizationContext the localization context
   * @param msgKey              the optional key representing the specific condition message
   * @param defaultMsgFormat    the default format string to use if no message key is provided
   * @param args                the arguments for the condition message
   */
  public static void addWarning(PluginExceptionConditionAccumulator accumulator,
      ConfigurationPropertyToken propertyToken, LocalizationContext localizationContext,
      String msgKey, String defaultMsgFormat, Object... args) {
    String propertyConfigKey =
        (propertyToken == null) ? null : propertyToken.unwrap().getConfigKey();
    addWarning(accumulator, propertyConfigKey, localizationContext, msgKey, defaultMsgFormat, args);
  }

  /**
   * Adds a warning to the specified accumulator.
   *
   * @param accumulator         the exception condition accumulator
   * @param propertyConfigKey   the configuration key of the configuration property
   * @param localizationContext the localization context
   * @param msgKey              the optional key representing the specific condition message
   * @param defaultMsgFormat    the default format string to use if no message key is provided
   * @param args                the arguments for the condition message
   */
  public static void addWarning(PluginExceptionConditionAccumulator accumulator,
      String propertyConfigKey, LocalizationContext localizationContext,
      String msgKey, String defaultMsgFormat, Object... args) {
    String[] keyComponents = (msgKey == null) ? new String[]{} : new String[]{msgKey};
    // TODO localization should support arguments
    accumulator.addWarning(propertyConfigKey,
        localizationContext.localize(String.format(defaultMsgFormat, args), keyComponents));
  }

  /**
   * Private constructor to prevent instantiation.
   */
  private Validations() {
  }
}
