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

package com.cloudera.director.spi.v2.compute.util;

import com.cloudera.director.spi.v2.compute.ComputeInstance;
import com.cloudera.director.spi.v2.compute.ComputeInstanceTemplate;
import com.cloudera.director.spi.v2.compute.VirtualizationType;
import com.cloudera.director.spi.v2.model.util.AbstractInstance;

import java.net.InetAddress;

/**
 * Abstract base class for compute instance implementations.
 *
 * @param <T> the type of compute instance template from which compute instances are constructed
 * @param <D> the type of instance details
 */
public abstract class AbstractComputeInstance<T extends ComputeInstanceTemplate, D>
    extends AbstractInstance<T, D> implements ComputeInstance<T> {

  /**
   * The resource type representing a generic compute instance.
   */
  public static final Type TYPE = new ResourceType("ComputeInstance");

  /**
   * The virtualization type.
   */
  private VirtualizationType virtualizationType;

  /**
   * Creates an abstract compute instance with the specified parameters.
   *
   * @param template   the template from which the resource was created
   * @param instanceId the instance identifier
   */
  protected AbstractComputeInstance(T template, String instanceId, InetAddress privateIpAddress) {
    this(template, instanceId, privateIpAddress, null);
  }

  /**
   * Creates an abstract compute instance with the specified parameters.
   *
   * @param template   the template from which the resource was created
   * @param instanceId the instance identifier
   * @param details    the provider-specific instance details
   * @deprecated  use {@link #AbstractComputeInstance(ComputeInstanceTemplate, String, InetAddress,
   * VirtualizationType, Object)} instead
   */
  protected AbstractComputeInstance(T template, String instanceId, InetAddress privateIpAddress, D details) {
    this(template, instanceId, privateIpAddress, VirtualizationType.UNKNOWN, details);
  }

  /**
   * Creates an abstract compute instance with the specified parameters.
   *
   * @param template           the template from which the resource was created
   * @param instanceId         the instance identifier
   * @param virtualizationType the virtualization type
   * @param details            the provider-specific instance details
   */
  protected AbstractComputeInstance(T template, String instanceId, InetAddress privateIpAddress,
      VirtualizationType virtualizationType, D details) {
    super(template, instanceId, privateIpAddress, details);
    this.virtualizationType = virtualizationType;
  }

  @Override
  public Type getType() {
    return TYPE;
  }

  @Override
  public VirtualizationType getVirtualizationType() {
    return virtualizationType;
  }

  /**
   * Sets the virtualization type.
   *
   * @param virtualizationType the virtualization type
   */
  protected void setVirtualizationType(VirtualizationType virtualizationType) {
    this.virtualizationType = virtualizationType;
  }
}
