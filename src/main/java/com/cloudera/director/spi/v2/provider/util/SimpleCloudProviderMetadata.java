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

import static com.cloudera.director.spi.v2.util.Preconditions.checkNotNull;

import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.provider.CloudProviderMetadata;
import com.cloudera.director.spi.v2.provider.CredentialsProviderMetadata;
import com.cloudera.director.spi.v2.provider.ResourceProviderMetadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A basic cloud provider metadata implementation.
 */
public class SimpleCloudProviderMetadata
    extends AbstractProviderMetadata implements CloudProviderMetadata {

  /**
   * The metadata about the supported resource providers.
   */
  private final List<ResourceProviderMetadata> resourceProviderMetadata;

  /**
   * The metadata needed to authenticate.
   */
  private final CredentialsProviderMetadata credentialsProviderMetadata;

  /**
   * Creates an abstract cloud provider metadata with the specified parameters.
   *
   * @param id                          the unique identifier of the provider within its scope
   * @param name                        the name of the provider, suitable for display in a
   *                                    drop-down menu of peer providers
   *                                    within the same scope
   * @param description                 the human-readable description of the provider
   * @param configurationProperties     the list of configuration properties that can be given to
   *                                    configure the provider
   * @param credentialsProviderMetadata the credentials provider metadata
   * @param resourceProviderMetadata    the metadata about the supported resource providers
   */
  public SimpleCloudProviderMetadata(String id, String name, String description,
      List<ConfigurationProperty> configurationProperties,
      CredentialsProviderMetadata credentialsProviderMetadata,
      List<ResourceProviderMetadata> resourceProviderMetadata) {
    super(id, name, description, configurationProperties);

    if (resourceProviderMetadata == null) {
      throw new NullPointerException("resourceProviderMetadata is null");
    }
    if (resourceProviderMetadata.isEmpty()) {
      throw new IllegalArgumentException("No supported resource providers.");
    }
    this.resourceProviderMetadata = Collections.unmodifiableList(
        new ArrayList<ResourceProviderMetadata>(resourceProviderMetadata));

    this.credentialsProviderMetadata =
        checkNotNull(credentialsProviderMetadata, "credentialsProviderMetadata is null");
  }

  @Override
  public List<ResourceProviderMetadata> getResourceProviderMetadata() {
    return resourceProviderMetadata;
  }

  @Override
  public ResourceProviderMetadata getResourceProviderMetadata(String resourceProviderId) {
    for (ResourceProviderMetadata candidate : getResourceProviderMetadata()) {
      if (candidate.getId().equals(resourceProviderId)) {
        return candidate;
      }
    }
    throw new NoSuchElementException("No metadata for provider: " + resourceProviderId);
  }

  @Override
  public CredentialsProviderMetadata getCredentialsProviderMetadata() {
    return credentialsProviderMetadata;
  }
}
