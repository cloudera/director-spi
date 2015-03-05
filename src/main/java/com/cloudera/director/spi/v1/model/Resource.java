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

import java.util.Locale;
import java.util.Map;

/**
 * Represents a cloud resource.
 */
public interface Resource {

  /**
   * Represents a type of cloud resource.
   */
  interface Type {

    /**
     * Returns a human-readable description of the resource type.
     *
     * @param locale the locale
     * @return a human-readable description of the resource type
     */
    String getDescription(Locale locale);
  }

  /**
   * Returns the type of the resource.
   *
   * @return the type of the resource
   */
  Type getType();

  /**
   * Returns the template from which the resource was created.
   *
   * @return the template from which the resource was created
   */
  ResourceTemplate getTemplate();

  /**
   * Returns an external identifier which uniquely identifies the resource within its type and scope.
   *
   * @return the identifier
   */
  String getId();

  /**
   * Returns a human-readable description of the resource.
   *
   * @param locale the locale
   * @return a human-readable description of the resource
   */
  String getDescription(Locale locale);

  /**
   * Returns a map representing the properties of the resource.
   *
   * @return a map representing the properties of the resource
   */
  Map<String, String> getPropertyValues();

  /**
   * Returns the provider-specific resource details, which may be <code>null</code> if (for example)
   * the resource is still being provisioned.
   *
   * @return the provider-specific resource details
   */
  Object unwrap();
}
