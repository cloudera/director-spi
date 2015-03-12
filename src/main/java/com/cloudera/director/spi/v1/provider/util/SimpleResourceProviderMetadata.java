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

package com.cloudera.director.spi.v1.provider.util;

import static com.cloudera.director.spi.v1.util.Preconditions.checkNotNull;

import com.cloudera.director.spi.v1.model.ConfigurationProperty;
import com.cloudera.director.spi.v1.provider.ResourceProviderMetadata;

import java.util.Collections;
import java.util.List;

/**
 * Base class for resource provider metadata implementations.
 */
public class SimpleResourceProviderMetadata
    extends AbstractProviderMetadata implements ResourceProviderMetadata {

  private final List<ConfigurationProperty> resourceTemplateConfigurationProperties;

  public static SimpleResourceProviderMetadataBuilder builder() {
    return new SimpleResourceProviderMetadataBuilder();
  }


  /**
   * Creates an abstract resource provider metadata with the specified parameters.
   *
   * @param id                              the unique identifier of the provider within its scope
   * @param name                            the name of the provider, suitable for display in a drop-down menu of peer providers
   *                                        within the same scope
   * @param description                     the human-readable description of the provider
   * @param providerConfigurationProperties the list of configuration properties that can be given to configure the provider
   */
  public SimpleResourceProviderMetadata(String id, String name, String description,
      List<ConfigurationProperty> providerConfigurationProperties,
      List<ConfigurationProperty> resourceTemplateConfigurationProperties) {
    super(id, name, description, providerConfigurationProperties);

    this.resourceTemplateConfigurationProperties = Collections.unmodifiableList(
        checkNotNull(resourceTemplateConfigurationProperties,
            "resourceTemplateConfigurationProperties is null"));
  }

  @Override
  public List<ConfigurationProperty> getResourceTemplateConfigurationProperties() {
    return resourceTemplateConfigurationProperties;
  }
}
