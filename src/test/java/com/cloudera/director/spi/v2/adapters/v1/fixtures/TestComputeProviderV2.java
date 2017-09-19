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

import com.cloudera.director.spi.v2.compute.ComputeInstance;
import com.cloudera.director.spi.v2.compute.ComputeInstanceTemplate;
import com.cloudera.director.spi.v2.compute.util.AbstractComputeInstance;
import com.cloudera.director.spi.v2.compute.util.AbstractComputeProvider;
import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.InstanceState;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.Resource;
import com.cloudera.director.spi.v2.model.exception.UnrecoverableProviderException;
import com.cloudera.director.spi.v2.provider.ResourceProviderMetadata;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.net.InetAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Contains test compute provider classes that implement V2 of the SPI.
 */
public class TestComputeProviderV2 {
  static final class TestComputeInstanceTemplate extends ComputeInstanceTemplate {
    public TestComputeInstanceTemplate(String name, Configured configuration, Map<String, String> tags,
                                       LocalizationContext providerLocalizationContext) {
      super(name, configuration, tags, providerLocalizationContext);
    }
  }

  static final class TestComputeInstance
      extends AbstractComputeInstance<TestComputeInstanceTemplate, Object>
      implements ComputeInstance<TestComputeInstanceTemplate> {


    protected TestComputeInstance(TestComputeInstanceTemplate template, String instanceId,
                                  InetAddress privateIpAddress) {
      super(template, instanceId, privateIpAddress);
    }

    @Override
    public Map<String, String> getProperties() {
      Map<String, String> result = Maps.newHashMap();
      result.put("prop1", "val1");
      return result;    }
  }

  static final class TestComputeProvider
      extends AbstractComputeProvider<TestComputeInstance, TestComputeInstanceTemplate> {

    Map<String, TestComputeInstance> computeInstances = Maps.newHashMap();

    public TestComputeProvider(Configured configuration, ResourceProviderMetadata providerMetadata,
                               LocalizationContext localizationContext) {
      super(configuration, providerMetadata, localizationContext);
    }

    @Override
    public Map<String, InstanceState> getInstanceState(TestComputeInstanceTemplate template, Collection<String> instanceIds) {
      Map<String, InstanceState> states = Maps.newHashMap();
      states.put("state1", TestFixturesV2.INSTANCE_STATE_PENDING);
      return states;
    }

    @Override
    public Resource.Type getResourceType() {
      return TestComputeInstance.TYPE;
    }

    @Override
    public TestComputeInstanceTemplate createResourceTemplate(String name, Configured configuration,
                                                              Map<String, String> tags) {
      return new TestComputeInstanceTemplate(name, configuration, tags, getLocalizationContext());
    }

    @Override
    public Collection<TestComputeInstance> allocate(TestComputeInstanceTemplate template, Collection<String> resourceIds, int minCount) throws InterruptedException {
      if (minCount < 0) {
        throw new UnrecoverableProviderException("min count should be greater than 0");
      }

      List<TestComputeInstance> allocatedInstances = Lists.newArrayList();
      for (String id : resourceIds) {
        TestComputeInstance testComputeInstance =
            new TestComputeInstance(template, id, mock(InetAddress.class));
        computeInstances.put(id, testComputeInstance);
        allocatedInstances.add(testComputeInstance);
      }
      return allocatedInstances;
    }

    @Override
    public Collection<TestComputeInstance> find(TestComputeInstanceTemplate template, Collection<String> resourceIds) throws InterruptedException {
      Collection<TestComputeInstance> foundInstances = Lists.newArrayList();
      for (String id : resourceIds) {
        if (computeInstances.containsKey(id)) {
          TestComputeInstance dbsInstance = new TestComputeInstance(
              template, id, mock(InetAddress.class));
          foundInstances.add(dbsInstance);
        }
      }
      return foundInstances;
    }

    @Override
    public void delete(TestComputeInstanceTemplate template, Collection<String> resourceIds) throws InterruptedException {
      for (String id : resourceIds) {
        computeInstances.remove(id);
      }
    }

    @Override
    public Map<String, List<String>> getHostKeyFingerprints(TestComputeInstanceTemplate template,
                                                            Collection<String> instanceIds) throws InterruptedException {
      Map<String, List<String>> fingerprints = Maps.newHashMap();
      for (String id : instanceIds) {
        fingerprints.put(id, Lists.newArrayList("fingerprint-" + id));
      }
      return fingerprints;
    }
  }
}
