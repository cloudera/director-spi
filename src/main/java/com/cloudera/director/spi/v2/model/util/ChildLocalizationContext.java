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

import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.util.Preconditions;

/**
 * A localization context with a namespace nested inside a parent localization context.
 */
public class ChildLocalizationContext extends AbstractLocalizationContext {

  /**
   * The parent localization context.
   */
  private final LocalizationContext parentContext;

  /**
   * The key component for the child context.
   */
  private final String keyComponent;

  /**
   * Creates a child localization context with the specified parameters.
   *
   * @param parentContext the parent localization context
   * @param keyComponent  the key component for the child context
   */
  public ChildLocalizationContext(LocalizationContext parentContext, String keyComponent) {
    super(Preconditions.checkNotNull(parentContext, "parentContext is null").getLocale(),
        buildKey(parentContext.getKeyPrefix(), keyComponent));
    this.parentContext = parentContext;
    this.keyComponent = keyComponent;
  }

  /**
   * Returns the parent localization context.
   *
   * @return the parent localization context
   */
  public LocalizationContext getParentContext() {
    return parentContext;
  }

  /**
   * Returns the key component for the child context.
   *
   * @return the key component for the child context
   */
  public String getKeyComponent() {
    return keyComponent;
  }

  @Override
  public String localize(String defaultValue, String... keyComponents) {
    int len = keyComponents.length;
    String result;
    if (len == 0) {
      result = defaultValue;
    } else {
      String[] fullKeyComponents = new String[len + 1];
      fullKeyComponents[0] = getKeyComponent();
      System.arraycopy(keyComponents, 0, fullKeyComponents, 1, len);
      result = getParentContext().localize(defaultValue, fullKeyComponents);
    }
    return result;
  }
}
