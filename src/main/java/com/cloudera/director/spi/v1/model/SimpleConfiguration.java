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

package com.cloudera.director.spi.v1.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple map-based configurable object implementation.
 */
public class SimpleConfiguration implements Configured {

  /**
   * The map of configuration values.
   */
  private final Map<String, String> configurationMap;

  /**
   * Creates a simple configuration with no configuration.
   */
  public SimpleConfiguration() {
    this.configurationMap = Collections.emptyMap();
  }

  /**
   * Creates a simple configuration with the specified configuration map.
   *
   * @param configurationMap the map of configuration values
   */
  public SimpleConfiguration(Map<String, String> configurationMap) {
    if (configurationMap == null) {
      throw new NullPointerException("configurationMap is null");
    }
    this.configurationMap = Collections.unmodifiableMap(new LinkedHashMap<String, String>(configurationMap));
  }

  @Override
  public Map<String, String> getConfigurationAsMap() {
    return configurationMap;
  }

  @Override
  public String getConfigurationPropertyValue(ConfigurationProperty configurationProperty) {
    String value;
    String configKey = configurationProperty.getConfigKey();
    if (configurationMap.containsKey(configKey)) {
      value = configurationMap.get(configKey);
    } else if (configurationProperty.isRequired()) {
      throw new IllegalArgumentException(configurationProperty.getMissingValueErrorMessage());
    } else {
      value = configurationProperty.getDefaultValue();
    }
    return value;
  }
}
