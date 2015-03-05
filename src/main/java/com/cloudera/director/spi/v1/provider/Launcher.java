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

import com.cloudera.director.spi.v1.model.Configured;

import java.io.File;
import java.util.List;

/**
 * <p>Cloud provider plugin launcher.</p>
 * <p/>
 * <p>This class provides the entry point from Cloudera Director to the cloud provider plugin implementation.
 * The plugin must be packaged as a single jar whose manifest includes the properties
 * <code>com.cloudera.director.provider.launcher-class</code> whose value is the fully-qualified name of a
 * class which implements this interface and has a no-argument public constructor, and
 * <code>com.cloudera.director.provider.spi-versions</code>, whose value is the supported SPI version (<em>e.g.</em> v1).
 * Cloudera Director instantiates the class via reflection and invokes the {@link #initialize(java.io.File)} method
 * before requesting any providers.</p>
 */
public interface Launcher {

  /**
   * Initializes the cloud provider plugin. The plugin is guaranteed read and execute access to the specified
   * plugin-specific configuration directory to allow for additional filesystem configuration by the end user.
   *
   * @param configurationDirectory the configuration directory
   */
  void initialize(File configurationDirectory);

  /**
   * Returns cloud provider metadata for all supported cloud providers.
   *
   * @return resource provider metadata for all supported cloud providers
   */
  List<CloudProviderMetadata> getCloudProviderMetadata();

  /**
   * Returns the specified cloud provider, using the specified configuration
   * if it has not already been initialized.
   *
   * @param cloudProviderId the cloud provider ID, as returned by its metadata
   * @param configuration   the configuration
   * @return the specified cloud provider, using the specified configuration
   * if it has not already been initialized
   */
  CloudProvider getCloudProvider(String cloudProviderId, Configured configuration);
}
