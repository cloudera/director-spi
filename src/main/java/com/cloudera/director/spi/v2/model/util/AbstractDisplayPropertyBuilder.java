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

import static com.cloudera.director.spi.v2.model.DisplayProperty.Widget;

import com.cloudera.director.spi.v2.model.DisplayProperty;

/**
 * Abstract base class for display property builder implementations.
 */
public abstract class AbstractDisplayPropertyBuilder
    <P extends DisplayProperty, B extends AbstractDisplayPropertyBuilder<P, B>>
    extends AbstractPropertyBuilder<Widget, P, B> {

  /**
   * Returns the display key.
   *
   * @return the display key
   */
  public String getDisplayKey() {
    return getKey();
  }

  /**
   * Sets the display key.
   *
   * @param displayKey the display key
   * @return the builder
   */
  public B displayKey(String displayKey) {
    return key(displayKey);
  }
}
