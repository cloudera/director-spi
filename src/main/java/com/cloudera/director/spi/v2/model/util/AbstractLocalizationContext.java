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

import java.util.Locale;

/**
 * Abstract base class for localication context implementations.
 */
public abstract class AbstractLocalizationContext implements LocalizationContext {

  /**
   * Builds a localization key from the specified key components. Individual components
   * may not be {@code null}, but may be empty. The may not start or end with a period,
   * but may have embedded period. The resulting key will be formed by concatenating all
   * the nonempty components, separated by periods.
   *
   * @param keyComponents the key components
   * @return the derived localization key
   * @throws NullPointerException if any key component is {@code null}
   * @throws IllegalArgumentException if any key component starts or ends with a period
   */
  protected static String buildKey(String... keyComponents) {
    StringBuilder buf = new StringBuilder();
    for (String keyComponent : keyComponents) {
      Preconditions.checkNotNull(keyComponent, "keyComponent is null");
      if (!keyComponent.isEmpty()) {
        if (keyComponent.startsWith(".") || keyComponent.endsWith(".")) {
          throw new IllegalArgumentException("keyComponent: " + keyComponent
              + " must not start or end with a period");
        }
        if (buf.length() != 0) {
          buf.append('.');
        }
        buf.append(keyComponent);
      }
    }
    return buf.toString();
  }

  /**
   * The locale.
   */
  private final Locale locale;

  /**
   * The key prefix of the context, which can be used for property namespacing.
   */
  private final String keyPrefix;

  /**
   * Creates an abstract localization context with the specified parameters.
   *
   * @param locale    the locale
   * @param keyPrefix the key prefix of the context, which can be used for property namespacing
   * @throws NullPointerException if the locale or key prefix is {@code null}
   * @throws IllegalArgumentException if the key prefix starts or ends with a period
   */
  protected AbstractLocalizationContext(Locale locale, String keyPrefix) {
    this.locale = Preconditions.checkNotNull(locale, "locale is null");
    this.keyPrefix = buildKey(keyPrefix);
  }

  @Override
  public Locale getLocale() {
    return locale;
  }

  @Override
  public String getKeyPrefix() {
    return keyPrefix;
  }
}
