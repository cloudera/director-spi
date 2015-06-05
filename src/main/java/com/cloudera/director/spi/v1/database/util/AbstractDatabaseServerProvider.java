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

package com.cloudera.director.spi.v1.database.util;

import com.cloudera.director.spi.v1.database.DatabaseServerInstance;
import com.cloudera.director.spi.v1.database.DatabaseServerInstanceTemplate;
import com.cloudera.director.spi.v1.database.DatabaseServerProvider;
import com.cloudera.director.spi.v1.model.Configured;
import com.cloudera.director.spi.v1.model.LocalizationContext;
import com.cloudera.director.spi.v1.provider.ResourceProviderMetadata;
import com.cloudera.director.spi.v1.provider.util.AbstractInstanceProvider;

/**
 * Abstract base class for database server provider implementations.
 *
 * @param <R> type of the database instance created by this provider
 * @param <T> type of the instance template needed to create an instance
 */
public abstract class AbstractDatabaseServerProvider
    <R extends DatabaseServerInstance<T>, T extends DatabaseServerInstanceTemplate>
    extends AbstractInstanceProvider<R, T> implements DatabaseServerProvider<R, T> {

  /**
   * Creates an abstract database server provider with the specified parameters.
   *
   * @param configuration       the configuration
   * @param providerMetadata    the resource provider metadata
   * @param localizationContext the localization context
   */
  public AbstractDatabaseServerProvider(Configured configuration,
      ResourceProviderMetadata providerMetadata, LocalizationContext localizationContext) {
    super(configuration, providerMetadata, localizationContext);
  }
}
