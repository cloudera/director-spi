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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract base class for credentials provider metadata.
 */
public class AbstractCredentialsProviderMetadata implements CredentialsProviderMetadata {
  /**
   * The list of configuration properties that can be given to configure a credentials provider
   * for the provider.
   */
  private final List<ConfigurationProperty> credentialsConfigurationProperties;

  /**
   * Creates an abstract credentials provider metadata with the specified parameters.
   *
   * @param credentialsConfigurationProperties the list of configuration properties that can be given to configure the credentials provider
   */
  public AbstractCredentialsProviderMetadata(List<ConfigurationProperty> credentialsConfigurationProperties) {
    if (credentialsConfigurationProperties == null) {
      throw new NullPointerException("credentialsConfigurationProperties is null");
    }
    this.credentialsConfigurationProperties = Collections.unmodifiableList(new ArrayList<ConfigurationProperty>(credentialsConfigurationProperties));
  }

  @Override
  public List<ConfigurationProperty> getCredentialsConfigurationProperties() {
    return credentialsConfigurationProperties;
  }
}
