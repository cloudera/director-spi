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

package com.cloudera.director.spi.v2.provider;

import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.LocalizationContext;

import java.util.List;

/**
 * Metadata about a credentials provider.
 */
public interface CredentialsProviderMetadata {

  /**
   * Returns a localization context for the provider with the specified
   * parent cloud localization context.
   *
   * @param cloudLocalizationContext the parent cloud localization context
   * @return a localization context for the provider with the specified
   * parent cloud localization context
   */
  LocalizationContext getLocalizationContext(LocalizationContext cloudLocalizationContext);

  /**
   * Returns the list of configuration properties that can be given to the provider to
   * configure credentials.
   *
   * @return the list of configuration properties that can be given to the provider to
   * configure credentials
   */
  List<ConfigurationProperty> getCredentialsConfigurationProperties();
}
