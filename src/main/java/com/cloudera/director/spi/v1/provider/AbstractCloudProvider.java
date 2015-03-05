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

import com.cloudera.director.spi.v1.model.Configured;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract base class for cloud provider implementations.
 */
public abstract class AbstractCloudProvider implements CloudProvider {

  /**
   * The cloud provider metadata.
   */
  private final CloudProviderMetadata cloudProviderMetadata;

  /**
   * The initialized resource providers.
   */
  private final Map<String, ResourceProvider> providersById = new HashMap<String, ResourceProvider>();

  /**
   * Creates an abstract cloud provider with the specified parameters.
   *
   * @param cloudProviderMetadata the metadata about the supported resource providers
   */
  protected AbstractCloudProvider(CloudProviderMetadata cloudProviderMetadata) {
    if (cloudProviderMetadata == null) {
      throw new NullPointerException("cloudProviderMetadata is null");
    }
    this.cloudProviderMetadata = cloudProviderMetadata;
  }

  @Override
  public CloudProviderMetadata getProviderMetadata() {
    return cloudProviderMetadata;
  }

  @Override
  public synchronized ResourceProvider getResourceProvider(String resourceProviderId, Configured configuration) {
    ResourceProvider resourceProvider = getInitializedResourceProvider(resourceProviderId);
    if (resourceProvider == null) {
      ResourceProviderMetadata resourceProviderMetadata = getProviderMetadata().getResourceProviderMetadata(resourceProviderId);
      resourceProvider = createResourceProvider(resourceProviderMetadata, configuration);
      providersById.put(resourceProviderId, resourceProvider);
    }
    return resourceProvider;
  }

  /**
   * Returns the initialized resource provider with the specified ID, or <code>null</code>
   * if the provider has not been initialized.
   *
   * @param resourceProviderId the resource provider ID
   * @return the initialized resource provider with the specified ID, or <code>null</code>
   * if the provider has not been initialized
   */
  protected ResourceProvider getInitializedResourceProvider(String resourceProviderId) {
    if (resourceProviderId == null) {
      throw new NullPointerException("resourceProviderId is null");
    }
    return providersById.get(resourceProviderId);
  }

  /**
   * Creates a resource provider based on the specified resource provider metadata and configuration.
   *
   * @param resourceProviderMetadata the resource provider metadata
   * @param configuration            the configuration
   * @return the resource provider
   */
  protected abstract ResourceProvider createResourceProvider(ResourceProviderMetadata resourceProviderMetadata, Configured configuration);
}
