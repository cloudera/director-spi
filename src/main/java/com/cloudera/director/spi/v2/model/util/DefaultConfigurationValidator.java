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

package com.cloudera.director.spi.v2.model.util;

import static com.cloudera.director.spi.v2.util.Preconditions.checkNotNull;

import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.ConfigurationValidator;
import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.exception.PluginExceptionConditionAccumulator;

import java.util.List;

/**
 * Default configuration property validator implementation that validates a configuration
 * by attempting to retrieve each of a list of configuration properties.
 */
public class DefaultConfigurationValidator implements ConfigurationValidator {

  /**
   * The configuration properties to validate.
   */
  private final List<ConfigurationProperty> configurationProperties;

  /**
   * Creates a configuration validator over the specified configuration properties.
   *
   * @param configurationProperties the configuration properties to validate
   */
  public DefaultConfigurationValidator(List<ConfigurationProperty> configurationProperties) {
    this.configurationProperties =
        checkNotNull(configurationProperties, "configurationProperties is null");
  }

  @Override
  public void validate(String name, Configured configuration, PluginExceptionConditionAccumulator accumulator,
      LocalizationContext localizationContext) {
    for (ConfigurationProperty configurationProperty : configurationProperties) {
      try {
        configuration.getConfigurationValue(configurationProperty, localizationContext);
      } catch (Exception e) {
        accumulator.addError(configurationProperty.getConfigKey(), e.getMessage());
      }
    }
  }
}
