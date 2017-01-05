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

package com.cloudera.director.spi.v2.model.util;

import com.cloudera.director.spi.v2.model.Instance;
import com.cloudera.director.spi.v2.model.InstanceTemplate;

import java.net.InetAddress;

/**
 * Abstract base class for instance implementations.
 *
 * @param <T> the type of instance template from which instances are constructed
 * @param <D> the type of instance details
 */
public abstract class AbstractInstance<T extends InstanceTemplate, D>
    extends AbstractResource<T, D> implements Instance<T> {

  /**
   * The resource type representing a generic instance.
   */
  public static final Type TYPE = new ResourceType("Instance");

  /**
   * The private IP address of the instance.
   */
  private InetAddress privateIpAddress;

  /**
   * Creates an abstract instance with the specified parameters.
   *
   * @param template         the template from which the resource was created
   * @param instanceId       the instance identifier
   * @param privateIpAddress the private IP address of this instance
   */
  protected AbstractInstance(T template, String instanceId, InetAddress privateIpAddress) {
    this(template, instanceId, privateIpAddress, null);
  }

  /**
   * Creates an abstract instance with the specified parameters.
   *
   * @param template         the template from which the resource was created
   * @param instanceId       the instance identifier
   * @param privateIpAddress the private IP address of this instance
   * @param details          the provider-specific instance details
   */
  protected AbstractInstance(T template, String instanceId, InetAddress privateIpAddress, D details) {
    super(template, instanceId, details);
    this.privateIpAddress = privateIpAddress;
  }

  @Override
  public Type getType() {
    return TYPE;
  }

  @Override
  public InetAddress getPrivateIpAddress() {
    return privateIpAddress;
  }

  /**
   * Sets the private IP address of this instance.
   *
   * @param privateIpAddress the private IP address of this instance
   */
  protected void setPrivateIpAddress(InetAddress privateIpAddress) {
    this.privateIpAddress = privateIpAddress;
  }
}
