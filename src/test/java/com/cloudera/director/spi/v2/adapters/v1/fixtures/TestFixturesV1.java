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

import com.cloudera.director.spi.v1.compute.ComputeProvider;
import com.cloudera.director.spi.v1.database.DatabaseServerProvider;
import com.cloudera.director.spi.v1.database.DatabaseType;
import com.cloudera.director.spi.v1.database.util.SimpleDatabaseServerProviderMetadata;
import com.cloudera.director.spi.v1.model.ConfigurationProperty;
import com.cloudera.director.spi.v1.model.ConfigurationPropertyValue;
import com.cloudera.director.spi.v1.model.Configured;
import com.cloudera.director.spi.v1.model.DisplayProperty;
import com.cloudera.director.spi.v1.model.InstanceState;
import com.cloudera.director.spi.v1.model.InstanceStatus;
import com.cloudera.director.spi.v1.model.util.DefaultLocalizationContext;
import com.cloudera.director.spi.v1.model.util.SimpleConfiguration;
import com.cloudera.director.spi.v1.model.util.SimpleConfigurationPropertyBuilder;
import com.cloudera.director.spi.v1.model.util.SimpleConfigurationPropertyValue;
import com.cloudera.director.spi.v1.model.util.SimpleDisplayPropertyBuilder;
import com.cloudera.director.spi.v1.model.util.SimpleInstanceState;
import com.cloudera.director.spi.v1.model.LocalizationContext;
import com.cloudera.director.spi.v1.provider.ResourceProviderMetadata;
import com.cloudera.director.spi.v1.provider.util.SimpleResourceProviderMetadata;

import com.cloudera.director.spi.v2.adapters.v1.fixtures.TestCloudProviderV1.TestLauncher;
import com.cloudera.director.spi.v2.adapters.v1.fixtures.TestComputeProviderV1.TestComputeInstance;
import com.cloudera.director.spi.v2.adapters.v1.fixtures.TestComputeProviderV1.TestComputeInstanceTemplate;
import com.cloudera.director.spi.v2.adapters.v1.fixtures.TestComputeProviderV1.TestComputeProvider;
import com.cloudera.director.spi.v2.adapters.v1.fixtures.TestDatabaseProviderV1.TestDatabaseServerInstance;
import com.cloudera.director.spi.v2.adapters.v1.fixtures.TestDatabaseProviderV1.TestDatabaseServerInstanceTemplate;
import com.cloudera.director.spi.v2.adapters.v1.fixtures.TestDatabaseProviderV1.TestDatabaseServerProvider;
import org.assertj.core.util.Lists;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static com.cloudera.director.spi.v2.adapters.v1.fixtures.TestCloudProviderV1.CLOUD_PROVIDER_METADATA;

/**
 * Contains objects that implement SPI V1. The data in these objects should
 * be equivalent to the objects in {@link TestFixturesV2}.
 */
public class TestFixturesV1 {

  public static final InstanceStatus INSTANCE_STATUS_PENDING = InstanceStatus.PENDING;

  public static final InstanceState INSTANCE_STATE_PENDING = new SimpleInstanceState(INSTANCE_STATUS_PENDING);

  public static final Configured SIMPLE_CONFIGURATION;

  static {
    final Map<String, String> config = new HashMap<String, String>();
    config.put("k1", "val1");
    config.put("k2", "val2");
    SIMPLE_CONFIGURATION = new SimpleConfiguration(config);
  }

  public static final Configured DATABASE_SERVER_CONFIGURATION;
  static {
    final Map<String, String> config = new HashMap<String, String>();
    config.put("type", "MYSQL");
    config.put("adminUsername", "admin");
    config.put("adminPassword", "password");
    DATABASE_SERVER_CONFIGURATION = new SimpleConfiguration(config);
  }

  public static final LocalizationContext LOCALIZATION_CONTEXT = new DefaultLocalizationContext(Locale.US, "en");

  public static final ConfigurationPropertyValue CONFIGURATION_PROPERTY_VALUE
      = new SimpleConfigurationPropertyValue("val1", "label1");

  public static final ConfigurationProperty SIMPLE_CONFIGURATION_PROPERTY = new SimpleConfigurationPropertyBuilder()
      .configKey("configKey")
      .name("Test Configuration Property")
      .defaultDescription("Test Description")
      .widget(ConfigurationProperty.Widget.TEXT)
      .build();

  public static final DisplayProperty SIMPLE_DISPLAY_PROPERTY = new SimpleDisplayPropertyBuilder()
      .displayKey("testDisplayKey")
      .name("Test Display Property")
      .defaultDescription("Test Description")
      .sensitive(false)
      .build();

  // test compute provider

  public static final ResourceProviderMetadata COMPUTE_PROVIDER_METADATA = SimpleResourceProviderMetadata.builder()
      .id("test-compute-provider")
      .name("Test-Compute-Provider")
      .description("Test Compute Provider")
      .providerClass(TestComputeProvider.class)
      .providerConfigurationProperties(Collections.singletonList(SIMPLE_CONFIGURATION_PROPERTY))
      .resourceTemplateConfigurationProperties(Collections.singletonList(SIMPLE_CONFIGURATION_PROPERTY))
      .resourceDisplayProperties(Collections.singletonList(SIMPLE_DISPLAY_PROPERTY))
      .build();

  public static final ComputeProvider<TestComputeInstance, TestComputeInstanceTemplate> COMPUTE_PROVIDER =
      new TestComputeProvider(SIMPLE_CONFIGURATION, COMPUTE_PROVIDER_METADATA,
          LOCALIZATION_CONTEXT);

  // test database provider

  private static final Set<DatabaseType> SUPPORTED_DB_TYPES = new HashSet<DatabaseType>();
  static {
    SUPPORTED_DB_TYPES.add(DatabaseType.MYSQL);
  }

  public static final ResourceProviderMetadata DATABASE_PROVIDER_METADATA =
      SimpleDatabaseServerProviderMetadata.databaseServerProviderMetadataBuilder()
          .id("test-db-provider")
          .name("Test-DB-Provider")
          .description("Test DB Provider")
          .providerClass(TestDatabaseServerProvider.class)
          .providerConfigurationProperties(Collections.singletonList(SIMPLE_CONFIGURATION_PROPERTY))
          .resourceTemplateConfigurationProperties(Collections.singletonList(SIMPLE_CONFIGURATION_PROPERTY))
          .resourceDisplayProperties(Collections.singletonList(SIMPLE_DISPLAY_PROPERTY))
          .supportedDatabaseTypes(SUPPORTED_DB_TYPES)
          .build();

  public static final DatabaseServerProvider<TestDatabaseServerInstance, TestDatabaseServerInstanceTemplate>
      DATABASE_SERVER_PROVIDER = new TestDatabaseServerProvider(SIMPLE_CONFIGURATION, DATABASE_PROVIDER_METADATA,
          LOCALIZATION_CONTEXT);

  // test cloud provider launcher

  public static final TestLauncher LAUNCHER = new TestLauncher(
      Lists.newArrayList(CLOUD_PROVIDER_METADATA), DefaultLocalizationContext.FACTORY);
}
