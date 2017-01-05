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

import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.DisplayProperty;
import com.cloudera.director.spi.v2.provider.ResourceProvider;

import java.util.Collections;
import java.util.List;

public abstract class AbstractResourceProviderMetadataBuilder
    <B extends AbstractResourceProviderMetadataBuilder<B, T>,
        T extends SimpleResourceProviderMetadata> {

  protected String id;
  protected String name;
  protected String description;
  protected Class<? extends ResourceProvider<?, ?>> providerClass;
  protected List<ConfigurationProperty> providerConfigurationProperties;
  protected List<ConfigurationProperty> resourceTemplateConfigurationProperties;
  protected List<DisplayProperty> resourceDisplayProperties =
      Collections.emptyList();

  public B id(String id) {
    this.id = id;
    return getThis();
  }

  public B name(String name) {
    this.name = name;
    return getThis();
  }

  public B description(String description) {
    this.description = description;
    return getThis();
  }

  public B providerClass(Class<? extends ResourceProvider<?, ?>> providerClass) {
    this.providerClass = providerClass;
    return getThis();
  }

  public B providerConfigurationProperties(
      List<ConfigurationProperty> providerConfigurationProperties) {
    this.providerConfigurationProperties = providerConfigurationProperties;
    return getThis();
  }

  public B resourceTemplateConfigurationProperties(
      List<ConfigurationProperty> resourceTemplateConfigurationProperties) {
    this.resourceTemplateConfigurationProperties = resourceTemplateConfigurationProperties;
    return getThis();
  }

  public B resourceDisplayProperties(
      List<DisplayProperty> resourceDisplayProperties) {
    this.resourceDisplayProperties = resourceDisplayProperties;
    return getThis();
  }

  public T build() {
    return build(id, name, description, providerClass,
        providerConfigurationProperties, resourceTemplateConfigurationProperties,
        resourceDisplayProperties);
  }

  protected abstract T build(String id, String name, String description,
      Class<? extends ResourceProvider<?, ?>> providerClass,
      List<ConfigurationProperty> providerConfigurationProperties,
      List<ConfigurationProperty> resourceTemplateConfigurationProperties,
      List<DisplayProperty> resourceDisplayProperties);

  /**
   * Returns this, as an instance of the type parameter.
   *
   * @return this, as an instance of the type parameter
   */
  @SuppressWarnings("unchecked")
  protected B getThis() {
    return (B) this;
  }
}
