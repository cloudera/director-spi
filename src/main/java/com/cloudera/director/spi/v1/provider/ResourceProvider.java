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
import com.cloudera.director.spi.v1.model.Resource;
import com.cloudera.director.spi.v1.model.ResourceTemplate;

import java.util.Collection;
import java.util.Map;

/**
 * Represents a provider of cloud resources.
 *
 * @param <R> type of the resource created by this provider
 * @param <T> type of the template required by this provider to create a resource
 */
public interface ResourceProvider
    <R extends Resource<T>, T extends ResourceTemplate> {

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
   * Create a new resource template instance for this provider using the configuration
   *
   * @return a configured resource template
   */
  T createResourceTemplate(String name, Configured configuration, Map<String, String> tags);

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
  Collection<R> allocate(T template, Collection<String> resourceIds, int minCount) throws InterruptedException;

  /**
   * Returns current resource information for the specified resources, which are guaranteed to have
   * been created by this provider. This method takes resources, rather than resource identifiers,
   * so that any additional information associated with the resource, such as its template,
   * can be attached to the resulting resources.
   *
   * @param template    the template that was used to create those resources
   * @param resourceIds the unique identifiers for the resources
   * @return new resources, with the most currently available information, corresponding to the
   * subset of the resources which still exist. Some or all of them may have <code>null</code>
   * details if they are not fully ready for use
   * @throws InterruptedException if the operation is interrupted
   */
  Collection<R> find(T template, Collection<String> resourceIds) throws InterruptedException;

  /**
   * Permanently removes the specified resources, which are guaranteed to have been created by this provider.
   *
   * @param template    the template that was used to create those resources
   * @param resourceIds the unique identifiers for the resources
   * @throws InterruptedException if the operation is interrupted
   */
  void delete(T template, Collection<String> resourceIds) throws InterruptedException;
}
