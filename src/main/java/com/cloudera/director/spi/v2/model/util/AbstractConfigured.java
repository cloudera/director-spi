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
import com.cloudera.director.spi.v2.model.ConfigurationPropertyToken;
import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.LocalizationContext;

import java.util.Map;

/**
 * Base configured object implementation.
 */
public abstract class AbstractConfigured implements Configured {

  /**
   * The configuration.
   */
  private final Configured configuration;

  /**
   * Creates a base configured object with the specified parameters.
   *
   * @param configuration source of configuration
   */
  public AbstractConfigured(Configured configuration) {
    this.configuration = checkNotNull(configuration, "configuration is null");
  }

  @Override
  public Map<String, String> getConfiguration(LocalizationContext localizationContext) {
    return configuration.getConfiguration(localizationContext);
  }

  @Override
  public String getConfigurationValue(ConfigurationPropertyToken token,
      LocalizationContext localizationContext) {
    return configuration.getConfigurationValue(token, localizationContext);
  }

  @Override
  public String getConfigurationValue(ConfigurationProperty property,
      LocalizationContext localizationContext) {
    return configuration.getConfigurationValue(property, localizationContext);
  }
}
