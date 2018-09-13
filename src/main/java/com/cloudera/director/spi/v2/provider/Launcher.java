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

package com.cloudera.director.spi.v2.provider;

import com.cloudera.director.spi.v2.common.http.HttpProxyParameters;
import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.util.DefaultLocalizationContext;

import java.io.File;
import java.util.List;
import java.util.Locale;

/**
 * <p>Cloud provider plugin launcher.</p>
 * <p>This class provides the entry point from Cloudera Altus Director to the cloud provider plugin
 * implementation.</p>
 * <p>The plugin must be packaged as a single jar, with a provider-configuration file
 * {@code META-INF/services/com.cloudera.director.spi.v2.provider.Launcher} containing
 * the fully-qualified name of a class which implements this interface and has a no-argument
 * public constructor.</p>
 * <p>Cloudera Altus Director uses Java's service loading mechanism to instantiate the class via
 * reflection, then invokes the {@link #initialize(File, HttpProxyParameters)} method before
 * requesting any providers.</p>
 */
public interface Launcher {

  /**
   * The default plugin localization context.
   */
  LocalizationContext DEFAULT_PLUGIN_LOCALIZATION_CONTEXT =
      DefaultLocalizationContext.FACTORY.createRootLocalizationContext(Locale.getDefault());

  /**
   * Initializes the cloud provider plugin. The plugin is guaranteed read and execute access to
   * the specified plugin-specific configuration directory to allow for additional filesystem
   * configuration by the end user.
   *
   * @param configurationDirectory the configuration directory
   * @param httpProxyParameters    the HTTP proxy parameters
   */
  void initialize(File configurationDirectory, HttpProxyParameters httpProxyParameters);

  /**
   * Returns cloud provider metadata for all supported cloud providers.
   *
   * @return resource provider metadata for all supported cloud providers
   */
  List<CloudProviderMetadata> getCloudProviderMetadata();

  /**
   * Returns the specified cloud provider, using the specified configuration.
   *
   * @param cloudProviderId the cloud provider ID, as returned by its metadata
   * @param configuration   the configuration
   * @param locale          the locale
   * @return the specified cloud provider, using the specified configuration
   * if it has not already been initialized
   */
  CloudProvider createCloudProvider(String cloudProviderId, Configured configuration,
      Locale locale);

  /**
   * Returns the localization context for the specified locale.
   *
   * @param locale the locale
   * @return the localization context for the specified locale
   */
  LocalizationContext getLocalizationContext(Locale locale);
}
