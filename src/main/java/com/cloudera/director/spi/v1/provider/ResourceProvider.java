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

import com.cloudera.director.spi.v1.model.ConfigurationProperty;
import com.cloudera.director.spi.v1.model.Resource;
import com.cloudera.director.spi.v1.model.ResourceTemplate;

import java.util.Collection;
import java.util.List;

/**
 * Represents a provider of resources.
 */
public interface ResourceProvider {

  /**
   * Returns the resource provider metadata.
   *
   * @return the resource provider metadata
   */
  ResourceProviderMetadata getProviderMetadata();

  /**
   * Returns the type of resource managed by the provider.
   *
   * @return the type of resource managed by the provider
   */
  Resource.Type getResourceType();

  /**
   * Returns the list of configuration properties that can be given to the provider to
   * configure a resource template.
   *
   * @return the list of configuration properties that can be given to the provider to
   * configure a resource template
   */
  List<ConfigurationProperty> getTemplateConfigurationProperties();

  /**
   * Atomically allocates multiple resources with the specified identifiers based on a single resource template.
   * If not all the resources can be allocated, the number of resources allocated must at least the specified minimum
   * or the method must fail cleanly with no billing implications.
   *
   * @param template    the resource template
   * @param resourceIds the unique identifiers for the resources
   * @param minCount    the minimum number of resources to allocate if not all resources can be allocated
   * @return the resources, some or all of which may have <code>null</code> details if they are not fully ready for use
   * @throws InterruptedException if the operation is interrupted
   */
  Collection<? extends Resource> allocate(ResourceTemplate template, Collection<String> resourceIds, int minCount)
      throws InterruptedException;

  /**
   * Returns current resource information for the specified resources, which are guaranteed to have
   * been created by this provider. This method takes resources, rather than resource identifiers,
   * so that any additional information associated with the resource, such as its template,
   * can be attached to the resulting resources.
   *
   * @param resources resources previously created by this provider, some or all of which may have
   *                  <code>null</code> details if they are not fully ready for use
   * @return new resources, with the most currently available information, corresponding to the
   * subset of the resources which still exist. Some or all of them may have <code>null</code>
   * details if they are not fully ready for use
   * @throws InterruptedException if the operation is interrupted
   */
  Collection<? extends Resource> find(Collection<? extends Resource> resources)
      throws InterruptedException;

  /**
   * Permanently removes the specified resources, which are guaranteed to have been created by this provider.
   *
   * @param resources resources previously created by this provider, which are guaranteed not to have
   *                  <code>null</code> details
   * @throws InterruptedException if the operation is interrupted
   */
  void delete(Collection<? extends Resource> resources)
      throws InterruptedException;
}
