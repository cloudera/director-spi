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

import static com.cloudera.director.spi.v2.util.Preconditions.checkNotNull;

import com.cloudera.director.spi.v2.compute.ComputeInstance;
import com.cloudera.director.spi.v2.compute.ComputeInstanceTemplate;
import com.cloudera.director.spi.v2.compute.ComputeProvider;
import com.cloudera.director.spi.v2.compute.VirtualizationType;
import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.InstanceState;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.Resource;
import com.cloudera.director.spi.v2.model.ResourceTemplate;
import com.cloudera.director.spi.v2.model.exception.PluginExceptionConditionAccumulator;
import com.cloudera.director.spi.v2.provider.ResourceProviderMetadata;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Contains functions to convert the compute provider and related
 * objects from V1 to V2 and vice versa.
 */
public class ComputeProviderAdapter {

  private ComputeProviderAdapter() {
  }

  // Functions to convert V1 -> V2

  static <R extends ComputeInstance<T>,
      T extends ComputeInstanceTemplate,
      U extends com.cloudera.director.spi.v1.compute.ComputeInstance<V>,
      V extends com.cloudera.director.spi.v1.compute.ComputeInstanceTemplate>
  ComputeProvider<R, T> fromV1(final com.cloudera.director.spi.v1.compute.ComputeProvider<U, V> computeProvider) {
    return new ComputeProvider<R, T>() {
      @Override
      public Map<String, InstanceState> getInstanceState(T template, Collection<String> instanceIds) {
        try {
          // todo return abstractmap instead of hashmap
          Map<String, com.cloudera.director.spi.v1.model.InstanceState> instanceStates
              = computeProvider.getInstanceState((V) toV1(template), instanceIds);

          Map<String, InstanceState> ret = new HashMap<String, InstanceState>();
          for (Map.Entry<String, com.cloudera.director.spi.v1.model.InstanceState> entry : instanceStates.entrySet()) {
            ret.put(entry.getKey(), FromV1.fromV1(entry.getValue()));
          }
          return ret;
        } catch(com.cloudera.director.spi.v1.model.exception.AbstractPluginException ex) {
          throw ExceptionsAdapter.fromV1(ex);
        }
      }

      @Override
      public ResourceProviderMetadata getProviderMetadata() {
        return FromV1.fromV1(computeProvider.getProviderMetadata());
      }

      @Override
      public Resource.Type getResourceType() {
        return FromV1.fromV1(computeProvider.getResourceType());
      }

      @Override
      public LocalizationContext getLocalizationContext() {
        return FromV1.fromV1(computeProvider.getLocalizationContext());
      }

      @Override
      public LocalizationContext getResourceTemplateLocalizationContext() {
        return FromV1.fromV1(computeProvider.getResourceTemplateLocalizationContext());
      }

      @Override
      public void validateResourceTemplateConfiguration(String name, Configured configuration,
                                                        PluginExceptionConditionAccumulator accumulator) {
        try {
          com.cloudera.director.spi.v1.model.exception.PluginExceptionConditionAccumulator accumulatorV1
              = ToV1.toV1(accumulator);
          computeProvider.validateResourceTemplateConfiguration(name, ToV1.toV1(configuration), accumulatorV1);
          FromV1.addNewConditions(accumulator, FromV1.fromV1(accumulatorV1));
        } catch(com.cloudera.director.spi.v1.model.exception.AbstractPluginException ex) {
          throw ExceptionsAdapter.fromV1(ex);
        }
      }

      @Override
      public T createResourceTemplate(String name, Configured configuration, Map<String, String> tags) {
        try {
          com.cloudera.director.spi.v1.compute.ComputeInstanceTemplate computeInstanceTemplate;
          computeInstanceTemplate = computeProvider.createResourceTemplate(name, ToV1.toV1(configuration), tags);
          return (T) fromV1(computeInstanceTemplate);
        } catch(com.cloudera.director.spi.v1.model.exception.AbstractPluginException ex) {
          throw ExceptionsAdapter.fromV1(ex);
        }
      }

      @Override
      public Collection<R> allocate(T template, Collection<String> resourceIds, int minCount)
          throws InterruptedException {
        try {
          V v1Template = (V) toV1(template);
          computeProvider.allocate(v1Template, resourceIds, minCount);
          return find(template, resourceIds);
        } catch(com.cloudera.director.spi.v1.model.exception.AbstractPluginException ex) {
          throw ExceptionsAdapter.fromV1(ex);
        }
      }

      @Override
      public Collection<R> find(T template, Collection<String> resourceIds) throws InterruptedException {
        try {
          // todo return Collection
          Collection<U> computeInstances = computeProvider.find((V) toV1(template), resourceIds);
          List<U> computeInstancesList = new ArrayList<U>(computeInstances);
          List<R> ret = new ArrayList<R>();
          for (U computeInstance : computeInstancesList) {
            ret.add((R) fromV1(computeInstance));
          }
          return ret;
        } catch(com.cloudera.director.spi.v1.model.exception.AbstractPluginException ex) {
          throw ExceptionsAdapter.fromV1(ex);
        }
      }

      @Override
      public void delete(T template, Collection<String> resourceIds) throws InterruptedException {
        try {
          computeProvider.delete((V) toV1(template), resourceIds);
        } catch(com.cloudera.director.spi.v1.model.exception.AbstractPluginException ex) {
          throw ExceptionsAdapter.fromV1(ex);
        }
      }

      @Override
      public Map<String, Set<String>> getHostKeyFingerprints(T template, Collection<String> instanceIds)
          throws InterruptedException {
        return new HashMap<String, Set<String>>();
      }
    };
  }

  private static ComputeInstance fromV1(final com.cloudera.director.spi.v1.compute.ComputeInstance computeInstance) {
    return new ComputeInstance() {
      @Override
      public VirtualizationType getVirtualizationType() {
        return EnumAdapter.fromV1(computeInstance.getVirtualizationType());
      }

      @Override
      public InetAddress getPrivateIpAddress() {
        return computeInstance.getPrivateIpAddress();
      }

      @Override
      public Type getType() {
        return FromV1.fromV1(computeInstance.getType());
      }

      @Override
      public ResourceTemplate getTemplate() {
        return ComputeProviderAdapter.fromV1(
            (com.cloudera.director.spi.v1.compute.ComputeInstanceTemplate) computeInstance.getTemplate()
        );
      }

      @Override
      public String getId() {
        return computeInstance.getId();
      }

      @Override
      public String getDescription(LocalizationContext localizationContext) {
        return computeInstance.getDescription(ToV1.toV1(localizationContext));
      }

      @Override
      public Map<String, String> getProperties() {
        return computeInstance.getProperties();
      }

      @Override
      public Object unwrap() {
        return computeInstance.unwrap();
      }
    };
  }

  public static class ComputeInstanceTemplateWrapper extends ComputeInstanceTemplate {

    private com.cloudera.director.spi.v1.compute.ComputeInstanceTemplate delegate;

    ComputeInstanceTemplateWrapper(String name, Configured configuration, Map<String, String> tags,
                                   LocalizationContext providerLocalizationContext,
                                   com.cloudera.director.spi.v1.compute.ComputeInstanceTemplate delegate) {
      super(name, configuration, tags, providerLocalizationContext);
      this.delegate = checkNotNull(delegate, "delegate is null");
    }

    public com.cloudera.director.spi.v1.compute.ComputeInstanceTemplate getDelegate() {
      return delegate;
    }
  }

  private static ComputeInstanceTemplateWrapper fromV1(
      final com.cloudera.director.spi.v1.compute.ComputeInstanceTemplate computeInstanceTemplate) {
    Configured configured = FromV1.fromV1(computeInstanceTemplate);
    return new ComputeInstanceTemplateWrapper(
        computeInstanceTemplate.getName(), configured, computeInstanceTemplate.getTags(),
        FromV1.fromV1(computeInstanceTemplate.getLocalizationContext()), computeInstanceTemplate);
  }

  // Functions to convert V2 -> V1

  @SuppressWarnings("PMD.UnusedPrivateMethod")
  private static com.cloudera.director.spi.v1.compute.ComputeInstanceTemplate toV1(
      final ComputeInstanceTemplate computeInstanceTemplate) {
    ComputeProviderAdapter.ComputeInstanceTemplateWrapper wrapper
        = (ComputeProviderAdapter.ComputeInstanceTemplateWrapper) computeInstanceTemplate;
    return wrapper.getDelegate();
  }
}
