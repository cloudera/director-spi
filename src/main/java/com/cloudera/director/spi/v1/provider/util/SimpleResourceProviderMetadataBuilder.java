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
import com.cloudera.director.spi.v1.model.DisplayProperty;
import com.cloudera.director.spi.v1.provider.ResourceProvider;

import java.util.List;

public class SimpleResourceProviderMetadataBuilder
    extends AbstractResourceProviderMetadataBuilder<SimpleResourceProviderMetadataBuilder,
    SimpleResourceProviderMetadata> {

  @Override
  protected SimpleResourceProviderMetadata build(String id, String name, String description,
      Class<? extends ResourceProvider<?, ?>> providerClass,
      List<ConfigurationProperty> providerConfigurationProperties,
      List<ConfigurationProperty> resourceTemplateConfigurationProperties,
      List<DisplayProperty> resourceDisplayProperties) {
    return new SimpleResourceProviderMetadata(id, name, description, providerClass,
        providerConfigurationProperties, resourceTemplateConfigurationProperties,
        resourceDisplayProperties);
  }

  @Override
  protected SimpleResourceProviderMetadataBuilder getThis() {
    return this;
  }
}
