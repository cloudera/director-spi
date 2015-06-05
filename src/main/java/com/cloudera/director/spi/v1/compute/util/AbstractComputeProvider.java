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

package com.cloudera.director.spi.v1.compute.util;

import com.cloudera.director.spi.v1.compute.ComputeInstance;
import com.cloudera.director.spi.v1.compute.ComputeInstanceTemplate;
import com.cloudera.director.spi.v1.compute.ComputeProvider;
import com.cloudera.director.spi.v1.model.Configured;
import com.cloudera.director.spi.v1.model.LocalizationContext;
import com.cloudera.director.spi.v1.provider.ResourceProviderMetadata;
import com.cloudera.director.spi.v1.provider.util.AbstractInstanceProvider;

/**
 * Abstract base class for compute provider implementations.
 *
 * @param <R> type of the resource that can be created by this provider
 * @param <T> type of the template needed to create resources
 */
public abstract class AbstractComputeProvider<R extends ComputeInstance<T>, T extends ComputeInstanceTemplate>
    extends AbstractInstanceProvider<R, T> implements ComputeProvider<R, T> {

  /**
   * Creates an abstract compute provider with the specified parameters.
   *
   * @param configuration       the configuration
   * @param providerMetadata    the resource provider metadata
   * @param localizationContext the localization context
   */
  public AbstractComputeProvider(Configured configuration,
      ResourceProviderMetadata providerMetadata, LocalizationContext localizationContext) {
    super(configuration, providerMetadata, localizationContext);
  }
}
