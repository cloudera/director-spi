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

package com.cloudera.director.spi.v2.provider.util;

import com.cloudera.director.spi.v2.common.http.HttpProxyParameters;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.util.DefaultLocalizationContext;
import com.cloudera.director.spi.v2.provider.CloudProviderMetadata;
import com.cloudera.director.spi.v2.provider.Launcher;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

/**
 * Abstract base class for launcher implementations.
 */
public abstract class AbstractLauncher implements Launcher {

  /**
   * The metadata about the supported cloud providers.
   */
  private final List<CloudProviderMetadata> cloudProviderMetadata;

  /**
   * The localization context factory.
   */
  private final LocalizationContext.Factory localizationContextFactory;

  /**
   * Creates an abstract launcher with the specified parameters.
   *
   * @param cloudProviderMetadata      the metadata about the supported cloud providers
   * @param localizationContextFactory the localization context factory, or {@code null}
   *                                   to use the default
   */
  protected AbstractLauncher(List<CloudProviderMetadata> cloudProviderMetadata, LocalizationContext.Factory localizationContextFactory) {
    if (cloudProviderMetadata == null) {
      throw new NullPointerException("cloudProviderMetadata is null");
    }
    if (cloudProviderMetadata.isEmpty()) {
      throw new IllegalArgumentException("No supported cloud providers.");
    }
    this.cloudProviderMetadata = Collections.unmodifiableList(
        new ArrayList<CloudProviderMetadata>(cloudProviderMetadata));

    this.localizationContextFactory = (localizationContextFactory == null)
        ? DefaultLocalizationContext.FACTORY
        : localizationContextFactory;
  }

  @Override
  public void initialize(File configurationDirectory, HttpProxyParameters httpProxyParameters) {
  }

  @Override
  public List<CloudProviderMetadata> getCloudProviderMetadata() {
    return cloudProviderMetadata;
  }

  protected CloudProviderMetadata getCloudProviderMetadata(String providerId) {
    for (CloudProviderMetadata candidate : getCloudProviderMetadata()) {
      if (candidate.getId().equals(providerId)) {
        return candidate;
      }
    }

    throw new NoSuchElementException(
        String.format("Cloud provider metadata not found for: %s", providerId));
  }

  @Override
  public LocalizationContext getLocalizationContext(Locale locale) {
    return localizationContextFactory.createRootLocalizationContext(locale);
  }
}
