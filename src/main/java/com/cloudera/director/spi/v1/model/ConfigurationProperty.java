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

import java.util.List;

/**
 * A configuration property.
 */
public interface ConfigurationProperty extends Property<ConfigurationProperty.Widget> {

  /**
   * Represents the widget used to display and edit a configuration property.
   */
  enum Widget {

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
    OPENLIST,

    /**
     * Multiple selection widget for a fixed set of values.
     */
    MULTI,

    /**
     * Multiple selection widget that supports typing additional values.
     */
    OPENMULTI
  }

  /**
   * Represents an attribute of a configuration property that can be localized.
   */
  enum ConfigurationPropertyLocalizableAttribute implements LocalizableAttribute {

    /**
     * Missing value error message attribute.
     */
    MISSING_VALUE_ERROR_MESSAGE("missingValueErrorMessage"),

    /**
     * Valid values message attribute.
     */
    VALID_VALUES("validValues");

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
   * Returns whether the configuration property is required.
   *
   * @return whether the configuration property is required
   */
  boolean isRequired();

  /**
   * Returns the default value of the configuration property.
   *
   * @return the default value of the configuration property
   */
  String getDefaultValue();

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
   * Returns a list of valid values for the configuration property with localized labels. Normally
   * applies to properties that use LIST, OPENLIST, MULTI, or OPENMULTI widgets, but can apply to
   * other widget types.
   *
   * @param localizationContext the localizationContext
   * @return the valid values for the configuration property
   */
  List<ConfigurationPropertyValue> getValidValues(LocalizationContext localizationContext);
}
