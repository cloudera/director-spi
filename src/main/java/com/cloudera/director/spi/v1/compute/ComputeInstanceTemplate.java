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
import com.cloudera.director.spi.v1.model.Configured;
import com.cloudera.director.spi.v1.model.InstanceTemplate;
import com.cloudera.director.spi.v1.model.ResourceTemplate;
import com.cloudera.director.spi.v1.util.ConfigurationPropertiesUtil;

import java.util.List;
import java.util.Locale;
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
          ConfigurationPropertiesUtil.asList(ComputeInstanceTemplateConfigurationProperty.values())
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
   * Instance configuration properties.
   */
  public static enum ComputeInstanceTemplateConfigurationProperty implements ConfigurationProperty {

    /**
     * The provider-specific image ID.
     */
    IMAGE("image", true, null, "The image ID.", "Image is mandatory"),

    /**
     * The provider-specific instance type.
     */
    TYPE("type", true, null, "The instance type.", "Instance type is mandatory");

    /**
     * Creates a configuration property with the specified parameters.
     *
     * @param configKey    the configuration key
     * @param required     whether the configuration property is required
     * @param defaultValue the default value of the configuration property
     * @param description  the human-readable description of the configuration property
     * @param errorMessage the human-readable error message for when a required configuration property is missing
     */
    private ComputeInstanceTemplateConfigurationProperty(String configKey, boolean required,
        String defaultValue, String description, String errorMessage) {
      this.configKey = configKey;
      this.required = required;
      this.defaultValue = defaultValue;
      this.description = description;
      this.errorMessage = errorMessage;
    }

    /**
     * The configuration key.
     */
    private final String configKey;

    /**
     * Whether the configuration property is required.
     */
    private final boolean required;

    /**
     * The default value of the configuration property.
     */
    private final String defaultValue;

    /**
     * The human-readable description of the configuration property.
     */
    private final String description;

    /**
     * The human-readable error message for when a required configuration property is missing.
     */
    private final String errorMessage;

    @Override
    public String getConfigKey() {
      return configKey;
    }

    @Override
    public boolean isRequired() {
      return required;
    }

    @Override
    public String getDefaultValue() {
      return defaultValue;
    }

    @Override
    public String getDescription(Locale locale) {
      return description;
    }

    @Override
    public String getMissingValueErrorMessage() {
      return errorMessage;
    }

    @Override
    public boolean isSensitive() {
      return false;
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
