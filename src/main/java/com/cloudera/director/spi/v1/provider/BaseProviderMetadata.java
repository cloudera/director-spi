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
import java.util.Locale;

/**
 * Base class for provider metadata implementations.
 */
public class BaseProviderMetadata implements ProviderMetadata {

  /**
   * The unique identifier of the provider within its scope.
   */
  private final String id;

  /**
   * The name of the provider, suitable for display in a drop-down menu of peer providers
   * within the same scope.
   */
  private final String name;

  /**
   * The human-readable description of the provider.
   */
  private final String description;

  /**
   * The class or interface representing the type of provider.
   */
  private final Class<?> providerType;

  /**
   * The list of configuration properties that can be given to configure the provider.
   */
  private final List<ConfigurationProperty> providerConfigurationProperties;

  /**
   * The credentials provider metadata.
   */
  private final CredentialsProviderMetadata credentialsProviderMetadata;

  /**
   * Creates an abstract provider metadata with the specified parameters.
   *
   * @param id                              the unique identifier of the provider within its scope
   * @param name                            the name of the provider, suitable for display in a drop-down menu of peer providers
   *                                        within the same scope
   * @param description                     the human-readable description of the provider
   * @param providerType                    the class or interface representing the type of provider
   * @param providerConfigurationProperties the list of configuration properties that can be given to configure the provider
   * @param credentialsProviderMetadata     the credentials provider metadata
   */
  public BaseProviderMetadata(String id, String name, String description, Class<?> providerType,
      List<ConfigurationProperty> providerConfigurationProperties, CredentialsProviderMetadata credentialsProviderMetadata) {
    if (id == null) {
      throw new NullPointerException("id is null");
    }
    this.id = id;
    if (name == null) {
      throw new NullPointerException("name is null");
    }
    this.name = name;
    if (description == null) {
      throw new NullPointerException("description is null");
    }
    this.description = description;
    if (providerType == null) {
      throw new NullPointerException("providerType is null");
    }
    this.providerType = providerType;
    if (providerConfigurationProperties == null) {
      throw new NullPointerException("providerConfigurationProperties is null");
    }
    this.providerConfigurationProperties = Collections.unmodifiableList(new ArrayList<ConfigurationProperty>(providerConfigurationProperties));
    if (credentialsProviderMetadata == null) {
      throw new NullPointerException("credentialsProviderMetadata is null");
    }
    this.credentialsProviderMetadata = credentialsProviderMetadata;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getName(Locale locale) {
    return name;
  }

  @Override
  // NOTE: This implementation does not do any actual localization, and simply returns the fixed description.
  // Plugin implementors may override to perform localization.
  public String getDescription(Locale locale) {
    return description;
  }

  @Override
  public Class<?> getProviderType() {
    return providerType;
  }

  @Override
  public List<ConfigurationProperty> getProviderConfigurationProperties() {
    return providerConfigurationProperties;
  }

  @Override
  public CredentialsProviderMetadata getCredentialsProviderMetadata() {
    return credentialsProviderMetadata;
  }
}
