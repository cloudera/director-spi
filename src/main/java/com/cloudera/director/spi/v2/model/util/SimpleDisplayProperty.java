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

/**
 * Simple display property implementation.
 */
public class SimpleDisplayProperty extends AbstractDisplayProperty {

  /**
   * Creates a simple display property with the specified parameters.
   *
   * @param displayKey         the display key
   * @param type               the type of the display property
   * @param name               the default human-readable name of the display
   *                           property, for error messages and labels, used when a localized
   *                           name cannot be found, or {@code null} to use the display
   *                           key
   * @param widget             the widget used to display values of the property
   * @param defaultDescription the default human-readable description of the display
   *                           property, used when a localized description cannot be found
   * @param sensitive          whether the display property contains sensitive information
   * @param hidden             whether the display property should be hidden from the user
   *                           interface
   */
  protected SimpleDisplayProperty(String displayKey, Type type, String name, Widget widget,
      String defaultDescription, boolean sensitive, boolean hidden) {
    super(displayKey, type, name, widget, defaultDescription, sensitive, hidden);
  }
}
