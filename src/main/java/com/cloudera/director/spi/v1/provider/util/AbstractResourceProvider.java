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

package com.cloudera.director.spi.v1.provider.util;

import static com.cloudera.director.spi.v1.util.Preconditions.checkNotNull;

import com.cloudera.director.spi.v1.model.ConfigurationValidator;
import com.cloudera.director.spi.v1.model.Configured;
import com.cloudera.director.spi.v1.model.LocalizationContext;
import com.cloudera.director.spi.v1.model.Resource;
import com.cloudera.director.spi.v1.model.ResourceTemplate;
import com.cloudera.director.spi.v1.model.exception.PluginExceptionConditionAccumulator;
import com.cloudera.director.spi.v1.model.util.AbstractConfigured;
import com.cloudera.director.spi.v1.model.util.SimpleResourceTemplate;
import com.cloudera.director.spi.v1.provider.ResourceProvider;
import com.cloudera.director.spi.v1.provider.ResourceProviderMetadata;

/**
 * Abstract base class for resource provider implementations.
 */
public abstract class AbstractResourceProvider<R extends Resource<T>, T extends ResourceTemplate>
    extends AbstractConfigured implements ResourceProvider<R, T> {

  /**
   * The resource provider metadata.
   */
  private final ResourceProviderMetadata providerMetadata;

  /**
   * The localization context.
   */
  private final LocalizationContext localizationContext;

  /**
   * Creates an abstract resource provider with the specified parameters.
   *
   * @param configuration            the configuration
   * @param providerMetadata         the resource provider metadata
   * @param cloudLocalizationContext the parent localization context
   */
  public AbstractResourceProvider(Configured configuration, ResourceProviderMetadata providerMetadata,
      LocalizationContext cloudLocalizationContext) {
    super(configuration);
    this.providerMetadata = checkNotNull(providerMetadata, "providerMetadata is null");
    this.localizationContext = providerMetadata.getLocalizationContext(
        checkNotNull(cloudLocalizationContext, "cloudLocalizationContext is null"));
  }

  @Override
  public ResourceProviderMetadata getProviderMetadata() {
    return providerMetadata;
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
   * Returns the resource template localization context.
   *
   * @return the resource template localization context
   */
  public LocalizationContext getResourceTemplateLocalizationContext() {
    return SimpleResourceTemplate.getTemplateLocalizationContext(getLocalizationContext());
  }

  @Override
  public void validateResourceTemplateConfiguration(String name, Configured configuration,
      PluginExceptionConditionAccumulator accumulator) {
    LocalizationContext templateLocalizationContext = getResourceTemplateLocalizationContext();
    Configured enhancedConfiguration =
        enhanceTemplateConfiguration(name, configuration, templateLocalizationContext);
    getResourceTemplateConfigurationValidator().validate(
        name, enhancedConfiguration,
        accumulator, templateLocalizationContext
    );
  }

  /**
   * Returns template configuration which may contain additional derived configuration property
   * values in addition to the specified configuration.
   *
   * @param name                        the name of the template
   * @param configuration               the configuration to be enhanced
   * @param templateLocalizationContext the template localization context
   * @return the enhanced configuration
   */
  protected Configured enhanceTemplateConfiguration(String name, Configured configuration,
      LocalizationContext templateLocalizationContext) {
    return configuration;
  }

  /**
   * Returns the resource template configuration validator. The validator should make a best-faith
   * effort to identify configuration problems that will prevent successful resource template
   * creation.
   *
   * @return the configuration validator
   */
  protected ConfigurationValidator getResourceTemplateConfigurationValidator() {
    return getProviderMetadata().getResourceTemplateConfigurationValidator();
  }
}
