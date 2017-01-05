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
 * Represents a well-known attribute that can be localized.
 */
public enum CommonLocalizableAttribute implements LocalizableAttribute {

  /**
   * Name attribute.
   */
  NAME("name"),

  /**
   * Description attribute.
   */
  DESCRIPTION("description");

  /**
   * The key component, used in building a localization key.
   */
  private final String keyComponent;

  /**
   * Creates a localizable attribute with the specified parameters.
   *
   * @param keyComponent the key component, used in building a localization key
   */
  private CommonLocalizableAttribute(String keyComponent) {
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
