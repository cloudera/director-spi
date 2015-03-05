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

import com.cloudera.director.spi.v1.model.Instance;
import com.cloudera.director.spi.v1.model.InstanceState;
import com.cloudera.director.spi.v1.model.Resource;
import com.cloudera.director.spi.v1.model.ResourceTemplate;

import java.util.Collection;
import java.util.Map;

/**
 * Represents a provider of instances.
 */
public interface InstanceProvider extends ResourceProvider {

  /**
   * Atomically allocates multiple instances with the specified identifiers based on a single instance template.
   * In addition to the guarantees made by {@link ResourceProvider#allocate}, this method guarantees that all instances
   * with details will have private IP addresses.
   *
   * @param template    the instance template
   * @param resourceIds the unique identifiers for the instances
   * @param minCount    the minimum number of instances to allocate if not all instances can be allocated
   * @return the instances, some or all of which may have <code>null</code> details if they were not allocated successfully
   * @throws InterruptedException if the operation is interrupted
   */
  @Override
  Collection<? extends Instance> allocate(ResourceTemplate template, Collection<String> resourceIds, int minCount)
      throws InterruptedException;

  /**
   * Returns current instance information for the specified instances, which are guaranteed to have
   * been created by this provider.
   *
   * @param instances instances previously created by this provider, some or all of which may have
   *                  <code>null</code> details if they are not fully ready for use
   * @return new instances, with the most currently available information, corresponding to the
   * subset of the instances which still exist. Some or all of them may have <code>null</code>
   * details if they are not fully ready for use
   * @throws InterruptedException if the operation is interrupted
   */
  @Override
  Collection<? extends Instance> find(Collection<? extends Resource> instances)
      throws InterruptedException;

  /**
   * Returns a map from instance identifiers to instance state for the specified batch of instances.
   *
   * @param instances instances previously created by this provider, which are guaranteed not to have
   *                  <code>null</code> details
   * @return the map from instance identifiers to instance state for the specified batch of instances
   */
  Map<String, InstanceState> getInstanceState(Collection<? extends Resource> instances);
}
