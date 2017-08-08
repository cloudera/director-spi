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

import static org.mockito.Mockito.mock;

import com.cloudera.director.spi.v2.database.DatabaseServerInstance;
import com.cloudera.director.spi.v2.database.DatabaseServerInstanceTemplate;
import com.cloudera.director.spi.v2.database.util.AbstractDatabaseServerInstance;
import com.cloudera.director.spi.v2.database.util.AbstractDatabaseServerProvider;
import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.InstanceState;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.Resource;
import com.cloudera.director.spi.v2.provider.ResourceProviderMetadata;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.net.InetAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains test database provider classes that implement V2 of the SPI.
 */
public class TestDatabaseProviderV2 {

  static final class TestDatabaseServerInstanceTemplate extends DatabaseServerInstanceTemplate {
    public TestDatabaseServerInstanceTemplate(String name, Configured configuration, Map<String, String> tags,
                                              LocalizationContext localizationContext) {
      super(name, configuration, tags, localizationContext);
    }
  }

  static final class TestDatabaseServerInstance
      extends AbstractDatabaseServerInstance<TestDatabaseServerInstanceTemplate, Object>
      implements DatabaseServerInstance<TestDatabaseServerInstanceTemplate> {
    protected TestDatabaseServerInstance(TestDatabaseServerInstanceTemplate template, String instanceId,
                                         InetAddress privateIpAddress, Integer port) {
      super(template, instanceId, privateIpAddress, port);
    }

    @Override
    public Map<String, String> getProperties() {
      Map<String, String> result = new HashMap<String, String>();
      result.put("dbProp1", "dbVal1");
      return result;
    }
  }

  static final class TestDatabaseServerProvider
      extends AbstractDatabaseServerProvider<TestDatabaseServerInstance,TestDatabaseServerInstanceTemplate> {

    Map<String, TestDatabaseServerInstance> dbInstances = Maps.newHashMap();

    public TestDatabaseServerProvider(Configured configuration, ResourceProviderMetadata providerMetadata,
                                      LocalizationContext localizationContext) {
      super(configuration, providerMetadata, localizationContext);
    }

    @Override
    public Map<String, InstanceState> getInstanceState(TestDatabaseServerInstanceTemplate template,
                                                       Collection<String> instanceIds) {
      Map<String, InstanceState> states = Maps.newHashMap();
      states.put("state1", TestFixturesV2.INSTANCE_STATE_PENDING);
      return states;
    }

    @Override
    public Resource.Type getResourceType() {
      return TestDatabaseServerInstance.TYPE;
    }

    @Override
    public TestDatabaseServerInstanceTemplate createResourceTemplate(String name, Configured configuration,
                                                                     Map<String, String> tags) {
      return new TestDatabaseServerInstanceTemplate(name, configuration, tags, getLocalizationContext());
    }

    @Override
    public Collection<TestDatabaseServerInstance> allocate(TestDatabaseServerInstanceTemplate template, Collection<String> resourceIds,
                         int minCount) throws InterruptedException {
      List<TestDatabaseServerInstance> allocatedInstances = Lists.newArrayList();
      for (String id : resourceIds) {
        TestDatabaseServerInstance testDatabaseServerInstance =
            new TestDatabaseServerInstance(template, id, mock(InetAddress.class), 1000);
        dbInstances.put(id, testDatabaseServerInstance);
        allocatedInstances.add(testDatabaseServerInstance);
      }
      return allocatedInstances;
    }

    @Override
    public Collection<TestDatabaseServerInstance> find(TestDatabaseServerInstanceTemplate template,
                                                       Collection<String> resourceIds) throws InterruptedException {
      Collection<TestDatabaseServerInstance> foundInstances = Lists.newArrayList();
      for (String id : resourceIds) {
        if (dbInstances.containsKey(id)) {
          TestDatabaseServerInstance dbsInstance = new TestDatabaseServerInstance(
              template, id, mock(InetAddress.class), 1000);
          foundInstances.add(dbsInstance);
        }
      }
      return foundInstances;
    }

    @Override
    public void delete(TestDatabaseServerInstanceTemplate template,
                       Collection<String> resourceIds) throws InterruptedException {
      for (String id : resourceIds) {
        dbInstances.remove(id);
      }
    }
  }
}
