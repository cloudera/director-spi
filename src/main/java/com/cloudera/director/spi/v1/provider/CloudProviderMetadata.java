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

import java.util.List;

/**
 * Provides metadata about a cloud provider.
 */
public interface CloudProviderMetadata extends ProviderMetadata {

  /**
   * Returns resource provider metadata for all supported resource providers.
   *
   * @return resource provider metadata for all supported resource providers
   */
  List<ResourceProviderMetadata> getResourceProviderMetadata();

  /**
   * Returns resource provider metadata for all resource providers of the specified type.
   *
   * @param resourceProviderType the resource provider type
   * @return resource provider metadata for all resource providers of the specified type
   */
  List<ResourceProviderMetadata> getResourceProviderMetadata(Class<?> resourceProviderType);

  /**
   * Returns the resource provider metadata for the specified resource provider ID.
   *
   * @param resourceProviderId the resource provider ID
   * @return the resource provider metadata for the specified resource provider ID
   */
  ResourceProviderMetadata getResourceProviderMetadata(String resourceProviderId);
}
