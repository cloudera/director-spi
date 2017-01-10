// (c) Copyright 2017 Cloudera, Inc.
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

package com.cloudera.director.spi.v2.adapters.v1.fixtures;

import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.provider.CloudProvider;
import com.cloudera.director.spi.v2.provider.CloudProviderMetadata;
import com.cloudera.director.spi.v2.provider.CredentialsProviderMetadata;
import com.cloudera.director.spi.v2.provider.ResourceProvider;
import com.cloudera.director.spi.v2.provider.ResourceProviderMetadata;
import com.cloudera.director.spi.v2.provider.util.AbstractCloudProvider;
import com.cloudera.director.spi.v2.provider.util.AbstractLauncher;
import com.cloudera.director.spi.v2.provider.util.SimpleCloudProviderMetadataBuilder;
import org.assertj.core.util.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.mock;

/**
 * Contains test cloud provider classes that implement V2 of the SPI.
 */
public class TestCloudProviderV2 {

  public static final CloudProviderMetadata CLOUD_PROVIDER_METADATA = new SimpleCloudProviderMetadataBuilder()
      .id("test")
      .name("Test Cloud Provider")
      .description("Test Cloud Provider Description")
      .configurationProperties(Collections.<ConfigurationProperty>emptyList())
      .credentialsProviderMetadata(mock(CredentialsProviderMetadata.class))
      .resourceProviderMetadata(Lists.newArrayList(
          TestFixturesV2.COMPUTE_PROVIDER_METADATA,
          TestFixturesV2.DATABASE_PROVIDER_METADATA))
      .build();

  public static final class TestProvider extends AbstractCloudProvider {

    public TestProvider(CloudProviderMetadata providerMetadata, LocalizationContext rootLocalizationContext) {
      super(providerMetadata, rootLocalizationContext);
    }

    @Override
    public ResourceProvider createResourceProvider(String resourceProviderId, Configured configuration) {
      ResourceProviderMetadata resourceProviderMetadata =
          getProviderMetadata().getResourceProviderMetadata(resourceProviderId);
      if (resourceProviderMetadata.getId().equals(TestFixturesV2.COMPUTE_PROVIDER_METADATA.getId())) {
        return TestFixturesV2.COMPUTE_PROVIDER;
      } else if (resourceProviderId.equals(TestFixturesV2.DATABASE_PROVIDER_METADATA.getId())) {
        return TestFixturesV2.DATABASE_SERVER_PROVIDER;
      }
      throw new IllegalStateException("Unknown provider " + resourceProviderMetadata.getId());
    }
  }

  public static final class TestLauncher extends AbstractLauncher {
    protected TestLauncher(List<CloudProviderMetadata> cloudProviderMetadata,
                           LocalizationContext.Factory localizationContextFactory) {
      super(cloudProviderMetadata, localizationContextFactory);
    }

    @Override
    public CloudProvider createCloudProvider(String cloudProviderId, Configured configuration, Locale locale) {
      return new TestProvider(getCloudProviderMetadata(cloudProviderId), getLocalizationContext(locale));
    }
  }
}
