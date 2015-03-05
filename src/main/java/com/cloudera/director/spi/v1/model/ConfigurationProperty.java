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

import java.util.Locale;

/**
 * A configuration property.
 */
public interface ConfigurationProperty {

  /**
   * The configuration key.
   *
   * @return the configuration key
   */
  String getConfigKey();

  /**
   * Whether the configuration property is required.
   *
   * @return whether the configuration property is required
   */
  boolean isRequired();

  /**
   * The default value of the configuration property.
   *
   * @return the default value of the configuration property
   */
  String getDefaultValue();

  /**
   * The human-readable name of the configuration key for error messages.
   *
   * @return the human-readable name of the configuration key for error messages
   */
  String getDescription(Locale locale);

  /**
   * The human-readable error message for when a required configuration property is missing.
   *
   * @return the human-readable error message for when a required configuration property is missing
   */
  String getMissingValueErrorMessage();

  /**
   * Whether the configuration property contains sensitive information.
   *
   * @return whether the configuration property contains sensitive information
   */
  boolean isSensitive();
}
