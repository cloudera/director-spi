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

import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.ConfigurationValidator;
import com.cloudera.director.spi.v2.model.DisplayProperty;

import java.util.List;

/**
 * Provider metadata about a resource provider.
 */
public interface ResourceProviderMetadata extends ProviderMetadata {

  /**
   * Returns the resource provider class.
   *
   * @return the resource provider class
   */
  Class<? extends ResourceProvider<?, ?>> getProviderClass();

  /**
   * Returns the list of properties needed to create a new resource template.
   *
   * @return the list of properties needed to create a new resource template
   */
  List<ConfigurationProperty> getResourceTemplateConfigurationProperties();

  /**
   * Returns the list of display properties exposed by a resource.
   *
   * @return the list of display properties exposed by a resource
   */
  List<DisplayProperty> getResourceDisplayProperties();

  /**
   * Returns the resource template configuration validator. The validator should make a best-faith
   * effort to identify configuration problems that will prevent successful resource template
   * creation.
   *
   * @return the configuration validator
   */
  ConfigurationValidator getResourceTemplateConfigurationValidator();
}
