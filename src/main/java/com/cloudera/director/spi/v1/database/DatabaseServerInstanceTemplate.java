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
 * Represents a template for constructing cloud database server instances.
 */
public class DatabaseServerInstanceTemplate extends InstanceTemplate {

  /**
   * The list of configuration properties (including inherited properties).
   */
  private static final List<ConfigurationProperty> CONFIGURATION_PROPERTIES =
      ConfigurationPropertiesUtil.merge(
          InstanceTemplate.getConfigurationProperties(),
          ConfigurationPropertiesUtil.asConfigurationPropertyList(DatabaseServerInstanceTemplateConfigurationPropertyToken.values())
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
   * Database server instance template configuration properties.
   */
  public static enum DatabaseServerInstanceTemplateConfigurationPropertyToken implements ConfigurationPropertyToken {

    /**
     * Database type.
     */
    TYPE(new SimpleConfigurationPropertyBuilder()
        .configKey("type")
        .name("Type")
        .required(true)
        .widget(ConfigurationProperty.Widget.OPENLIST)
        .defaultDescription("The database type.")
        .defaultErrorMessage("Database type is mandatory")
        .build()),

    /**
     * Admin username.
     */
    ADMIN_USERNAME(new SimpleConfigurationPropertyBuilder()
        .configKey("adminUsername")
        .name("Admin username")
        .required(true)
        .defaultDescription("The admin user name.")
        .defaultErrorMessage("Admin username is mandatory")
        .build()),

    /**
     * Admin password.
     */
    ADMIN_PASSWORD(new SimpleConfigurationPropertyBuilder()
        .configKey("adminPassword")
        .name("Admin password")
        .required(true)
        .widget(ConfigurationProperty.Widget.PASSWORD)
        .defaultDescription("The admin password.")
        .defaultErrorMessage("Admin password is mandatory")
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
    private DatabaseServerInstanceTemplateConfigurationPropertyToken(ConfigurationProperty configurationProperty) {
      this.configurationProperty = configurationProperty;
    }

    @Override
    public ConfigurationProperty unwrap() {
      return configurationProperty;
    }
  }

  /**
   * The database type.
   */
  private final DatabaseType databaseType;

  /**
   * The admin username.
   */
  private final String adminUser;

  /**
   * The admin password.
   */
  private final String adminPassword;

  /**
   * Creates a database server instance template from the specified resource template.
   *
   * @param name             the name of the template
   * @param resourceTemplate another resource template
   */
  public DatabaseServerInstanceTemplate(String name, ResourceTemplate resourceTemplate) {
    this(name, resourceTemplate, resourceTemplate.getTags());
  }

  /**
   * Creates a database server instance template with the specified parameters.
   *
   * @param name          the name of the template
   * @param configuration the source of configuration
   * @param tags          the map of tags to be applied to resources created from the template
   */
  public DatabaseServerInstanceTemplate(String name, Configured configuration, Map<String, String> tags) {
    super(name, configuration, tags);
    this.databaseType = DatabaseType.valueOf(configuration.getConfigurationValue(DatabaseServerInstanceTemplateConfigurationPropertyToken.TYPE));
    this.adminUser = configuration.getConfigurationValue(DatabaseServerInstanceTemplateConfigurationPropertyToken.ADMIN_USERNAME);
    this.adminPassword = configuration.getConfigurationValue(DatabaseServerInstanceTemplateConfigurationPropertyToken.ADMIN_PASSWORD);
  }

  /**
   * Returns the database type.
   *
   * @return the database type
   */
  public DatabaseType getDatabaseType() {
    return databaseType;
  }

  /**
   * Returns the admin username.
   *
   * @return the admin username
   */
  public String getAdminUser() {
    return adminUser;
  }

  /**
   * Returns the admin password.
   *
   * @return the admin password
   */
  public String getAdminPassword() {
    return adminPassword;
  }
}
