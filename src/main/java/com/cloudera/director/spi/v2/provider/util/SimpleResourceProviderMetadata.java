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
import com.cloudera.director.spi.v2.model.ConfigurationValidator;
import com.cloudera.director.spi.v2.model.DisplayProperty;
import com.cloudera.director.spi.v2.model.util.DefaultConfigurationValidator;
import com.cloudera.director.spi.v2.provider.ResourceProvider;
import com.cloudera.director.spi.v2.provider.ResourceProviderMetadata;

import java.util.Collections;
import java.util.List;

/**
 * Base class for resource provider metadata implementations.
 */
public class SimpleResourceProviderMetadata
    extends AbstractProviderMetadata implements ResourceProviderMetadata {

  private Class<? extends ResourceProvider<?, ?>> providerClass;

  private final List<ConfigurationProperty> resourceTemplateConfigurationProperties;

  private final List<DisplayProperty> resourceDisplayProperties;

  public static SimpleResourceProviderMetadataBuilder builder() {
    return new SimpleResourceProviderMetadataBuilder();
  }


  /**
   * Creates an abstract resource provider metadata with the specified parameters.
   *
   * @param id                                      the unique identifier of the provider within its
   *                                                scope
   * @param name                                    the name of the provider, suitable for display
   *                                                in a drop-down menu of peer providers within the
   *                                                same scope
   * @param description                             the human-readable description of the provider
   * @param providerClass                           the resource provider class
   * @param providerConfigurationProperties         the list of configuration properties that can be
   *                                                given to configure the provider
   * @param resourceTemplateConfigurationProperties the list of configuration properties that can be
   *                                                given to configure resources provided by the
   *                                                provider
   * @param resourceDisplayProperties               the list of display properties present on
   *                                                resources provided by the provider
   */
  public SimpleResourceProviderMetadata(String id, String name, String description,
      Class<? extends ResourceProvider<?, ?>> providerClass,
      List<ConfigurationProperty> providerConfigurationProperties,
      List<ConfigurationProperty> resourceTemplateConfigurationProperties,
      List<DisplayProperty> resourceDisplayProperties) {
    super(id, name, description, providerConfigurationProperties);

    this.providerClass = checkNotNull(providerClass, "providerClass is null");
    this.resourceTemplateConfigurationProperties = Collections.unmodifiableList(
        checkNotNull(resourceTemplateConfigurationProperties,
            "resourceTemplateConfigurationProperties is null"));
    this.resourceDisplayProperties = Collections.unmodifiableList(
        checkNotNull(resourceDisplayProperties,
            "resourceDisplayProperties is null"));
  }

  @Override
  public Class<? extends ResourceProvider<?, ?>> getProviderClass() {
    return providerClass;
  }

  @Override
  public List<ConfigurationProperty> getResourceTemplateConfigurationProperties() {
    return resourceTemplateConfigurationProperties;
  }

  @Override
  public List<DisplayProperty> getResourceDisplayProperties() {
    return resourceDisplayProperties;
  }

  @Override
  public ConfigurationValidator getResourceTemplateConfigurationValidator() {
    return new DefaultConfigurationValidator(getResourceTemplateConfigurationProperties());
  }
}
