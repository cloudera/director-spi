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
 * Base class for cloud provider metadata implementations.
 */
public class BaseCloudProviderMetadata extends BaseProviderMetadata implements CloudProviderMetadata {

  /**
   * The metadata about the supported resource providers.
   */
  private final List<ResourceProviderMetadata> resourceProviderMetadata;

  /**
   * Creates an abstract cloud provider metadata with the specified parameters.
   *
   * @param id                              the unique identifier of the provider within its scope
   * @param name                            the name of the provider, suitable for display in a drop-down menu of peer providers
   *                                        within the same scope
   * @param description                     the human-readable description of the provider
   * @param providerConfigurationProperties the list of configuration properties that can be given to configure the provider
   * @param credentialsProviderMetadata     the credentials provider metadata
   * @param resourceProviderMetadata        the metadata about the supported resource providers
   */
  public BaseCloudProviderMetadata(String id, String name, String description,
      List<ConfigurationProperty> providerConfigurationProperties, CredentialsProviderMetadata credentialsProviderMetadata,
      List<ResourceProviderMetadata> resourceProviderMetadata) {
    super(id, name, description, CloudProvider.class, providerConfigurationProperties, credentialsProviderMetadata);
    if (resourceProviderMetadata == null) {
      throw new NullPointerException("resourceProviderMetadata is null");
    }
    if (resourceProviderMetadata.isEmpty()) {
      throw new IllegalStateException("No supported resource providers.");
    }
    this.resourceProviderMetadata = Collections.unmodifiableList(new ArrayList<ResourceProviderMetadata>(resourceProviderMetadata));
  }

  @Override
  public List<ResourceProviderMetadata> getResourceProviderMetadata() {
    return resourceProviderMetadata;
  }

  @Override
  public List<ResourceProviderMetadata> getResourceProviderMetadata(Class<?> resourceProviderType) {
    if (resourceProviderType == null) {
      throw new NullPointerException("resourceProviderType is null");
    }
    List<ResourceProviderMetadata> matchingResourceProviderMetadata = new ArrayList<ResourceProviderMetadata>();
    for (ResourceProviderMetadata resourceProviderMetadata : getResourceProviderMetadata()) {
      if (resourceProviderType.isAssignableFrom(resourceProviderMetadata.getProviderType())) {
        //noinspection unchecked
        matchingResourceProviderMetadata.add(resourceProviderMetadata);
      }
    }
    return matchingResourceProviderMetadata;
  }

  @Override
  public ResourceProviderMetadata getResourceProviderMetadata(String resourceProviderId) {
    ResourceProviderMetadata matchingResourceProviderMetadata = null;
    for (ResourceProviderMetadata resourceProviderMetadata : getResourceProviderMetadata()) {
      if (resourceProviderMetadata.getId().equals(resourceProviderId)) {
        matchingResourceProviderMetadata = resourceProviderMetadata;
      }
    }
    if (matchingResourceProviderMetadata == null) {
      throw new IllegalArgumentException("No such resource provider: " + resourceProviderId);
    }
    return matchingResourceProviderMetadata;
  }
}
