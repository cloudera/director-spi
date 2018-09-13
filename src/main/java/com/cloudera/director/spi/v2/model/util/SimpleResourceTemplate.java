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

package com.cloudera.director.spi.v2.model.util;

import static com.cloudera.director.spi.v2.util.Preconditions.checkNotNull;

import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.ResourceTemplate;
import com.cloudera.director.spi.v2.util.ConfigurationPropertiesUtil;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Base resource template implementation.
 */
public class SimpleResourceTemplate extends AbstractConfigured implements ResourceTemplate {

  /**
   * The list of configuration properties (including inherited properties).
   */
  private static final List<ConfigurationProperty> CONFIGURATION_PROPERTIES =
      ConfigurationPropertiesUtil.asConfigurationPropertyList(
          SimpleResourceTemplateConfigurationPropertyToken.values());

  /**
   * Returns the list of configuration properties for creating a resource template.
   *
   * @return the list of configuration properties for creating a resource template
   */
  public static List<ConfigurationProperty> getConfigurationProperties() {
    return CONFIGURATION_PROPERTIES;
  }

  /**
   * Simple resource template configuration property tokens.
   */
  // Fully qualifying class name due to compiler bug
  public static enum SimpleResourceTemplateConfigurationPropertyToken
      implements com.cloudera.director.spi.v2.model.ConfigurationPropertyToken {

    /**
     * Resource name.
     */
    NAME(new SimpleConfigurationPropertyBuilder()
        .configKey("name")
        .name("Name")
        .defaultDescription("The name of the resource.")
        .hidden(true)
        .build()),

    /**
     * The unique ID of the group containing instances created from this template.
     */
    GROUP_ID(new SimpleConfigurationPropertyBuilder()
        .configKey("group")
        .name("Group ID")
        .required(false)
        .widget(ConfigurationProperty.Widget.TEXT)
        .defaultDescription(
            "The unique ID of the group containing instances created from this template.")
        .hidden(true)
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
    private SimpleResourceTemplateConfigurationPropertyToken(
        ConfigurationProperty configurationProperty) {
      this.configurationProperty = configurationProperty;
    }

    @Override
    public ConfigurationProperty unwrap() {
      return configurationProperty;
    }
  }

  /**
   * Returns the template localization context for the specified provider localization context.
   *
   * @param providerLocalizationContext the parent provider localization context
   * @return the template localization context
   */
  public static ChildLocalizationContext getTemplateLocalizationContext(
      LocalizationContext providerLocalizationContext) {
    return new ChildLocalizationContext(providerLocalizationContext, "template");
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
   * The localization context.
   */
  private final LocalizationContext localizationContext;

  /**
   * The unique ID of the group containing resources created from this template.
   */
  private final String groupId;

  /**
   * Creates a base resource template with the specified parameters.
   *
   * @param name                        the name of the template
   * @param configuration               the source of configuration
   * @param tags                        the map of tags to be applied to resources created from
   *                                    the template
   * @param providerLocalizationContext the parent provider localization context
   */
  public SimpleResourceTemplate(String name, Configured configuration, Map<String, String> tags,
      LocalizationContext providerLocalizationContext) {
    super(configuration);

    this.name = checkNotNull(name, "name is null");
    this.tags = (tags == null) ? Collections.<String, String>emptyMap() :
        Collections.unmodifiableMap(new LinkedHashMap<String, String>(tags));
    this.localizationContext = getTemplateLocalizationContext(providerLocalizationContext);
    this.groupId = getConfigurationValue(
        SimpleResourceTemplateConfigurationPropertyToken.GROUP_ID, localizationContext);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Map<String, String> getTags() {
    return tags;
  }

  @Override
  public Object unwrap() {
    return null;
  }

  /**
   * Returns the localization context.
   *
   * @return the localization context
   */
  public LocalizationContext getLocalizationContext() {
    return localizationContext;
  }

  /**
   * Returns the unique ID of the group containing instances created from this template.
   *
   * @return the unique ID of the group containing instances created from this template
   */
  @Override
  public String getGroupId() {
    return groupId;
  }
}
