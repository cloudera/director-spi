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

package com.cloudera.director.spi.v2.model;

import com.cloudera.director.spi.v2.model.exception.PluginExceptionConditionAccumulator;

/**
 * Validates a configuration.
 */
public interface ConfigurationValidator {

  /**
   * Validates the specified configuration using the specified localization context,
   * accumulating errors and warnings in the specified accumulator.
   *
   * @param name                the name of the object being validated
   * @param configuration       the configuration to be validated
   * @param accumulator         the exception condition accumulator
   * @param localizationContext the localization context
   */
  void validate(String name, Configured configuration,
      PluginExceptionConditionAccumulator accumulator, LocalizationContext localizationContext);
}
