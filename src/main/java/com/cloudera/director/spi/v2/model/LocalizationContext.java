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

import java.util.Locale;

/**
 * Provides context for localization.
 */
public interface LocalizationContext {

  /**
   * Localization context factory.
   */
  interface Factory {

    /**
     * Creates a root localization context for the specified locale.
     *
     * @param locale the locale
     * @return a root localization context for the specified locale
     */
    LocalizationContext createRootLocalizationContext(Locale locale);
  }

  /**
   * Returns the locale.
   *
   * @return the locale
   */
  Locale getLocale();

  /**
   * Returns the key prefix of the context, which can be used for property namespacing.
   *
   * @return the key prefix of the context
   */
  String getKeyPrefix();

  /**
   * Returns a localized value for the specified localization key suffix components,
   * or the specified default value if a localized value cannot be determined.
   *
   * @param defaultValue  the default value to return if a localized value cannot be determined
   * @param keyComponents the localization key suffix components
   * @return a localized value for the specified localization key suffix components,
   * or the specified default value if a localized value cannot be determined
   */
  String localize(String defaultValue, String... keyComponents);
}
