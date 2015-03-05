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

import com.cloudera.director.spi.v1.model.AbstractInstance;

/**
 * Abstract base class for compute instance implementations.
 *
 * @param <D> the type of compute instance details
 * @param <T> the type of compute instance template from which compute instances are constructed
 */
public class AbstractComputeInstance<D, T extends ComputeInstanceTemplate>
    extends AbstractInstance<D, T> implements ComputeInstance {

  /**
   * The resource type representing a generic compute instance.
   */
  public static final Type TYPE = new ResourceType("ComputeInstance");

  /**
   * Creates an abstract compute instance with the specified parameters.
   *
   * @param template           the template from which the resource was created
   * @param instanceIdentifier the instance identifier
   */
  protected AbstractComputeInstance(T template, String instanceIdentifier) {
    this(template, instanceIdentifier, null);
  }

  /**
   * Creates an abstract compute instance with the specified parameters.
   *
   * @param template           the template from which the instance was created
   * @param instanceIdentifier the instance identifier
   * @param instanceDetails    the provider-specific instance details
   */
  protected AbstractComputeInstance(T template, String instanceIdentifier, D instanceDetails) {
    super(template, instanceIdentifier, instanceDetails);
  }

  @Override
  public Type getType() {
    return TYPE;
  }
}
