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

import java.io.Serializable;

/**
 * A property.
 *
 * @param <W> the widget type supported by the property
 */
public interface Property<W extends Enum<W>> extends Serializable {

  /**
   * Represents the type of a property.
   */
  enum Type {

    /**
     * Boolean-valued property type.
     */
    BOOLEAN,

    /**
     * Integer-valued property type.
     */
    INTEGER,

    /**
     * Double-valued property type.
     */
    DOUBLE,

    /**
     * String-valued property type.
     */
    STRING
  }

  /**
   * Returns the type of the display property.
   *
   * @return the type of the display property
   */
  Type getType();

  /**
   * Returns the widget used to display values of the property.
   *
   * @return the widget used to display values of the property
   */
  W getWidget();

  /**
   * Returns the localized human-readable name of the display property for labels
   * and error messages.
   *
   * @param localizationContext the localization context
   * @return the localized human-readable name of the display property for labels
   * and error messages
   */
  String getName(LocalizationContext localizationContext);

  /**
   * Returns the localized human-readable description of the display property.
   *
   * @param localizationContext the localization context
   * @return the localized human-readable description of the display property
   */
  String getDescription(LocalizationContext localizationContext);

  /**
   * Returns whether the display property contains sensitive information.
   *
   * @return whether the display property contains sensitive information
   */
  boolean isSensitive();

  /**
   * Returns whether the display property should be hidden from the user interface.
   *
   * @return whether the display property should be hidden from the user interface
   */
  boolean isHidden();
}
