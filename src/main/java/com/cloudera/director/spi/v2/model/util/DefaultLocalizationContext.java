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

import java.util.Locale;

/**
 * Localization context that does not do any actual localization.
 */
public class DefaultLocalizationContext extends AbstractLocalizationContext {

  /**
   * Default localization context factory implementation.
   */
  public static final Factory FACTORY = new Factory() {

    @Override
    public LocalizationContext createRootLocalizationContext(Locale locale) {
      return new DefaultLocalizationContext(locale, "");
    }

  };

  /**
   * Creates a default localization context with the specified parameters.
   *
   * @param locale    the locale
   * @param keyPrefix the key prefix of the context, which can be used for property namespacing
   */
  public DefaultLocalizationContext(Locale locale, String keyPrefix) {
    super(locale, keyPrefix);
  }

  @Override
  public String localize(String defaultValue, String... keySuffixComponents) {
    return defaultValue;
  }
}
