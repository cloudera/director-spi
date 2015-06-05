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

import com.cloudera.director.spi.v1.model.Configured;
import com.cloudera.director.spi.v1.model.Instance;
import com.cloudera.director.spi.v1.model.InstanceTemplate;
import com.cloudera.director.spi.v1.model.LocalizationContext;
import com.cloudera.director.spi.v1.provider.InstanceProvider;
import com.cloudera.director.spi.v1.provider.ResourceProviderMetadata;

/**
 * Abstract base class for instance provider implementations.
 */
public abstract class AbstractInstanceProvider<R extends Instance<T>, T extends InstanceTemplate>
    extends AbstractResourceProvider<R, T> implements InstanceProvider<R, T> {

  /**
   * Creates an abstract instance provider with the specified parameters.
   *
   * @param configuration       the configuration
   * @param providerMetadata    the resource provider metadata
   * @param localizationContext the localization context
   */
  public AbstractInstanceProvider(Configured configuration,
      ResourceProviderMetadata providerMetadata, LocalizationContext localizationContext) {
    super(configuration, providerMetadata, localizationContext);
  }
}
