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

/**
 * Base class for resource provider metadata implementations.
 */
public class BaseResourceProviderMetadata extends BaseProviderMetadata implements ResourceProviderMetadata {

  /**
   * Creates an abstract resource provider metadata with the specified parameters.
   *
   * @param id                              the unique identifier of the provider within its scope
   * @param name                            the name of the provider, suitable for display in a drop-down menu of peer providers
   *                                        within the same scope
   * @param description                     the human-readable description of the provider
   * @param providerType                    the class or interface representing the type of provider
   * @param providerConfigurationProperties the list of configuration properties that can be given to configure the provider
   * @param credentialsProviderMetadata     the credentials provider metadata
   */
  public BaseResourceProviderMetadata(String id, String name, String description, Class<?> providerType,
      List<ConfigurationProperty> providerConfigurationProperties, CredentialsProviderMetadata credentialsProviderMetadata) {
    super(id, name, description, providerType, providerConfigurationProperties, credentialsProviderMetadata);
  }
}
