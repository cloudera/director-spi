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

package com.cloudera.director.spi.v1.database;

import com.cloudera.director.spi.v1.model.AbstractInstance;

/**
 * Abstract base class for database server instance implementations.
 *
 * @param <D> the type of database server instance details
 * @param <T> the type of database server instance template from which database server instances are constructed
 */
public class AbstractDatabaseServerInstance<D, T extends DatabaseServerInstanceTemplate>
    extends AbstractInstance<D, T> implements DatabaseServerInstance {

  /**
   * The resource type representing a database server instance.
   */
  public static final Type TYPE = new ResourceType("DatabaseServerInstance");

  /**
   * Creates an abstract database server instance with the specified parameters.
   *
   * @param template           the template from which the instance was created
   * @param instanceIdentifier the instance identifier
   */
  protected AbstractDatabaseServerInstance(T template, String instanceIdentifier) {
    this(template, instanceIdentifier, null);
  }

  /**
   * Creates an abstract database server instance with the specified parameters.
   *
   * @param template           the template from which the instance was created
   * @param instanceIdentifier the instance identifier
   * @param instanceDetails    the provider-specific instance details
   */
  protected AbstractDatabaseServerInstance(T template, String instanceIdentifier, D instanceDetails) {
    super(template, instanceIdentifier, instanceDetails);
  }

  @Override
  public Type getType() {
    return TYPE;
  }
}
