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

import static com.cloudera.director.spi.v2.model.ConfigurationProperty.Widget;

import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.util.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for configuration property builder implementations.
 */
public abstract class AbstractConfigurationPropertyBuilder
    <P extends ConfigurationProperty, B extends AbstractConfigurationPropertyBuilder<P, B>>
    extends AbstractPropertyBuilder<Widget, P, B> {

  /**
   * Whether the configuration property is required.
   */
  private boolean required;

  /**
   * The default value of the configuration property.
   */
  private String defaultValue;

  /**
   * The human-readable error message for when a required configuration property is missing.
   */
  private String defaultErrorMessage;

  /**
   * The default list of valid values for this configuration property.
   */
  private List<String> validValues = new ArrayList<String>();

  /**
   * Returns the configuration key.
   *
   * @return the configuration key
   */
  public String getConfigKey() {
    return getKey();
  }

  /**
   * Sets the configuration key.
   *
   * @param configKey the configuration key
   * @return the builder
   */
  public B configKey(String configKey) {
    return key(configKey);
  }

  /**
   * Returns whether the configuration property is required.
   *
   * @return whether the configuration property is required
   */
  public boolean isRequired() {
    return required;
  }

  /**
   * Sets whether the configuration property is required.
   *
   * @param required whether the configuration property is required
   * @return the builder
   */
  public B required(boolean required) {
    this.required = required;
    return getThis();
  }

  /**
   * Returns the default value of the configuration property.
   *
   * @return the default value of the configuration property
   */
  public String getDefaultValue() {
    return defaultValue;
  }

  /**
   * Sets the default value of the configuration property.
   *
   * @param defaultValue the default value of the configuration property
   * @return the default value of the configuration property
   */
  public B defaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
    return getThis();
  }

  /**
   * Returns the default human-readable error message for when a required configuration property is
   * missing, used when a localized error message cannot be found.
   *
   * @return the default human-readable error message for when a required configuration property is
   * missing, used when a localized error message cannot be found
   */
  public String getDefaultErrorMessage() {
    return defaultErrorMessage;
  }

  /**
   * Sets the default human-readable error message for when a required configuration property is
   * missing, used when a localized error message cannot be found.
   *
   * @param defaultErrorMessage the default human-readable error message for when a required
   *                            configuration property is missing, used when a localized error
   *                            message cannot be found
   * @return the builder
   */
  public B defaultErrorMessage(String defaultErrorMessage) {
    this.defaultErrorMessage = defaultErrorMessage;
    return getThis();
  }

  /**
   * Returns the list of valid values for this configuration property.
   *
   * @return the list of valid values for this configuration property
   */
  public List<String> getValidValues() {
    return validValues;
  }

  /**
   * Adds one or more valid values to the list of valid values for this configuration property.
   *
   * @param validValues one or more valid values to add to this configuration property
   * @return the builder
   */
  public B addValidValues(String... validValues) {
    for (String validValue : validValues) {
      this.validValues.add(Preconditions.checkNotNull(validValue,
          "validValues contains a null element"));
    }
    return getThis();
  }

  /**
   * Sets the list of valid values for this configuration property.
   *
   * @param validValues the list of valid values for this configuration property
   * @return the builder
   */
  public B validValues(List<String> validValues) {
    this.validValues = new ArrayList<String>(Preconditions.checkNotNull(validValues,
        "validValues is null"));
    return getThis();
  }
}
