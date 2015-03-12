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

package com.cloudera.director.spi.v1.provider;

import com.cloudera.director.spi.v1.model.ConfigurationProperty;

import java.util.List;
import java.util.Locale;

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
   * Returns the name of the provider, suitable for display in a drop-down menu of peer providers
   * within the same scope.
   *
   * @param locale the locale
   * @return the name of the provider
   */
  String getName(Locale locale);

  /**
   * Returns a human-readable description of the provider.
   *
   * @param locale the locale
   * @return a human-readable description of the provider
   */
  String getDescription(Locale locale);

  /**
   * Returns the list of configuration properties that can be given to configure the provider.
   *
   * @return the list of configuration properties that can be given to configure the provider
   */
  List<ConfigurationProperty> getProviderConfigurationProperties();

}
