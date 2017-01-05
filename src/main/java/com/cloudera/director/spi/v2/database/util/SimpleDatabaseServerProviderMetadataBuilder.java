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

package com.cloudera.director.spi.v2.database.util;

import com.cloudera.director.spi.v2.database.DatabaseType;
import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.DisplayProperty;
import com.cloudera.director.spi.v2.provider.ResourceProvider;
import com.cloudera.director.spi.v2.provider.util.AbstractResourceProviderMetadataBuilder;

import java.util.List;
import java.util.Set;

/**
 * Builder for simple database server provider metadata.
 */
public class SimpleDatabaseServerProviderMetadataBuilder
    extends AbstractResourceProviderMetadataBuilder<SimpleDatabaseServerProviderMetadataBuilder,
    SimpleDatabaseServerProviderMetadata> {

  /**
   * The set of supported database types.
   */
  private Set<DatabaseType> supportedDatabaseTypes;

  /**
   * Sets the set of supported database types.
   *
   * @param supportedDatabaseTypes the set of supported database types
   */
  public SimpleDatabaseServerProviderMetadataBuilder supportedDatabaseTypes(Set<DatabaseType> supportedDatabaseTypes) {
    this.supportedDatabaseTypes = supportedDatabaseTypes;
    return this;
  }

  @Override
  protected SimpleDatabaseServerProviderMetadataBuilder getThis() {
    return this;
  }

  @Override
  protected SimpleDatabaseServerProviderMetadata build(String id, String name, String description,
      Class<? extends ResourceProvider<?, ?>> providerClass,
      List<ConfigurationProperty> providerConfigurationProperties,
      List<ConfigurationProperty> resourceTemplateConfigurationProperties,
      List<DisplayProperty> resourceDisplayProperties) {
    return new SimpleDatabaseServerProviderMetadata(id, name, description, providerClass,
        providerConfigurationProperties, resourceTemplateConfigurationProperties,
        resourceDisplayProperties, supportedDatabaseTypes);
  }
}
