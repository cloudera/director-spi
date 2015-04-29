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

import com.cloudera.director.spi.v1.model.ConfigurationProperty;
import com.cloudera.director.spi.v1.model.ConfigurationPropertyToken;
import com.cloudera.director.spi.v1.model.Configured;
import com.cloudera.director.spi.v1.model.InstanceTemplate;
import com.cloudera.director.spi.v1.model.ResourceTemplate;
import com.cloudera.director.spi.v1.model.util.SimpleConfigurationPropertyBuilder;
import com.cloudera.director.spi.v1.util.ConfigurationPropertiesUtil;

import java.util.List;
import java.util.Map;

/**
 * Represents a template for constructing cloud compute instances.
 */
public class ComputeInstanceTemplate extends InstanceTemplate {

  /**
   * The list of configuration properties (including inherited properties).
   */
  private static final List<ConfigurationProperty> CONFIGURATION_PROPERTIES =
      ConfigurationPropertiesUtil.merge(
          InstanceTemplate.getConfigurationProperties(),
          ConfigurationPropertiesUtil.asConfigurationPropertyList(ComputeInstanceTemplateConfigurationPropertyToken.values())
      );

  /**
   * Returns the list of configuration properties for creating a compute instance template,
   * including inherited properties.
   *
   * @return the list of configuration properties for creating a compute instance template,
   * including inherited properties
   */
  public static List<ConfigurationProperty> getConfigurationProperties() {
    return CONFIGURATION_PROPERTIES;
  }

  /**
   * Compute instance configuration properties.
   */
  public static enum ComputeInstanceTemplateConfigurationPropertyToken implements ConfigurationPropertyToken {

    /**
     * The provider-specific image ID.
     */
    IMAGE(new SimpleConfigurationPropertyBuilder()
        .configKey("image")
        .name("Image ID")
        .required(true)
        .widget(ConfigurationProperty.Widget.OPENLIST)
        .defaultDescription("The image ID.")
        .defaultErrorMessage("Image is mandatory")
        .build()),

    /**
     * The provider-specific instance type.
     */
    TYPE(new SimpleConfigurationPropertyBuilder()
        .configKey("type")
        .name("Type")
        .required(true)
        .widget(ConfigurationProperty.Widget.OPENLIST)
        .defaultDescription("The instance type.")
        .defaultErrorMessage("Instance type is mandatory")
        .build());

    /**
     * The configuration property.
     */
    private ConfigurationProperty configurationProperty;

    /**
     * Creates a configuration property token with the specified parameters.
     *
     * @param configurationProperty the configuration property
     */
    private ComputeInstanceTemplateConfigurationPropertyToken(ConfigurationProperty configurationProperty) {
      this.configurationProperty = configurationProperty;
    }

    @Override
    public ConfigurationProperty unwrap() {
      return configurationProperty;
    }
  }

  /**
   * Creates a compute instance template from the specified resource template.
   *
   * @param resourceTemplate another resource template
   */
  public ComputeInstanceTemplate(ResourceTemplate resourceTemplate) {
    this(resourceTemplate.getName(), resourceTemplate, resourceTemplate.getTags());
  }

  /**
   * Creates a compute instance template with the specified parameters.
   *
   * @param name          the name of the template
   * @param configuration the source of configuration
   * @param tags          the map of tags to be applied to resources created from the template
   */
  public ComputeInstanceTemplate(String name, Configured configuration, Map<String, String> tags) {
    super(name, configuration, tags);
  }
}
