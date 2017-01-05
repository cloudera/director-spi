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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple map-based configurable object implementation.
 */
public class SimpleConfiguration implements Configured {

  /**
   * The map of configuration values.
   */
  private final Map<String, String> configuration;

  /**
   * Creates a simple configuration with no configuration.
   */
  public SimpleConfiguration() {
    this.configuration = Collections.emptyMap();
  }

  /**
   * Creates a simple configuration with the specified configuration map.
   *
   * @param configuration the map of configuration values
   */
  public SimpleConfiguration(Map<String, String> configuration) {
    this.configuration = Collections.unmodifiableMap(new HashMap<String, String>(
        checkNotNull(configuration, "configuration is null")));
  }

  @Override
  public Map<String, String> getConfiguration(LocalizationContext localizationContext) {
    return configuration;
  }

  @Override
  public String getConfigurationValue(ConfigurationPropertyToken token,
      LocalizationContext localizationContext) {
    return getConfigurationValue(token.unwrap(), localizationContext);
  }

  @Override
  public String getConfigurationValue(ConfigurationProperty property,
      LocalizationContext localizationContext) {
    String configKey = property.getConfigKey();
    if (configuration.containsKey(configKey)) {
      return configuration.get(configKey);
    } else if (property.isRequired()) {
      throw new IllegalArgumentException(property.getMissingValueErrorMessage(localizationContext));
    }

    return property.getDefaultValue();
  }
}
