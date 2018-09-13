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

package com.cloudera.director.spi.v2.compute;

import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.InstanceTemplate;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.util.SimpleConfigurationPropertyBuilder;
import com.cloudera.director.spi.v2.util.ConfigurationPropertiesUtil;

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
          ConfigurationPropertiesUtil.asConfigurationPropertyList(
              ComputeInstanceTemplateConfigurationPropertyToken.values())
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
  // Fully qualifying class name due to compiler bug
  public static enum ComputeInstanceTemplateConfigurationPropertyToken
      implements com.cloudera.director.spi.v2.model.ConfigurationPropertyToken {

    /**
     * The provider-specific image ID.
     */
    IMAGE(new SimpleConfigurationPropertyBuilder()
        .configKey("image")
        .name("Image ID")
        .required(true)
        .widget(ConfigurationProperty.Widget.OPENLIST)
        .defaultDescription("The image ID.")
        .defaultPlaceholder("Select an image")
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
        .defaultPlaceholder("Select an instance type")
        .defaultErrorMessage("Instance type is mandatory")
        .build()),

    /**
     * Whether instances created from this template are part of an automatic instance group.
     */
    AUTOMATIC(new SimpleConfigurationPropertyBuilder()
        .configKey("automatic")
        .name("Automatic")
        .required(false)
        .widget(ConfigurationProperty.Widget.CHECKBOX)
        .defaultDescription(
            "Whether instances created from this template are part of an automatic instance group.")
        .hidden(true)
        .build()),

    /**
     * The SSH username.
     */
    SSH_USERNAME(new SimpleConfigurationPropertyBuilder()
        .configKey("sshUsername")
        .name("SSH username")
        .required(false)
        .widget(ConfigurationProperty.Widget.TEXT)
        .defaultDescription("The SSH username.")
        .hidden(true)
        .build()),

    /**
     * The SSH password.
     */
    SSH_PASSWORD(new SimpleConfigurationPropertyBuilder()
        .configKey("sshPassword")
        .name("SSH password")
        .required(false)
        .widget(ConfigurationProperty.Widget.PASSWORD)
        .defaultDescription("The SSH password.")
        .hidden(true)
        .build()),

    /**
     * The SSH public key.
     */
    SSH_JCE_PUBLIC_KEY(new SimpleConfigurationPropertyBuilder()
        .configKey("sshJcePublicKey")
        .name("SSH JCE public key")
        .required(false)
        .widget(ConfigurationProperty.Widget.TEXT)
        .defaultDescription("The SSH public key, as a Base64-encoded serialized PublicKey.")
        .hidden(true)
        .build()),

    /**
     * The SSH public key, in OpenSSH format ("ssh-rsa key-data comment"),
     * similar to RFC 4716.
     */
    SSH_OPENSSH_PUBLIC_KEY(new SimpleConfigurationPropertyBuilder()
        .configKey("sshOpensshPublicKey")
        .name("SSH OpenSSH public key")
        .required(false)
        .widget(ConfigurationProperty.Widget.TEXT)
        .defaultDescription("The SSH public key, in OpenSSH format.")
        .hidden(true)
        .build()),

    /**
     * The SSH private key.
     */
    SSH_JCE_PRIVATE_KEY(new SimpleConfigurationPropertyBuilder()
        .configKey("sshJcePrivateKey")
        .name("SSH JCE private key")
        .required(false)
        .widget(ConfigurationProperty.Widget.TEXT)
        .defaultDescription("The SSH private key, as a Base64-encoded serialized PrivateKey.")
        .hidden(true)
        .build()),

    /**
     * The SSH private key data in its original form.
     */
    SSH_PRIVATE_KEY(new SimpleConfigurationPropertyBuilder()
        .configKey("sshPrivateKey")
        .name("SSH private key")
        .required(false)
        .widget(ConfigurationProperty.Widget.TEXT)
        .defaultDescription("The SSH private key data, in its original form.")
        .hidden(true)
        .build()),

    /**
     * The SSH port.
     */
    SSH_PORT(new SimpleConfigurationPropertyBuilder()
        .configKey("sshPort")
        .name("SSH port")
        .required(true)
        .widget(ConfigurationProperty.Widget.NUMBER)
        .defaultDescription("The SSH port.")
        .defaultErrorMessage("SSH port is mandatory")
        .hidden(true)
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
    private ComputeInstanceTemplateConfigurationPropertyToken(
        ConfigurationProperty configurationProperty) {
      this.configurationProperty = configurationProperty;
    }

    @Override
    public ConfigurationProperty unwrap() {
      return configurationProperty;
    }
  }

  /**
   * Whether instances created from this template are part of an automatic instance group.
   */
  private final boolean automatic;

  /**
   * Creates a compute instance template with the specified parameters.
   *
   * @param name                        the name of the template
   * @param configuration               the source of configuration
   * @param tags                        the map of tags to be applied to resources created from the template
   * @param providerLocalizationContext the parent provider localization context
   */
  public ComputeInstanceTemplate(String name, Configured configuration, Map<String, String> tags,
      LocalizationContext providerLocalizationContext) {
    super(name, configuration, tags, providerLocalizationContext);
    LocalizationContext localizationContext = getLocalizationContext();
    this.automatic = Boolean.parseBoolean(getConfigurationValue(
        ComputeInstanceTemplateConfigurationPropertyToken.AUTOMATIC, localizationContext));
  }

  /**
   * Returns whether instances created from this template are part of an automatic instance group.
   *
   * @return whether instances created from this template are part of an automatic instance group
   */
  public boolean isAutomatic() {
    return automatic;
  }
}
