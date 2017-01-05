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
import com.cloudera.director.spi.v2.provider.CredentialsProviderMetadata;
import com.cloudera.director.spi.v2.provider.ResourceProviderMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * @see com.cloudera.director.spi.v2.provider.CloudProviderMetadata
 */
public class SimpleCloudProviderMetadataBuilder {

  private String id;

  private String name;

  private String description;

  private List<ConfigurationProperty> configurationProperties = new ArrayList<ConfigurationProperty>();

  private CredentialsProviderMetadata credentialsProviderMetadata;

  private List<ResourceProviderMetadata> resourceProviderMetadata;

  public SimpleCloudProviderMetadataBuilder id(String id) {
    this.id = id;
    return this;
  }

  public SimpleCloudProviderMetadataBuilder name(String name) {
    this.name = name;
    return this;
  }

  public SimpleCloudProviderMetadataBuilder description(String description) {
    this.description = description;
    return this;
  }

  public SimpleCloudProviderMetadataBuilder configurationProperties(
      List<ConfigurationProperty> configurationProperties) {
    this.configurationProperties = configurationProperties;
    return this;
  }

  public SimpleCloudProviderMetadataBuilder credentialsProviderMetadata(
      CredentialsProviderMetadata credentialsProviderMetadata) {
    this.credentialsProviderMetadata = credentialsProviderMetadata;
    return this;
  }

  public SimpleCloudProviderMetadataBuilder resourceProviderMetadata(
      List<ResourceProviderMetadata> resourceProviderMetadata) {
    this.resourceProviderMetadata = resourceProviderMetadata;
    return this;
  }

  public SimpleCloudProviderMetadata build() {
    return new SimpleCloudProviderMetadata(id, name, description,
        configurationProperties, credentialsProviderMetadata, resourceProviderMetadata
    );
  }
}
