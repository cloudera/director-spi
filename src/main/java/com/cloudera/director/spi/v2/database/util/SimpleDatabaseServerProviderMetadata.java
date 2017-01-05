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

import com.cloudera.director.spi.v2.database.DatabaseServerProviderMetadata;
import com.cloudera.director.spi.v2.database.DatabaseType;
import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.DisplayProperty;
import com.cloudera.director.spi.v2.provider.ResourceProvider;
import com.cloudera.director.spi.v2.provider.util.SimpleResourceProviderMetadata;
import com.cloudera.director.spi.v2.util.Preconditions;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Base class for database server provider metadata implementations.
 */
public class SimpleDatabaseServerProviderMetadata
    extends SimpleResourceProviderMetadata implements DatabaseServerProviderMetadata {

  /**
   * Returns a builder.
   *
   * @return the builder
   */
  public static SimpleDatabaseServerProviderMetadataBuilder databaseServerProviderMetadataBuilder() {
    return new SimpleDatabaseServerProviderMetadataBuilder();
  }

  /**
   * The set of supported database types.
   */
  private final Set<DatabaseType> supportedDatabaseTypes;

  /**
   * Creates an abstract resource provider metadata with the specified parameters.
   *
   * @param id                                      the unique identifier of the provider within
   *                                                its scope
   * @param name                                    the name of the provider, suitable for display
   *                                                in a drop-down menu of peer providers within
   *                                                the same scope
   * @param description                             the human-readable description of the provider
   * @param providerClass                           the resource provider class
   * @param providerConfigurationProperties         the list of configuration properties that can
   *                                                be given to configure the provider
   * @param resourceTemplateConfigurationProperties the list of configuration properties that can
   *                                                be given to configure resources provided by the
   *                                                provider
   * @param resourceDisplayProperties               the list of display properties present on
   *                                                resources provided by the provider
   * @param supportedDatabaseTypes                  the set of supported database types
   */
  public SimpleDatabaseServerProviderMetadata(String id, String name, String description,
      Class<? extends ResourceProvider<?, ?>> providerClass,
      List<ConfigurationProperty> providerConfigurationProperties,
      List<ConfigurationProperty> resourceTemplateConfigurationProperties,
      List<DisplayProperty> resourceDisplayProperties,
      Set<DatabaseType> supportedDatabaseTypes) {
    super(id, name, description, providerClass, providerConfigurationProperties,
        resourceTemplateConfigurationProperties, resourceDisplayProperties);
    this.supportedDatabaseTypes = Collections.unmodifiableSet(
        new HashSet<DatabaseType>(
            Preconditions.checkNotNull(supportedDatabaseTypes, "supportedDatabaseTypes is null")));
  }

  @Override
  public Set<DatabaseType> getSupportedDatabaseTypes() {
    return supportedDatabaseTypes;
  }
}
