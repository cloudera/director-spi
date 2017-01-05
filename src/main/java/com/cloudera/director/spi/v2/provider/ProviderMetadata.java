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
import com.cloudera.director.spi.v2.model.ConfigurationValidator;
import com.cloudera.director.spi.v2.model.LocalizationContext;

import java.util.List;

/**
 * Metadata about a provider.
 */
public interface ProviderMetadata {

  /**
   * Returns the unique identifier of the provider within its scope.
   *
   * @return the unique identifier of the resource provider within its scope
   */
  String getId();

  /**
   * Returns a localization context for the provider with the specified parent context.
   *
   * @param parentContext the parent localization context
   * @return a localization context for the provider with the specified parent context
   */
  LocalizationContext getLocalizationContext(LocalizationContext parentContext);

  /**
   * Returns the localized name of the provider, suitable for display in a drop-down menu of peer
   * providers within the same scope.
   *
   * @param localizationContext the localization context
   * @return the name of the provider
   */
  String getName(LocalizationContext localizationContext);

  /**
   * Returns a localized human-readable description of the provider.
   *
   * @param localizationContext the localization context
   * @return a localized human-readable description of the provider
   */
  String getDescription(LocalizationContext localizationContext);

  /**
   * Returns the list of configuration properties that can be given to configure the provider.
   *
   * @return the list of configuration properties that can be given to configure the provider
   */
  List<ConfigurationProperty> getProviderConfigurationProperties();

  /**
   * Returns the configuration validator. The validator should make a best-faith effort
   * to identify configuration problems that will prevent successful provider initialization.
   *
   * @return the configuration validator
   */
  ConfigurationValidator getProviderConfigurationValidator();
}
