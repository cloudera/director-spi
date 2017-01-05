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

package com.cloudera.director.spi.v2.model;

/**
 * A display property.
 */
public interface DisplayProperty extends Property<DisplayProperty.Widget> {

  /**
   * Represents the widget used to display a property.
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
     * Text area widget.
     */
    TEXTAREA,

    /**
     * File download widget.
     */
    FILE,

    /**
     * Multiple display widget for a fixed set of values.
     */
    MULTI
  }

  /**
   * Returns the display key.
   *
   * @return the display key
   */
  String getDisplayKey();
}
