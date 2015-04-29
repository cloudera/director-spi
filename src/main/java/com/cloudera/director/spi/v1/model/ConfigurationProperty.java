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

/**
 * A configuration property.
 */
public interface ConfigurationProperty {

  /**
   * Represents the type of a configuration property.
   */
  enum Type {

    /**
     * Boolean-valued configuration property type.
     */
    BOOLEAN,

    /**
     * Integer-valued configuration property type.
     */
    INTEGER,

    /**
     * Double-valued configuration property type.
     */
    DOUBLE,

    /**
     * String-valued configuration property type.
     */
    STRING
  }

  /**
   * Represents the widget used to display and edit a configuration property.
   */
  public enum Widget {

    /**
     * Radio button widget.
     */
    RADIO,

    /**
     * Checkbox widget.
     */
    CHECKBOX,

    /**
     * Text box widget.
     */
    TEXT,

    /**
     * Password widget.
     */
    PASSWORD,

    /**
     * Numeric widget.
     */
    NUMBER,

    /**
     * Text area widget.
     */
    TEXTAREA,

    /**
     * File upload widget.
     */
    FILE,

    /**
     * List widget with fixed set of values.
     */
    LIST,

    /**
     * List widget that supports typing additional values.
     */
    OPENLIST
  }

  /**
   * Represents an attribute of a configuration property that can be localized.
   */
  enum ConfigurationPropertyLocalizableAttribute implements LocalizableAttribute {

    /**
     * Missing value error message attribute.
     */
    MISSING_VALUE_ERROR_MESSAGE("missingValueErrorMessage");

    /**
     * The key component, used in building a localization key.
     */
    private final String keyComponent;

    /**
     * Creates a localizable attribute with the specified parameters.
     *
     * @param keyComponent the key component, used in building a localization key
     */
    private ConfigurationPropertyLocalizableAttribute(String keyComponent) {
      this.keyComponent = keyComponent;
    }

    /**
     * Returns the key component, used in building a localization key.
     *
     * @return the key component, used in building a localization key
     */
    public String getKeyComponent() {
      return keyComponent;
    }
  }

  /**
   * Returns the configuration key.
   *
   * @return the configuration key
   */
  String getConfigKey();

  /**
   * Returns the type of the configuration property.
   *
   * @return the type of the configuration property
   */
  Type getType();

  /**
   * Returns whether the configuration property is required.
   *
   * @return whether the configuration property is required
   */
  boolean isRequired();

  /**
   * Returns the widget used to display and edit values of the configuration property.
   *
   * @return the widget used to display and edit values of the configuration property
   */
  Widget getWidget();

  /**
   * Returns the default value of the configuration property.
   *
   * @return the default value of the configuration property
   */
  String getDefaultValue();

  /**
   * Returns the localized human-readable name of the configuration key for labels
   * and error messages.
   *
   * @param localizationContext the localization context
   * @return the localized human-readable name of the configuration key for labels
   * and error messages
   */
  String getName(LocalizationContext localizationContext);

  /**
   * Returns the localized human-readable description of the configuration property.
   *
   * @param localizationContext the localization context
   * @return the localized human-readable description of the configuration property
   */
  String getDescription(LocalizationContext localizationContext);

  /**
   * Returns the localized human-readable error message for when a required configuration property
   * is missing.
   *
   * @param localizationContext the localization context
   * @return the localized human-readable error message for when a required configuration property
   * is missing
   */
  String getMissingValueErrorMessage(LocalizationContext localizationContext);

  /**
   * Returns whether the configuration property contains sensitive information.
   *
   * @return whether the configuration property contains sensitive information
   */
  boolean isSensitive();
}
