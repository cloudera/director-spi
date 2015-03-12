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

package com.cloudera.director.spi.v1.database.util;

import com.cloudera.director.spi.v1.database.DatabaseServerInstance;
import com.cloudera.director.spi.v1.database.DatabaseServerInstanceTemplate;
import com.cloudera.director.spi.v1.model.util.AbstractInstance;

import java.net.InetAddress;

/**
 * Abstract base class for database server instance implementations.
 *
 * @param <T> the type of database server instance template from which database server instances are constructed
 */
public abstract class AbstractDatabaseServerInstance<T extends DatabaseServerInstanceTemplate>
    extends AbstractInstance<T> implements DatabaseServerInstance<T> {

  /**
   * The resource type representing a database server instance.
   */
  public static final Type TYPE = new ResourceType("DatabaseServerInstance");

  /**
   * Creates an abstract database server instance with the specified parameters.
   *
   * @param template         the template from which the instance was created
   * @param instanceId       the instance identifier
   * @param privateIpAddress the private IP address of this instance
   */
  protected AbstractDatabaseServerInstance(T template, String instanceId, InetAddress privateIpAddress) {
    super(template, instanceId, privateIpAddress);
  }

  @Override
  public Type getType() {
    return TYPE;
  }

}
