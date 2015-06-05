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

import java.util.List;

/**
 * Simple configuration property implementation.
 */
public class SimpleConfigurationProperty extends AbstractConfigurationProperty {

  /**
   * Creates a simple configuration property with the specified parameters.
   *
   * @param configKey           the configuration key
   * @param type                the type of the configuration property
   * @param name                the default human-readable name of the configuration
   *                            property, for error messages and labels, used when a localized
   *                            name cannot be found, or {@code null} to use the configuration
   *                            key
   * @param required            whether the configuration property is required
   * @param widget              the widget used to display and edit values of the configuration
   *                            property
   * @param defaultValue        the default value of the configuration property
   * @param defaultDescription  the default human-readable description of the configuration
   *                            property, used when a localized description cannot be found
   * @param defaultErrorMessage the default human-readable error message for when a required
   *                            configuration property is missing, used when a localized error
   *                            message cannot be found
   * @param validValues         the list of valid values for this configuration property
   * @param sensitive           whether the configuration property contains sensitive information
   * @param hidden              whether the configuration property should be hidden from the user
   *                            interface
   */
  protected SimpleConfigurationProperty(String configKey, Type type, String name, boolean required,
      Widget widget, String defaultValue, String defaultDescription, String defaultErrorMessage,
      List<String> validValues, boolean sensitive, boolean hidden) {
    super(configKey, type, name, required, widget, defaultValue, defaultDescription,
        defaultErrorMessage, validValues, sensitive, hidden);
  }
}
