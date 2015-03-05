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

import com.cloudera.director.spi.v1.model.AbstractConfigured;
import com.cloudera.director.spi.v1.model.BaseResourceTemplate;
import com.cloudera.director.spi.v1.model.ConfigurationProperty;
import com.cloudera.director.spi.v1.model.Configured;
import com.cloudera.director.spi.v1.model.Resource;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract base class for resource provider implementations.
 */
public abstract class AbstractResourceProvider extends AbstractConfigured
    implements ResourceProvider {

  /**
   * Creates an abstract resource provider with the specified parameters.
   *
   * @param configuration the configuration
   */
  public AbstractResourceProvider(Configured configuration) {
    super(configuration);
  }

  @Override
  public List<ConfigurationProperty> getTemplateConfigurationProperties() {
    return BaseResourceTemplate.getConfigurationProperties();
  }

  /**
   * Returns a typesafe map from resource identifiers to the specified resource subclass.
   *
   * @param resources     the collection of resources
   * @param resourceClass the resource class
   * @param <T>           the type of resource
   * @return the typesafe map from resource identifiers to the specified resource subclass
   * @throws ClassCastException if any of the resources are not of the specified class
   */
  protected <T extends Resource> Map<String, T> indexResourcesById(Collection<? extends Resource> resources, Class<T> resourceClass) {
    Map<String, T> resourcesById = new LinkedHashMap<String, T>();
    for (Resource resource : resources) {
      resourcesById.put(resource.getId(), resourceClass.cast(resource));
    }
    return resourcesById;
  }
}
