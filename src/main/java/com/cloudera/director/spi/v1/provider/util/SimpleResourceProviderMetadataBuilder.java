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

import com.cloudera.director.spi.v1.model.ConfigurationProperty;

import java.util.List;

public class SimpleResourceProviderMetadataBuilder {

  private String id;
  private String name;
  private String description;
  private List<ConfigurationProperty> providerConfigurationProperties;
  private List<ConfigurationProperty> resourceTemplateConfigurationProperties;


  public SimpleResourceProviderMetadataBuilder id(String id) {
    this.id = id;
    return this;
  }

  public SimpleResourceProviderMetadataBuilder name(String name) {
    this.name = name;
    return this;
  }

  public SimpleResourceProviderMetadataBuilder description(String description) {
    this.description = description;
    return this;
  }

  public SimpleResourceProviderMetadataBuilder providerConfigurationProperties(
      List<ConfigurationProperty> providerConfigurationProperties) {
    this.providerConfigurationProperties = providerConfigurationProperties;
    return this;
  }

  public SimpleResourceProviderMetadataBuilder resourceTemplateConfigurationProperties(
      List<ConfigurationProperty> resourceTemplateConfigurationProperties) {
    this.resourceTemplateConfigurationProperties = resourceTemplateConfigurationProperties;
    return this;
  }

  public SimpleResourceProviderMetadata build() {
    return new SimpleResourceProviderMetadata(id, name, description,
        providerConfigurationProperties, resourceTemplateConfigurationProperties);
  }
}