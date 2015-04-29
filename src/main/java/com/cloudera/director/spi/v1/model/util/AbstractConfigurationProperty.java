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

import static com.cloudera.director.spi.v1.model.CommonLocalizableAttribute.DESCRIPTION;
import static com.cloudera.director.spi.v1.model.CommonLocalizableAttribute.NAME;
import static com.cloudera.director.spi.v1.model.ConfigurationProperty.ConfigurationPropertyLocalizableAttribute.MISSING_VALUE_ERROR_MESSAGE;

import com.cloudera.director.spi.v1.model.ConfigurationProperty;
import com.cloudera.director.spi.v1.model.LocalizationContext;
import com.cloudera.director.spi.v1.util.Preconditions;

/**
 * Abstract base class for configuration property implementations.
 */
public abstract class AbstractConfigurationProperty implements ConfigurationProperty {

  /**
   * The configuration key.
   */
  private final String configKey;

  /**
   * The name of the configuration property.
   */
  private final String name;

  /**
   * The type of the configuration property.
   */
  private final Type type;

  /**
   * Whether the configuration property is required.
   */
  private final boolean required;

  /**
   * Returns the widget used to display and edit values of the configuration property.
   */
  private final Widget widget;

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
   * Whether the configuration property contains sensitive information.
   */
  private final boolean sensitive;

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
   * @param sensitive           whether the configuration property contains sensitive information
   */
  protected AbstractConfigurationProperty(String configKey, Type type, String name,
      boolean required, Widget widget, String defaultValue, String defaultDescription,
      String defaultErrorMessage, boolean sensitive) {
    this.configKey = Preconditions.checkNotNull(configKey, "configKey is null");
    this.type = (type == null) ? Type.STRING : type;
    this.name = (name == null) ? configKey : name;
    this.required = required;
    this.widget = (widget == null) ? Widget.TEXT : widget;
    this.defaultValue = defaultValue;
    this.defaultDescription =
        Preconditions.checkNotNull(defaultDescription, "defaultDescription is null");
    this.defaultMissingValueErrorMessage = (defaultErrorMessage == null)
        ? "Configuration property not found: " + this.name
        : defaultErrorMessage;
    this.sensitive = sensitive;
  }

  @Override
  public String getConfigKey() {
    return configKey;
  }

  @Override
  public Type getType() {
    return type;
  }

  /**
   * Returns the name of the configuration property.
   *
   * @return the name of the configuration property
   */
  public String getName() {
    return name;
  }

  @Override
  public boolean isRequired() {
    return required;
  }

  @Override
  public Widget getWidget() {
    return widget;
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

  @Override
  public boolean isSensitive() {
    return sensitive;
  }

  @Override
  public String getName(LocalizationContext localizationContext) {
    String name = getDefaultDescription();
    return (localizationContext == null) ? name : localizationContext.localize(
        name,
        getConfigKey(),
        NAME.getKeyComponent()
    );
  }

  @Override
  public String getDescription(LocalizationContext localizationContext) {
    String defaultDescription = getDefaultDescription();
    return (localizationContext == null) ? defaultDescription : localizationContext.localize(
        defaultDescription,
        getConfigKey(),
        DESCRIPTION.getKeyComponent()
    );
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
}
