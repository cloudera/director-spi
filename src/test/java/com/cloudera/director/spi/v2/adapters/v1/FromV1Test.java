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

package com.cloudera.director.spi.v2.adapters.v1;

import com.cloudera.director.spi.v2.adapters.v1.fixtures.TestFixturesV1;
import com.cloudera.director.spi.v2.adapters.v1.fixtures.TestFixturesV2;
import com.cloudera.director.spi.v2.compute.ComputeInstance;
import com.cloudera.director.spi.v2.compute.ComputeInstanceTemplate;
import com.cloudera.director.spi.v2.compute.ComputeProvider;
import com.cloudera.director.spi.v2.database.DatabaseServerInstance;
import com.cloudera.director.spi.v2.database.DatabaseServerProvider;
import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.DisplayProperty;
import com.cloudera.director.spi.v2.model.Instance;
import com.cloudera.director.spi.v2.model.InstanceTemplate;
import com.cloudera.director.spi.v2.model.exception.AbstractPluginException;
import com.cloudera.director.spi.v2.model.exception.UnrecoverableProviderException;
import com.cloudera.director.spi.v2.model.util.SimpleConfiguration;
import com.cloudera.director.spi.v2.provider.CloudProvider;
import com.cloudera.director.spi.v2.provider.CloudProviderMetadata;
import com.cloudera.director.spi.v2.provider.InstanceProvider;
import com.cloudera.director.spi.v2.provider.Launcher;
import com.cloudera.director.spi.v2.provider.ResourceProvider;
import com.cloudera.director.spi.v2.provider.ResourceProviderMetadata;
import com.cloudera.director.spi.v2.model.ConfigurationPropertyValue;
import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.InstanceState;
import com.cloudera.director.spi.v2.model.InstanceStatus;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Maps;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.cloudera.director.spi.v2.adapters.v1.fixtures.TestFixturesV2.SIMPLE_CONFIGURATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;

/**
 * Test the adapters by verifying that V2 SPI objects are equivalent to V1 objects
 * that have been converted to V2 through the adapter.
 */
public class FromV1Test {

  private static final LocalizationContext lcontext = mock(LocalizationContext.class);

  @Test
  public void testInstanceStatus() {
    InstanceStatus v2 = TestFixturesV2.INSTANCE_STATUS_PENDING;
    InstanceStatus convertedV2 = EnumAdapter.fromV1(TestFixturesV1.INSTANCE_STATUS_PENDING);

    assertThat(convertedV2).isEqualTo(v2);
  }

  @Test
  public void testInstanceState() {
    InstanceState v2 = TestFixturesV2.INSTANCE_STATE_PENDING;
    InstanceState convertedV2 = FromV1.fromV1(TestFixturesV1.INSTANCE_STATE_PENDING);

    assertThat(convertedV2.getInstanceStatus()).isEqualTo(v2.getInstanceStatus());
    assertThat(convertedV2.getInstanceStateDescription(lcontext)).isEqualTo(
        v2.getInstanceStateDescription(lcontext)
    );
  }

  @Test
  public void testConfigured() {
    Configured v2 = SIMPLE_CONFIGURATION;
    Configured convertedV2 = FromV1.fromV1(TestFixturesV1.SIMPLE_CONFIGURATION);

    assertThat(convertedV2.getConfiguration(lcontext)).isEqualTo(v2.getConfiguration(lcontext));
  }

  @Test
  public void testConfigurationPropertyValue() {
    ConfigurationPropertyValue v2 = TestFixturesV2.CONFIGURATION_PROPERTY_VALUE;
    ConfigurationPropertyValue convertedV2 = FromV1.fromV1(TestFixturesV1.CONFIGURATION_PROPERTY_VALUE);

    assertThat(convertedV2.getValue()).isEqualTo(v2.getValue());
    assertThat(convertedV2.getLabel()).isEqualTo(v2.getLabel());
  }

  @Test
  public void testLauncher() throws InterruptedException {
    Launcher v2 = TestFixturesV2.LAUNCHER;
    Launcher convertedV2 = FromV1.fromV1(TestFixturesV1.LAUNCHER);

    CloudProviderMetadata cpmV2 = v2.getCloudProviderMetadata().get(0);
    CloudProviderMetadata cpmConvertedV2 = convertedV2.getCloudProviderMetadata().get(0);

    List<ResourceProviderMetadata> rpmListV2 = cpmV2.getResourceProviderMetadata();
    List<ResourceProviderMetadata> rpmListConvertedV2 = cpmConvertedV2.getResourceProviderMetadata();

    CloudProvider cloudProviderV2 = v2.createCloudProvider(cpmV2.getId(),
        SIMPLE_CONFIGURATION, Locale.US);
    CloudProvider cloudProviderConvertedV2 = convertedV2.createCloudProvider(cpmConvertedV2.getId(),
        SIMPLE_CONFIGURATION, Locale.US);

    for (int i = 0; i < rpmListV2.size(); i++) {
      ResourceProviderMetadata rpmV2 = rpmListV2.get(i);
      ResourceProviderMetadata rpmConvertedV2 = rpmListConvertedV2.get(i);

      checkResourceProviderMetadata(rpmV2, rpmConvertedV2);

      String id = rpmV2.getId();

      if (id.equals("test-compute-provider")) {
        ResourceProvider computeProviderV2 =
            cloudProviderV2.createResourceProvider(id, SIMPLE_CONFIGURATION);
        ResourceProvider computeProviderConvertedV2 =
            cloudProviderConvertedV2.createResourceProvider(id, SIMPLE_CONFIGURATION);
        assertThat(ComputeProvider.class).isAssignableFrom(computeProviderV2.getClass());
        assertThat(ComputeProvider.class).isAssignableFrom(computeProviderConvertedV2.getClass());
      } else if (id.equals("test-db-provider")) {
        ResourceProvider databaseServerProviderV2 =
            cloudProviderV2.createResourceProvider(id, SIMPLE_CONFIGURATION);
        ResourceProvider databaseServerProviderConvertedV2 =
            cloudProviderConvertedV2.createResourceProvider(id, SIMPLE_CONFIGURATION);
        assertThat(DatabaseServerProvider.class).isAssignableFrom(databaseServerProviderV2.getClass());
        assertThat(DatabaseServerProvider.class).isAssignableFrom(databaseServerProviderConvertedV2.getClass());
      } else {
        fail("Unexpected resource provider id: " + id);
      }
    }
  }

  @Test
  public void testDatabaseProvider() throws InterruptedException {
    DatabaseServerProvider v2 = TestFixturesV2.DATABASE_SERVER_PROVIDER;
    DatabaseServerProvider convertedV2 = DatabaseServerProviderAdapter.fromV1(
        TestFixturesV1.DATABASE_SERVER_PROVIDER);

    checkResourceProviderMetadata(convertedV2.getProviderMetadata(), v2.getProviderMetadata());

    Map<String, String> configs = new HashMap<String, String>();
    configs.put("type", "MYSQL");
    configs.put("adminUsername", "admin");
    configs.put("adminPassword", "password");

    testInstanceProvider(v2, convertedV2, configs, DatabaseServerInstance.class);
  }

  @Test
  public void testComputeProvider() throws InterruptedException {
    ComputeProvider v2 = TestFixturesV2.COMPUTE_PROVIDER;
    ComputeProvider convertedV2 = ComputeProviderAdapter.fromV1(TestFixturesV1.COMPUTE_PROVIDER);

    checkResourceProviderMetadata(convertedV2.getProviderMetadata(), v2.getProviderMetadata());

    Map<String, String> configs = new HashMap<String, String>();
    testInstanceProvider(v2, convertedV2, configs, ComputeInstance.class);
  }

  @SuppressWarnings("unchecked")
  private void testInstanceProvider(InstanceProvider instanceProviderV2,
                                    InstanceProvider instanceProviderConvertedV2,
                                    Map<String, String> configs, Class<?> expectedInstanceClass)
      throws InterruptedException {
    Map<String, String> tags = Maps.newHashMap();
    tags.put("key1", "val1");
    tags.put("key2", "val2");
    new SimpleConfiguration(configs);

    InstanceTemplate templateV2 = (InstanceTemplate) instanceProviderV2.createResourceTemplate(
        "template", new SimpleConfiguration(configs), tags);
    InstanceTemplate convertedTemplateV2 = (InstanceTemplate) instanceProviderConvertedV2.createResourceTemplate(
        "template", new SimpleConfiguration(configs), tags);

    List<String> resourceIds = Lists.newArrayList("id1", "id2", "id3");

    // test allocation

    instanceProviderV2.allocate(templateV2, resourceIds, resourceIds.size());
    instanceProviderConvertedV2.allocate(convertedTemplateV2, resourceIds, resourceIds.size());

    // test find

    Collection<Instance> resultsV2 = instanceProviderV2.find(templateV2, resourceIds);
    Collection<Instance> convertedResultsV2 = instanceProviderConvertedV2.find(convertedTemplateV2, resourceIds);
    assertThat(convertedResultsV2).hasSize(resourceIds.size());
    checkInstances(convertedResultsV2, resultsV2, expectedInstanceClass);

    Map<String, InstanceState> instanceStatesV2 = instanceProviderV2.getInstanceState(templateV2, resourceIds);
    Map<String, InstanceState> convertedInstanceStatesV2 = instanceProviderV2.getInstanceState(templateV2, resourceIds);
    checkInstanceState(instanceStatesV2, convertedInstanceStatesV2);

    // test host key fingerprint retrieval for compute provider

    if (expectedInstanceClass.isAssignableFrom(ComputeProvider.class)) {
      ComputeProvider computeProviderV2 = (ComputeProvider) instanceProviderV2;
      ComputeProvider computeProviderConvertedV2 = (ComputeProvider) instanceProviderConvertedV2;

      Map<String, List<String>> hostKeyFingerprintsV2 = computeProviderV2
          .getHostKeyFingerprints((ComputeInstanceTemplate) templateV2, resourceIds);
      Map<String, List<String>> hostKeyFingerprintsConvertedV2 = computeProviderConvertedV2
          .getHostKeyFingerprints((ComputeInstanceTemplate) convertedTemplateV2, resourceIds);

      assertThat(hostKeyFingerprintsV2).hasSameSizeAs(resourceIds);
      assertThat(hostKeyFingerprintsConvertedV2).isEmpty();
    }

    // test delete

    instanceProviderV2.delete(templateV2, resourceIds);
    instanceProviderConvertedV2.delete(convertedTemplateV2, resourceIds);

    resultsV2 = instanceProviderV2.find(templateV2, resourceIds);
    convertedResultsV2 = instanceProviderV2.find(templateV2, resourceIds);
    assertThat(resultsV2).hasSize(0);
    assertThat(convertedResultsV2).hasSize(0);

    // test that exceptions get converted

    try {
      instanceProviderConvertedV2.allocate(convertedTemplateV2, resourceIds, -1);
    } catch(RuntimeException ex) {
      assertThat(AbstractPluginException.class.isAssignableFrom(ex.getClass()));
      assertThat(UnrecoverableProviderException.class).isEqualTo(ex.getClass());
    }
  }

  private void checkResourceProviderMetadata(ResourceProviderMetadata rpm1, ResourceProviderMetadata rpm2) {
    checkDisplayProperty(rpm1.getResourceDisplayProperties(), rpm2.getResourceDisplayProperties());
    checkConfigurationProperty(rpm1.getResourceTemplateConfigurationProperties(),
        rpm2.getResourceTemplateConfigurationProperties());
  }

  private void checkInstances(Collection<Instance> instances1, Collection<Instance> instances2,
                              Class<?> expectedResourceClass) {
    assertThat(instances1).hasSameSizeAs(instances2);

    List<Instance> resourceList1 = Lists.newArrayList(instances1);
    List<Instance> resourceList2 = Lists.newArrayList(instances2);

    for (int i = 0; i < resourceList1.size(); i++) {
      Instance instance1 = resourceList1.get(i);
      Instance instance2 = resourceList2.get(i);

      assertThat(instance1.getId()).isEqualTo(instance2.getId());
      assertThat(instance1.getProperties()).isEqualTo(instance2.getProperties());
      assertThat(instance1.getType().getDescription(lcontext)).isEqualTo(instance2.getType().getDescription(lcontext));
      assertThat(instance1.getDescription(lcontext)).isEqualTo(instance2.getDescription(lcontext));

      assertThat(expectedResourceClass.isAssignableFrom(instance1.getClass()));
      assertThat(expectedResourceClass.isAssignableFrom(instance2.getClass()));

      if (expectedResourceClass == DatabaseServerInstance.class) {
        DatabaseServerInstance dbInstance1 = (DatabaseServerInstance) instance1;
        DatabaseServerInstance dbInstance2 = (DatabaseServerInstance) instance2;
        assertThat(dbInstance1.getPort()).isEqualTo(dbInstance2.getPort());
      }

      if (expectedResourceClass == ComputeInstance.class) {
        ComputeInstance computeInstance1 = (ComputeInstance) instance1;
        ComputeInstance computeInstance2 = (ComputeInstance) instance2;
        assertThat(computeInstance1.getVirtualizationType()).isEqualTo(computeInstance2.getVirtualizationType());
      }
    }
  }

  private void checkDisplayProperty(List<DisplayProperty> displayPropertyList1,
                                    List<DisplayProperty> displayPropertyList2) {
    for (int i = 0; i < displayPropertyList1.size(); i++) {
      DisplayProperty d1 = displayPropertyList1.get(i);
      DisplayProperty d2 = displayPropertyList2.get(i);
      assertThat(d1.getDisplayKey()).isEqualTo(d2.getDisplayKey());
    }
  }

  private void checkConfigurationProperty(List<ConfigurationProperty> configurationPropertyList1,
                                          List<ConfigurationProperty> configurationPropertyList2) {
    for (int i = 0; i < configurationPropertyList1.size(); i++) {
      ConfigurationProperty c1 = configurationPropertyList1.get(i);
      ConfigurationProperty c2 = configurationPropertyList2.get(i);

      assertThat(c1.getConfigKey()).isEqualTo(c2.getConfigKey());
      assertThat(c1.getDefaultValue()).isEqualTo(c2.getDefaultValue());
      assertThat(c1.getMissingValueErrorMessage(lcontext)).isEqualTo(c2.getMissingValueErrorMessage(lcontext));
      checkConfigurationPropertyValue(c1.getValidValues(lcontext), c2.getValidValues(lcontext));
    }
  }

  private void checkConfigurationPropertyValue(List<ConfigurationPropertyValue> configurationPropertyValueList1,
                                               List<ConfigurationPropertyValue> configurationPropertyValueList2) {
    for (int i = 0; i < configurationPropertyValueList1.size(); i++) {
      ConfigurationPropertyValue c1 = configurationPropertyValueList1.get(i);
      ConfigurationPropertyValue c2 = configurationPropertyValueList2.get(i);
      assertThat(c1.getLabel()).isEqualTo(c2.getLabel());
      assertThat(c1.getValue()).isEqualTo(c2.getValue());
    }
  }

  private void checkInstanceState(Map<String, InstanceState> instanceStateMap1,
                                  Map<String, InstanceState> instanceStateMap2) {
    assertThat(instanceStateMap1).hasSize(instanceStateMap2.size());
    for (String key : instanceStateMap1.keySet()) {
      InstanceState val1 = instanceStateMap1.get(key);
      InstanceState val2 = instanceStateMap2.get(key);
      assertThat(val1.getInstanceStateDescription(lcontext)).isEqualTo(val2.getInstanceStateDescription(lcontext));
      assertThat(val1.getInstanceStatus()).isEqualTo(val2.getInstanceStatus());
    }
  }
}
