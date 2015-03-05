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

import java.util.Map;

/**
 * Represents a configured object that can report its configuration parameter values.
 */
public interface Configured {

  /**
   * Returns the unmodifiable map of configuration parameter values.
   *
   * @return the unmodifiable map of configuration parameter values
   */
  Map<String, String> getConfigurationAsMap();

  /**
   * Returns the value of the specified configuration property, or the default value if the value is not present
   * and the configuration property is optional.
   *
   * @param configurationProperty the configuration property
   * @return the value of the specified configuration property, or the default value if the value is not present
   * and the configuration property is optional
   * @throws IllegalArgumentException if the specified configuration property is not present and required
   */
  String getConfigurationPropertyValue(ConfigurationProperty configurationProperty);
}
