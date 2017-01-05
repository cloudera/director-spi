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

import static com.cloudera.director.spi.v2.model.CommonLocalizableAttribute.DESCRIPTION;
import static com.cloudera.director.spi.v2.model.CommonLocalizableAttribute.NAME;

import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.Property;
import com.cloudera.director.spi.v2.util.Preconditions;

/**
 * Abstract base class for property implementations.
 *
 * @param <W> the widget type supported by the property
 */
public abstract class AbstractProperty<W extends Enum<W>> implements Property<W> {

  /**
   * The key.
   */
  private final String key;

  /**
   * The name of the property.
   */
  private final String name;

  /**
   * The type of the property.
   */
  private final Type type;

  /**
   * Returns the widget used to interact with values of the property.
   */
  private final W widget;

  /**
   * The default human-readable description of the property, used when a
   * localized description cannot be found.
   */
  private final String defaultDescription;

  /**
   * Whether the property contains sensitive information.
   */
  private final boolean sensitive;

  /**
   * Whether the property should be hidden from the user interface.
   */
  private final boolean hidden;

  /**
   * Creates an abstract property with the specified parameters.
   *
   * @param key         the property key
   * @param type               the type of the property, or {@code null} to use the default type
   *                           of {@code STRING}
   * @param name               the default human-readable name of the property, for error messages
   *                           and labels, used when a localized name cannot be found, or
   *                           {@code null} to use the property key
   * @param widget             the widget used to interact with values of the property, or
   *                           {@code null} to use the default widget
   * @param defaultDescription the default human-readable description of the property, used when a
   *                           localized description cannot be found
   * @param sensitive          whether the property contains sensitive information
   * @param hidden             whether the property should be hidden from the user
   *                           interface
   */
  protected AbstractProperty(String key, Type type, String name, W widget,
      String defaultDescription, boolean sensitive, boolean hidden) {
    this.key = Preconditions.checkNotNull(key, "key is null");
    this.type = (type == null) ? Type.STRING : type;
    this.name = (name == null) ? key : name;
    this.widget = Preconditions.checkNotNull(widget, "widget is null");
    this.defaultDescription =
        Preconditions.checkNotNull(defaultDescription, "defaultDescription is null");
    this.sensitive = sensitive;
    this.hidden = hidden;
  }

  protected String getKey() {
    return key;
  }

  @Override
  public Type getType() {
    return type;
  }

  /**
   * Returns the name of the display property.
   *
   * @return the name of the display property
   */
  public String getName() {
    return name;
  }

  @Override
  public W getWidget() {
    return widget;
  }

  /**
   * Returns the default human-readable description of the display property, used when a
   * localized description cannot be found.
   *
   * @return the default human-readable description of the display property, used when a
   * localized description cannot be found
   */
  public String getDefaultDescription() {
    return defaultDescription;
  }

  @Override
  public boolean isSensitive() {
    return sensitive;
  }

  @Override
  public boolean isHidden() {
    return hidden;
  }

  @Override
  public String getName(LocalizationContext localizationContext) {
    String name = getName();
    return (localizationContext == null) ? name : localizationContext.localize(
        name,
        getKey(),
        NAME.getKeyComponent()
    );
  }

  @Override
  public String getDescription(LocalizationContext localizationContext) {
    String defaultDescription = getDefaultDescription();
    return (localizationContext == null) ? defaultDescription : localizationContext.localize(
        defaultDescription,
        getKey(),
        DESCRIPTION.getKeyComponent()
    );
  }
}
