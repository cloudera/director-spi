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

package com.cloudera.director.spi.v1.model;

import java.net.InetAddress;

/**
 * Represents a cloud server instance.
 */
public interface Instance<T extends InstanceTemplate> extends Resource<T> {

  /**
   * Returns the private IP address of the instance, which may be <code>null</code>
   * (if, for example, the instance has not finished being provisioned).
   *
   * @return the private IP address of the instance, which may be <code>null</code>
   */
  InetAddress getPrivateIpAddress();
}
