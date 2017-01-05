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

import static com.cloudera.director.spi.v2.model.ConfigurationProperty.ConfigurationPropertyLocalizableAttribute.MISSING_VALUE_ERROR_MESSAGE;
import static com.cloudera.director.spi.v2.model.ConfigurationProperty.ConfigurationPropertyLocalizableAttribute.VALID_VALUES;
import static com.cloudera.director.spi.v2.model.ConfigurationProperty.Widget;

import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.ConfigurationPropertyValue;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.util.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract base class for configuration property implementations.
 */
public abstract class AbstractConfigurationProperty extends AbstractProperty<Widget>
    implements ConfigurationProperty {

  /**
   * Whether the configuration property is required.
   */
  private final boolean required;

  /**
   * The default value of the configuration property.
   */
  private final String defaultValue;

  /**
   * The default human-readable description of the configuration property, used when a
   * localized description cannot be found.
   */
  private final String defaultDescription;

  /**
   * The default human-readable error message for when a required configuration property is
   * missing, used when a localized error message cannot be found.
   */
  private final String defaultMissingValueErrorMessage;

  /**
   * The list of valid values for this property.
   */
  private final List<String> validValues;

  /**
   * Creates an abstract configuration property with the specified parameters.
   *
   * @param configKey           the configuration key
   * @param type                the type of the configuration property, or {@code null} to use
   *                            the default type of {@code STRING}
   * @param name                the default human-readable name of the configuration
   *                            property, for error messages and labels, used when a localized
   *                            name cannot be found, or {@code null} to use the configuration
   *                            key
   * @param required            whether the configuration property is required
   * @param widget              the widget used to display and edit values of the configuration
   *                            property, or {@code null} to use the default {@code TEXT} widget
   * @param defaultValue        the default value of the configuration property
   * @param defaultDescription  the default human-readable description of the configuration
   *                            property, used when a localized description cannot be found
   * @param defaultErrorMessage the default human-readable error message for when a required
   *                            configuration property is missing, used when a localized error
   *                            message cannot be found, or {@code null} to use a sensible default
   *                            message
   * @param validValues         the default list of valid values for this configuration property
   * @param sensitive           whether the configuration property contains sensitive information
   * @param hidden              whether the configuration property should be hidden from the user
   *                            interface
   */
  protected AbstractConfigurationProperty(String configKey, Type type, String name,
      boolean required, Widget widget, String defaultValue, String defaultDescription,
      String defaultErrorMessage, List<String> validValues, boolean sensitive, boolean hidden) {
    super(configKey, type, name, (widget == null) ? Widget.TEXT : widget, defaultDescription,
        sensitive, hidden);
    this.required = required;
    this.defaultValue = defaultValue;
    this.defaultDescription =
        Preconditions.checkNotNull(defaultDescription, "defaultDescription is null");
    this.defaultMissingValueErrorMessage = (defaultErrorMessage == null)
        ? "Configuration property not found: " + name
        : defaultErrorMessage;
    this.validValues = (validValues == null) ? Collections.<String>emptyList() :
        Collections.unmodifiableList(new ArrayList<String>(validValues));
  }

  @Override
  public String getConfigKey() {
    return getKey();
  }

  @Override
  public boolean isRequired() {
    return required;
  }

  @Override
  public String getDefaultValue() {
    return defaultValue;
  }

  /**
   * Returns the default human-readable description of the configuration property, used when a
   * localized description cannot be found.
   *
   * @return the default human-readable description of the configuration property, used when a
   * localized description cannot be found
   */
  public String getDefaultDescription() {
    return defaultDescription;
  }

  /**
   * Returns the default human-readable error message for when a required configuration property
   * is missing, used when a localized error message cannot be found.
   *
   * @return the default human-readable error message for when a required configuration property
   * is missing, used when a localized error message cannot be found
   */
  public String getDefaultMissingValueErrorMessage() {
    return defaultMissingValueErrorMessage;
  }

  /**
   * Returns the list of valid values for this configuration property.
   *
   * @return the list of valid values for this configuration property
   */
  public List<String> getValidValues() {
    return validValues;
  }

  @Override
  public String getMissingValueErrorMessage(LocalizationContext localizationContext) {
    String defaultErrorMessage = getDefaultMissingValueErrorMessage();
    return (localizationContext == null) ? defaultErrorMessage : localizationContext.localize(
        defaultErrorMessage,
        getConfigKey(),
        MISSING_VALUE_ERROR_MESSAGE.getKeyComponent()
    );
  }

  @Override
  public List<ConfigurationPropertyValue> getValidValues(LocalizationContext localizationContext) {
    List<String> validValues = getValidValues();

    List<ConfigurationPropertyValue> localizedValidValues =
        new ArrayList<ConfigurationPropertyValue>();

    for (String validValue : validValues) {
      localizedValidValues.add(new SimpleConfigurationPropertyValue(validValue,
          (localizationContext == null) ? validValue :
              localizationContext.localize(
                  validValue,
                  getConfigKey(),
                  VALID_VALUES.getKeyComponent(),
                  validValue)));
    }

    return localizedValidValues;
  }
}
