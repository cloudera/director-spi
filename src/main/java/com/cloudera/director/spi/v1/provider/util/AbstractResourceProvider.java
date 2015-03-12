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

package com.cloudera.director.spi.v1.provider.util;

import com.cloudera.director.spi.v1.model.Configured;
import com.cloudera.director.spi.v1.model.Resource;
import com.cloudera.director.spi.v1.model.ResourceTemplate;
import com.cloudera.director.spi.v1.model.util.AbstractConfigured;
import com.cloudera.director.spi.v1.provider.ResourceProvider;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Abstract base class for resource provider implementations.
 */
public abstract class AbstractResourceProvider<R extends Resource<T>, T extends ResourceTemplate>
    extends AbstractConfigured implements ResourceProvider<R, T> {

  /**
   * Creates an abstract resource provider with the specified parameters.
   *
   * @param configuration the configuration
   */
  public AbstractResourceProvider(Configured configuration) {
    super(configuration);
  }

  /**
   * Returns a type safe map from resource identifiers to the specified resource subclass.
   *
   * @param resources     the collection of resources
   * @param resourceClass the resource class
   * @param <X>           the type of resource
   * @return the type safe map from resource identifiers to the specified resource subclass
   * @throws ClassCastException if any of the resources are not of the specified class
   */
  protected <X extends Resource> Map<String, X> indexResourcesById(
      Collection<? extends Resource> resources, Class<X> resourceClass) {

    Map<String, X> resourcesById = new LinkedHashMap<String, X>();
    for (Resource resource : resources) {
      resourcesById.put(resource.getId(), resourceClass.cast(resource));
    }
    return resourcesById;
  }
}
