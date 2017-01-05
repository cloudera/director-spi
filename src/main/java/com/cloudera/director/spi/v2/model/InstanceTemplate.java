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

package com.cloudera.director.spi.v2.model;

import static com.cloudera.director.spi.v2.model.InstanceTemplate.InstanceTemplateConfigurationPropertyToken.INSTANCE_NAME_PREFIX;

import com.cloudera.director.spi.v2.model.util.SimpleConfigurationPropertyBuilder;
import com.cloudera.director.spi.v2.model.util.SimpleResourceTemplate;
import com.cloudera.director.spi.v2.util.ConfigurationPropertiesUtil;

import java.util.List;
import java.util.Map;

/**
 * Represents a template for constructing cloud instances.
 */
public class InstanceTemplate extends SimpleResourceTemplate {

  /**
   * The list of configuration properties (including inherited properties).
   */
  private static final List<ConfigurationProperty> CONFIGURATION_PROPERTIES =
      ConfigurationPropertiesUtil.merge(
          SimpleResourceTemplate.getConfigurationProperties(),
          ConfigurationPropertiesUtil.asConfigurationPropertyList(
              InstanceTemplateConfigurationPropertyToken.values())
      );

  /**
   * Returns the list of configuration properties for creating an instance template,
   * including inherited properties.
   *
   * @return the list of configuration properties for creating an instance template,
   * including inherited properties
   */
  public static List<ConfigurationProperty> getConfigurationProperties() {
    return CONFIGURATION_PROPERTIES;
  }

  /**
   * Instance template configuration property tokens.
   */
  // Fully qualifying class name due to compiler bug
  public static enum InstanceTemplateConfigurationPropertyToken
      implements com.cloudera.director.spi.v2.model.ConfigurationPropertyToken {

    /**
     * String to use as prefix for instance names.
     */
    INSTANCE_NAME_PREFIX(new SimpleConfigurationPropertyBuilder()
        .configKey("instanceNamePrefix")
        .name("Instance name prefix")
        .defaultValue("director")
        .defaultDescription("Prefix used when generating instance names in AWS"
            + " (not part of the hostname).")
        .build());

    /**
     * The configuration property.
     */
    private final ConfigurationProperty configurationProperty;

    /**
     * Creates a configuration property token with the specified parameters.
     *
     * @param configurationProperty the configuration property
     */
    private InstanceTemplateConfigurationPropertyToken(
        ConfigurationProperty configurationProperty) {
      this.configurationProperty = configurationProperty;
    }

    @Override
    public ConfigurationProperty unwrap() {
      return configurationProperty;
    }
  }

  /**
   * The instance name prefix.
   */
  private final String instanceNamePrefix;

  /**
   * Creates an instance template with the specified parameters.
   *
   * @param name                        the name of the template
   * @param configuration               the source of configuration
   * @param tags                        the map of tags to be applied to resources created from
   *                                    the template
   * @param providerLocalizationContext the parent provider localization context
   */
  public InstanceTemplate(String name, Configured configuration, Map<String, String> tags,
      LocalizationContext providerLocalizationContext) {
    super(name, configuration, tags, providerLocalizationContext);
    LocalizationContext localizationContext = getLocalizationContext();
    this.instanceNamePrefix =
        configuration.getConfigurationValue(INSTANCE_NAME_PREFIX, localizationContext);
  }

  /**
   * Returns the string to use as a prefix for instance names.
   *
   * @return the string to use as a prefix for instance names
   */
  public String getInstanceNamePrefix() {
    return instanceNamePrefix;
  }
}
