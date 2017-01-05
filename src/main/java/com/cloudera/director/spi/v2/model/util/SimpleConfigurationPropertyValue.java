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

import com.cloudera.director.spi.v2.model.ConfigurationPropertyValue;
import com.cloudera.director.spi.v2.util.Preconditions;

/**
 * Simple configuration property value implementation.
 */
public class SimpleConfigurationPropertyValue implements ConfigurationPropertyValue {

  /**
   * The internal value of the configuration property value.
   */
  private final String value;

  /**
   * The human-readable localized label for the configuration property value.
   */
  private final String label;

  /**
   * Creates a configuration property value with the specified parameters.
   *
   * @param value the internal value of the configuration property value
   * @param label the human-readable localized label for the configuration property value
   */
  public SimpleConfigurationPropertyValue(String value, String label) {
    this.value = Preconditions.checkNotNull(value, "value is null");
    this.label = Preconditions.checkNotNull(label, "label is null");
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public String getLabel() {
    return label;
  }
}
