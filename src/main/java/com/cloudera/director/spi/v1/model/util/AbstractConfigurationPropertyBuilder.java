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

import static com.cloudera.director.spi.v1.model.ConfigurationProperty.Type;
import static com.cloudera.director.spi.v1.model.ConfigurationProperty.Widget;

import com.cloudera.director.spi.v1.model.ConfigurationProperty;

/**
 * Abstract base class for configuration property builder implementations.
 */
public abstract class AbstractConfigurationPropertyBuilder
    <P extends ConfigurationProperty, B extends AbstractConfigurationPropertyBuilder<P, B>> {

  /**
   * The configuration key.
   */
  private String configKey;

  /**
   * The type of the configuration property.
   */
  private Type type;

  /**
   * The name of the configuration property.
   */
  private String name;

  /**
   * Whether the configuration property is required.
   */
  private boolean required;

  /**
   * Returns the widget used to display and edit values of the configuration property.
   */
  private Widget widget;

  /**
   * The default value of the configuration property.
   */
  private String defaultValue;

  /**
   * The default human-readable description of the configuration property, used when a
   * localized description cannot be found.
   */
  private String defaultDescription;

  /**
   * The human-readable error message for when a required configuration property is missing.
   */
  private String defaultErrorMessage;

  /**
   * Whether the configuration property contains sensitive information.
   */
  private boolean sensitive;

  /**
   * Returns the configuration key.
   *
   * @return the configuration key
   */
  public String getConfigKey() {
    return configKey;
  }

  /**
   * Sets the configuration key.
   *
   * @param configKey the configuration key
   * @return the builder
   */
  public B configKey(String configKey) {
    this.configKey = configKey;
    return getThis();
  }

  /**
   * Returns the type of the configuration property.
   *
   * @return the type of the configuration property
   */
  public Type getType() {
    return type;
  }

  /**
   * Sets the type of the configuration property.
   *
   * @param type the type of the configuration property
   * @return the builder
   */
  public B type(Type type) {
    this.type = type;
    return getThis();
  }

  /**
   * Returns the default human-readable name of the configuration property, for error messages
   * and labels, used when a localized name cannot be found.
   *
   * @return the default human-readable name of the configuration property, for error messages
   * and labels, used when a localized name cannot be found
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the default human-readable name of the configuration property, for error messages
   * and labels, used when a localized name cannot be found.
   *
   * @param name the default human-readable name of the configuration property, for error messages
   *             and labels, used when a localized name cannot be found
   */
  public B name(String name) {
    this.name = name;
    return getThis();
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
   * Returns the widget used to display and edit the value of the configuration property.
   *
   * @return the widget used to display and edit the value of the configuration property
   */
  public Widget getWidget() {
    return widget;
  }

  /**
   * Sets the widget used to display and edit the value of the configuration property.
   *
   * @param widget the widget used to display and edit the value of the configuration property
   * @return the builder
   */
  public B widget(Widget widget) {
    this.widget = widget;
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
   * Sets the default human-readable description of the configuration property, used when a
   * localized description cannot be found.
   *
   * @param defaultDescription the default human-readable description of the configuration property,
   *                           used when a localized description cannot be found
   * @return the builder
   */
  public B defaultDescription(String defaultDescription) {
    this.defaultDescription = defaultDescription;
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
   * Returns whether the configuration property contains sensitive information.
   *
   * @return whether the configuration property contains sensitive information
   */
  public boolean isSensitive() {
    return sensitive;
  }

  /**
   * Sets whether the configuration property contains sensitive information.
   *
   * @param sensitive whether the configuration property contains sensitive information
   * @return the builder
   */
  public B sensitive(boolean sensitive) {
    this.sensitive = sensitive;
    return getThis();
  }

  /**
   * Returns the builder, as an instance of the parameterized builder type.
   *
   * @return the builder, as an instance of the parameterized builder type
   */
  public abstract B getThis();

  /**
   * Builds and returns a configuration property.
   *
   * @return the configuration property
   */
  public abstract P build();
}
