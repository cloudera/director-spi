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

package com.cloudera.director.spi.v1.compute;

import com.cloudera.director.spi.v1.model.Resource;
import com.cloudera.director.spi.v1.model.ResourceTemplate;
import com.cloudera.director.spi.v1.provider.InstanceProvider;

import java.util.Collection;

/**
 * Represents a provider of compute instances.
 */
public interface ComputeProvider extends InstanceProvider {

  /**
   * Atomically allocates multiple compute instances with the specified identifiers based on a single instance template.
   *
   * @param template the instance template
   * @param ids      the unique identifiers for the instances
   * @param minCount the minimum number of instances to allocate if not all instances can be allocated
   * @return the instances, some or all of which may have <code>null</code> details if they were not allocated successfully
   * @throws InterruptedException if the operation is interrupted
   */
  @Override
  Collection<? extends ComputeInstance> allocate(ResourceTemplate template, Collection<String> ids, int minCount)
      throws InterruptedException;

  /**
   * Returns current compute instance information for the specified compute instances, which are guaranteed to have
   * been created by this provider.
   *
   * @param computeInstances compute instances previously created by this provider, some or all of which may have
   *                         <code>null</code> details if they are not fully ready for use
   * @return new compute instances, with the most currently available information, corresponding to the
   * subset of the compute instances which still exist. Some or all of them may have <code>null</code>
   * details if they are not fully ready for use
   * @throws InterruptedException if the operation is interrupted
   */
  @Override
  Collection<? extends ComputeInstance> find(Collection<? extends Resource> computeInstances)
      throws InterruptedException;
}
