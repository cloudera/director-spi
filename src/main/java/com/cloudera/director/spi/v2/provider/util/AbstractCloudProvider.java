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

import static com.cloudera.director.spi.v2.util.Preconditions.checkNotNull;

import com.cloudera.director.spi.v2.model.ConfigurationValidator;
import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.exception.PluginExceptionConditionAccumulator;
import com.cloudera.director.spi.v2.provider.CloudProvider;
import com.cloudera.director.spi.v2.provider.CloudProviderMetadata;
import com.cloudera.director.spi.v2.provider.ResourceProviderMetadata;

/**
 * Abstract base class for cloud provider implementations.
 */
public abstract class AbstractCloudProvider implements CloudProvider {

  /**
   * The cloud provider metadata.
   */
  private final CloudProviderMetadata providerMetadata;

  /**
   * The cloud localization context.
   */
  private final LocalizationContext localizationContext;

  /**
   * Creates an abstract cloud provider with the specified parameters.
   *
   * @param providerMetadata        the cloud provider metadata
   * @param rootLocalizationContext the root localization context
   */
  public AbstractCloudProvider(CloudProviderMetadata providerMetadata,
      LocalizationContext rootLocalizationContext) {
    this.providerMetadata = checkNotNull(providerMetadata, "providerMetadata is null");
    this.localizationContext = providerMetadata.getLocalizationContext(
        checkNotNull(rootLocalizationContext, "rootLocalizationContext is null"));
  }

  @Override
  public CloudProviderMetadata getProviderMetadata() {
    return providerMetadata;
  }

  @Override
  public LocalizationContext getLocalizationContext() {
    return localizationContext;
  }

  @Override
  public void validateResourceProviderConfiguration(String name,
      ResourceProviderMetadata resourceProviderMetadata,
      Configured configuration,
      PluginExceptionConditionAccumulator accumulator) {
    LocalizationContext providerLocalizationContext =
        resourceProviderMetadata.getLocalizationContext(getLocalizationContext());
    getResourceProviderConfigurationValidator(resourceProviderMetadata).validate(
        name, configuration, accumulator, providerLocalizationContext);
  }

  /**
   * Returns the resource provider configuration validator for the specified resource provider
   * ID. The validator should make a best-faith effort to identify configuration problems that will
   * prevent successful resource provider creation.
   *
   * @return the configuration validator
   */
  protected ConfigurationValidator getResourceProviderConfigurationValidator(
      ResourceProviderMetadata resourceProviderMetadata
  ) {
    return resourceProviderMetadata.getProviderConfigurationValidator();
  }
}
