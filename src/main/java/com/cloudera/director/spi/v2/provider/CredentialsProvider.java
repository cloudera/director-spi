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

import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.LocalizationContext;

/**
 * Represents a provider of cloud credentials.
 *
 * @param <T> the type of credentials provided
 */
public interface CredentialsProvider<T> {

  /**
   * Returns the credentials provider metadata.
   *
   * @return the credentials provider metadata
   */
  CredentialsProviderMetadata getMetadata();

  /**
   * Creates credentials based on the specified configuration.
   *
   * @param configuration       the configuration
   * @param localizationContext the localization context
   * @return the credentials
   */
  T createCredentials(Configured configuration, LocalizationContext localizationContext);
}
