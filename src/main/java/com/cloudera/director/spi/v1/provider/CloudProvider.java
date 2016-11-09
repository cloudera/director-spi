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

package com.cloudera.director.spi.v1.provider;

import com.cloudera.director.spi.v1.model.Configured;
import com.cloudera.director.spi.v1.model.LocalizationContext;
import com.cloudera.director.spi.v1.model.exception.PluginExceptionConditionAccumulator;

/**
 * A cloud provider, which may support multiple cloud resource types.
 */
public interface CloudProvider {

  /**
   * Returns the cloud provider metadata.
   *
   * @return the cloud provider metadata
   */
  CloudProviderMetadata getProviderMetadata();

  /**
   * Returns the cloud localization context.
   *
   * @return the cloud localization context
   */
  LocalizationContext getLocalizationContext();

  /**
   * Validates the specified configuration, accumulating errors and warnings in the specified
   * accumulator.
   *
   * @param name                     the name of the object being validated
   * @param resourceProviderMetadata the resource provider metadata
   * @param configuration            the configuration to be validated
   * @param accumulator              the exception condition accumulator
   */
  void validateResourceProviderConfiguration(String name,
      ResourceProviderMetadata resourceProviderMetadata,
      Configured configuration,
      PluginExceptionConditionAccumulator accumulator);

  /**
   * Returns the specified resource provider, using the specified configuration.
   *
   * @param resourceProviderId the resource provider ID, as returned by its metadata
   * @param configuration      the configuration
   * @return the specified resource provider, using the specified configuration
   * @throws java.util.NoSuchElementException if the cloud provider does not have a
   *                                          resource provider with the specified ID
   */
  ResourceProvider createResourceProvider(String resourceProviderId, Configured configuration);
}
