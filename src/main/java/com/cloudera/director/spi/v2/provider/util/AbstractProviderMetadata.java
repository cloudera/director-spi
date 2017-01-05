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

import static com.cloudera.director.spi.v2.model.CommonLocalizableAttribute.DESCRIPTION;
import static com.cloudera.director.spi.v2.model.CommonLocalizableAttribute.NAME;
import static com.cloudera.director.spi.v2.util.Preconditions.checkNotNull;

import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.ConfigurationValidator;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.util.ChildLocalizationContext;
import com.cloudera.director.spi.v2.model.util.DefaultConfigurationValidator;
import com.cloudera.director.spi.v2.provider.ProviderMetadata;

import java.util.Collections;
import java.util.List;

/**
 * Base class for provider metadata implementations.
 */
public abstract class AbstractProviderMetadata implements ProviderMetadata {

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
   * The list of configuration properties that can be given to configure the provider.
   */
  private final List<ConfigurationProperty> providerConfigurationProperties;

  /**
   * Creates an abstract provider metadata with the specified parameters.
   *
   * @param id                              the unique identifier of the provider within its scope
   * @param name                            the name of the provider, suitable for display in a
   *                                        drop-down menu of peer providers within the same scope
   * @param description                     the human-readable description of the provider
   * @param providerConfigurationProperties the list of configuration properties that can be given
   *                                        to configure the provider
   */
  public AbstractProviderMetadata(String id, String name, String description,
      List<ConfigurationProperty> providerConfigurationProperties) {
    this.id = checkNotNull(id, "is is null");
    this.name = checkNotNull(name, "name is null");
    this.description = checkNotNull(description, "description is null");
    this.providerConfigurationProperties = Collections.unmodifiableList(
        checkNotNull(providerConfigurationProperties, "providerConfigurationProperties is null"));
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public LocalizationContext getLocalizationContext(LocalizationContext parentContext) {
    return new ChildLocalizationContext(parentContext, id);
  }

  @Override
  public String getName(LocalizationContext localizationContext) {
    return (localizationContext == null) ? name : localizationContext.localize(
        name,
        NAME.getKeyComponent()
    );
  }

  @Override
  public String getDescription(LocalizationContext localizationContext) {
    return (localizationContext == null) ? description : localizationContext.localize(
        description,
        DESCRIPTION.getKeyComponent()
    );
  }

  @Override
  public List<ConfigurationProperty> getProviderConfigurationProperties() {
    return providerConfigurationProperties;
  }

  @Override
  public ConfigurationValidator getProviderConfigurationValidator() {
    return new DefaultConfigurationValidator(getProviderConfigurationProperties());
  }
}
