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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Abstract base class for launcher implementations.
 */
public abstract class AbstractLauncher implements Launcher {

  /**
   * The metadata about the supported cloud providers.
   */
  private final List<CloudProviderMetadata> cloudProviderMetadata;

  /**
   * The initialized cloud providers.
   */
  private final Map<String, CloudProvider> providersById = new HashMap<String, CloudProvider>();

  /**
   * Creates an abstract launcher with the specified parameters.
   *
   * @param cloudProviderMetadata the metadata about the supported cloud providers
   */
  protected AbstractLauncher(List<CloudProviderMetadata> cloudProviderMetadata) {
    if (cloudProviderMetadata == null) {
      throw new NullPointerException("cloudProviderMetadata is null");
    }
    if (cloudProviderMetadata.isEmpty()) {
      throw new IllegalArgumentException("No supported cloud providers.");
    }
    Set<String> providerIds = new HashSet<String>();
    for (CloudProviderMetadata providerMetadata : cloudProviderMetadata) {
      String id = providerMetadata.getId();
      if (providerIds.contains(id)) {
        throw new IllegalArgumentException("Duplicate cloud provider id: " + id);
      }
      providerIds.add(id);
    }
    this.cloudProviderMetadata = Collections.unmodifiableList(new ArrayList<CloudProviderMetadata>(cloudProviderMetadata));
  }

  @Override
  public void initialize(File configurationDirectory) {
  }

  @Override
  public List<CloudProviderMetadata> getCloudProviderMetadata() {
    return cloudProviderMetadata;
  }

  @Override
  public synchronized CloudProvider getCloudProvider(String cloudProviderId, Configured configuration) {
    CloudProvider cloudProvider = getInitializedCloudProvider(cloudProviderId);
    if (cloudProvider == null) {
      CloudProviderMetadata cloudProviderMetadata = getCloudProviderMetadata(cloudProviderId);
      cloudProvider = createCloudProvider(cloudProviderMetadata, configuration);
      providersById.put(cloudProviderId, cloudProvider);
    }
    return cloudProvider;
  }

  /**
   * Returns the cloud provider metadata for the first cloud provider in the launcher's metadata.
   *
   * @return the cloud provider metadata for the first cloud provider
   */
  protected CloudProviderMetadata getDefaultCloudProviderMetadata() {
    return getCloudProviderMetadata().iterator().next();
  }

  /**
   * Returns the cloud provider metadata for the specified cloud provider ID.
   *
   * @param cloudProviderId the cloud provider ID
   * @return the cloud provider metadata for the specified cloud provider ID
   */
  protected CloudProviderMetadata getCloudProviderMetadata(String cloudProviderId) {
    CloudProviderMetadata matchingCloudProviderMetadata = null;
    for (CloudProviderMetadata cloudProviderMetadata : getCloudProviderMetadata()) {
      if (cloudProviderMetadata.getId().equals(cloudProviderId)) {
        matchingCloudProviderMetadata = cloudProviderMetadata;
        break;
      }
    }
    if (matchingCloudProviderMetadata == null) {
      throw new IllegalArgumentException("No such cloud provider: " + cloudProviderId);
    }
    return matchingCloudProviderMetadata;
  }

  /**
   * Returns the initialized cloud provider with the specified ID, or <code>null</code>
   * if the provider has not been initialized.
   *
   * @param cloudProviderId the cloud provider ID
   * @return the initialized cloud provider with the specified ID, or <code>null</code>
   * if the provider has not been initialized
   */
  protected CloudProvider getInitializedCloudProvider(String cloudProviderId) {
    if (cloudProviderId == null) {
      throw new NullPointerException("cloudProviderId is null");
    }
    return providersById.get(cloudProviderId);
  }

  /**
   * Creates a cloud provider based on the specified cloud provider metadata and configuration.
   *
   * @param cloudProviderMetadata the cloud provider metadata
   * @param configuration         the configuration
   * @return the cloud provider
   */
  protected abstract CloudProvider createCloudProvider(CloudProviderMetadata cloudProviderMetadata, Configured configuration);
}
