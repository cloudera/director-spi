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

package com.cloudera.director.spi.v2.provider.util;

import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.util.ChildLocalizationContext;
import com.cloudera.director.spi.v2.provider.CredentialsProviderMetadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract base class for credentials provider metadata.
 */
public class SimpleCredentialsProviderMetadata implements CredentialsProviderMetadata {

  /**
   * The list of configuration properties that can be given to configure a credentials provider
   * for the provider.
   */
  private final List<ConfigurationProperty> credentialsConfigurationProperties;

  /**
   * Creates a credentials provider metadata with the specified parameters.
   *
   * @param configurationProperties the list of configuration properties that can
   *                                be given to configure the credentials provider
   */
  public SimpleCredentialsProviderMetadata(List<ConfigurationProperty> configurationProperties) {
    if (configurationProperties == null) {
      throw new NullPointerException("credentialsConfigurationProperties is null");
    }
    this.credentialsConfigurationProperties = Collections.unmodifiableList(
        new ArrayList<ConfigurationProperty>(configurationProperties));
  }

  @Override
  public LocalizationContext getLocalizationContext(LocalizationContext cloudLocalizationContext) {
    return new ChildLocalizationContext(cloudLocalizationContext, "credentials");
  }

  @Override
  public List<ConfigurationProperty> getCredentialsConfigurationProperties() {
    return credentialsConfigurationProperties;
  }
}
