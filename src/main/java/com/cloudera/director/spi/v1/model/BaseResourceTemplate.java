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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Base resource template implementation.
 */
public class BaseResourceTemplate extends AbstractConfigured implements ResourceTemplate {

  /**
   * The list of configuration properties (including inherited properties).
   */
  private static final List<ConfigurationProperty> CONFIGURATION_PROPERTIES = Collections.emptyList();

  /**
   * Returns the list of configuration properties for creating a resource template.
   *
   * @return the list of configuration properties for creating a resource template
   */
  public static List<ConfigurationProperty> getConfigurationProperties() {
    return CONFIGURATION_PROPERTIES;
  }

  /**
   * The name of the template.
   */
  private final String name;

  /**
   * The map of tags to be applied to resources created from the template.
   */
  private final Map<String, String> tags;

  /**
   * Creates a base resource template from the specified resource template.
   *
   * @param resourceTemplate another resource template
   */
  public BaseResourceTemplate(ResourceTemplate resourceTemplate) {
    this(resourceTemplate.getName(), resourceTemplate, resourceTemplate.getTags());
  }

  /**
   * Creates a base resource template with the specified parameters.
   *
   * @param name          the name of the template
   * @param configuration the source of configuration
   * @param tags          the map of tags to be applied to resources created from the template
   */
  public BaseResourceTemplate(String name, Configured configuration, Map<String, String> tags) {
    super(configuration);
    if (name == null) {
      throw new NullPointerException("name is null");
    }
    this.name = name;
    this.tags = (tags == null) ? Collections.<String, String>emptyMap() : Collections.unmodifiableMap(new LinkedHashMap<String, String>(tags));
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Map<String, String> getTags() {
    return tags;
  }
}
