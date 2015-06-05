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

import static com.cloudera.director.spi.v1.model.Property.Type;

import com.cloudera.director.spi.v1.model.Property;

/**
 * Abstract base class for property builder implementations.
 */
public abstract class AbstractPropertyBuilder
    <W extends Enum<W>, P extends Property<W>, B extends AbstractPropertyBuilder<W, P, B>> {

  /**
   * The property key.
   */
  private String key;

  /**
   * The type of the configuration property.
   */
  private Type type;

  /**
   * The name of the configuration property.
   */
  private String name;

  /**
   * Returns the widget used to interact with values of the configuration property.
   */
  private W widget;

  /**
   * The default human-readable description of the configuration property, used when a
   * localized description cannot be found.
   */
  private String defaultDescription;

  /**
   * Whether the configuration property contains sensitive information.
   */
  private boolean sensitive;

  /**
   * Whether the configuration property should be hidden from the user interface.
   */
  private boolean hidden;

  /**
   * Returns the property key.
   *
   * @return the property key
   */
  protected String getKey() {
    return key;
  }

  /**
   * Sets the property key.
   *
   * @param key the property key
   * @return the builder
   */
  protected B key(String key) {
    this.key = key;
    return getThis();
  }

  /**
   * Returns the type of the property.
   *
   * @return the type of the property
   */
  public Type getType() {
    return type;
  }

  /**
   * Sets the type of the property.
   *
   * @param type the type of the property
   * @return the builder
   */
  public B type(Type type) {
    this.type = type;
    return getThis();
  }

  /**
   * Returns the default human-readable name of the property, for error messages
   * and labels, used when a localized name cannot be found.
   *
   * @return the default human-readable name of the property, for error messages
   * and labels, used when a localized name cannot be found
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the default human-readable name of the property, for error messages
   * and labels, used when a localized name cannot be found.
   *
   * @param name the default human-readable name of the property, for error messages
   *             and labels, used when a localized name cannot be found
   */
  public B name(String name) {
    this.name = name;
    return getThis();
  }

  /**
   * Returns the widget used to interact with the value of the property.
   *
   * @return the widget used to interact with the value of the property
   */
  public W getWidget() {
    return widget;
  }

  /**
   * Sets the widget used to interact with the value of the property.
   *
   * @param widget the widget used to interact with the value of the property
   * @return the builder
   */
  public B widget(W widget) {
    this.widget = widget;
    return getThis();
  }

  /**
   * Returns the default human-readable description of the property, used when a
   * localized description cannot be found.
   *
   * @return the default human-readable description of the property, used when a
   * localized description cannot be found
   */
  public String getDefaultDescription() {
    return defaultDescription;
  }

  /**
   * Sets the default human-readable description of the property, used when a
   * localized description cannot be found.
   *
   * @param defaultDescription the default human-readable description of the property,
   *                           used when a localized description cannot be found
   * @return the builder
   */
  public B defaultDescription(String defaultDescription) {
    this.defaultDescription = defaultDescription;
    return getThis();
  }

  /**
   * Returns whether the property contains sensitive information.
   *
   * @return whether the property contains sensitive information
   */
  public boolean isSensitive() {
    return sensitive;
  }

  /**
   * Sets whether the property contains sensitive information.
   *
   * @param sensitive whether the property contains sensitive information
   * @return the builder
   */
  public B sensitive(boolean sensitive) {
    this.sensitive = sensitive;
    return getThis();
  }

  /**
   * Returns whether the property should be hidden from the user interface.
   *
   * @return whether the property should be hidden from the user interface
   */
  public boolean isHidden() {
    return hidden;
  }

  /**
   * Sets whether the property should be hidden from the user interface.
   *
   * @param hidden whether the property should be hidden from the user interface
   * @return the builder
   */
  public B hidden(boolean hidden) {
    this.hidden = hidden;
    return getThis();
  }

  /**
   * Returns the builder, as an instance of the parameterized builder type.
   *
   * @return the builder, as an instance of the parameterized builder type
   */
  public abstract B getThis();

  /**
   * Builds and returns a property.
   *
   * @return the property
   */
  public abstract P build();
}
