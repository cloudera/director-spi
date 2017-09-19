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

import com.cloudera.director.spi.v2.database.DatabaseServerInstance;
import com.cloudera.director.spi.v2.database.DatabaseServerInstanceTemplate;
import com.cloudera.director.spi.v2.database.DatabaseServerProvider;
import com.cloudera.director.spi.v2.database.DatabaseServerProviderMetadata;
import com.cloudera.director.spi.v2.database.DatabaseType;
import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.ConfigurationValidator;
import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.DisplayProperty;
import com.cloudera.director.spi.v2.model.InstanceState;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.Resource;
import com.cloudera.director.spi.v2.model.ResourceTemplate;
import com.cloudera.director.spi.v2.model.exception.PluginExceptionConditionAccumulator;
import com.cloudera.director.spi.v2.provider.ResourceProvider;
import com.cloudera.director.spi.v2.provider.ResourceProviderMetadata;
import com.cloudera.director.spi.v2.util.Preconditions;

import java.net.InetAddress;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Contains functions to convert the database server provider and related
 * objects from V1 to V2 and vice versa.
 */
public class DatabaseServerProviderAdapter {

  private DatabaseServerProviderAdapter() {
  }

  static <R extends DatabaseServerInstance<T>,
      T extends DatabaseServerInstanceTemplate,
      U extends com.cloudera.director.spi.v1.database.DatabaseServerInstance<V>,
      V extends com.cloudera.director.spi.v1.database.DatabaseServerInstanceTemplate>
  DatabaseServerProvider<R,T> fromV1(
      final com.cloudera.director.spi.v1.database.DatabaseServerProvider<U,V> databaseServerProvider) {
    return new DatabaseServerProvider<R, T>() {
      @Override
      public Map<String, InstanceState> getInstanceState(T template, Collection<String> instanceIds) {
        try {
          // todo return abstractmap instead of hashmap
          Map<String, com.cloudera.director.spi.v1.model.InstanceState> instanceStates
              = databaseServerProvider.getInstanceState((V) toV1(template), instanceIds);

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
        return fromV1((com.cloudera.director.spi.v1.database.DatabaseServerProviderMetadata)
            databaseServerProvider.getProviderMetadata());
      }

      @Override
      public Resource.Type getResourceType() {
        return FromV1.fromV1(databaseServerProvider.getResourceType());
      }

      @Override
      public LocalizationContext getLocalizationContext() {
        return FromV1.fromV1(databaseServerProvider.getLocalizationContext());
      }

      @Override
      public LocalizationContext getResourceTemplateLocalizationContext() {
        return FromV1.fromV1(databaseServerProvider.getResourceTemplateLocalizationContext());
      }

      @Override
      public void validateResourceTemplateConfiguration(String name, Configured configuration,
                                                        PluginExceptionConditionAccumulator accumulator) {
        try {
          com.cloudera.director.spi.v1.model.exception.PluginExceptionConditionAccumulator accumulatorV1
              = ToV1.toV1(accumulator);
          databaseServerProvider.validateResourceTemplateConfiguration(name, ToV1.toV1(configuration),
              accumulatorV1);
          FromV1.addNewConditions(accumulator, FromV1.fromV1(accumulatorV1));
        } catch(com.cloudera.director.spi.v1.model.exception.AbstractPluginException ex) {
          throw ExceptionsAdapter.fromV1(ex);
        }
      }

      @Override
      public T createResourceTemplate(String name, Configured configuration, Map<String, String> tags) {
        try {
          com.cloudera.director.spi.v1.database.DatabaseServerInstanceTemplate databaseServerInstanceTemplate;
          databaseServerInstanceTemplate =
              databaseServerProvider.createResourceTemplate(name, ToV1.toV1(configuration), tags);
          return (T) fromV1(databaseServerInstanceTemplate);
        } catch(com.cloudera.director.spi.v1.model.exception.AbstractPluginException ex) {
          throw ExceptionsAdapter.fromV1(ex);
        }
      }

      @Override
      public Collection<R> allocate(T template, Collection<String> resourceIds, int minCount) throws InterruptedException {
        try {
          databaseServerProvider.allocate((V) toV1(template), resourceIds, minCount);
          return find(template, resourceIds);
        } catch(com.cloudera.director.spi.v1.model.exception.AbstractPluginException ex) {
          throw ExceptionsAdapter.fromV1(ex);
        }
      }

      @Override
      public Collection<R> find(T template, Collection<String> resourceIds) throws InterruptedException {
        try {
          // todo return Collection
          Collection<U> databaseServerInstances = databaseServerProvider.find((V) toV1(template), resourceIds);
          List<U> databaseServerInstancesList = new ArrayList<U>(databaseServerInstances);
          List<R> ret = new ArrayList<R>();
          for (U databaseServerInstance : databaseServerInstancesList) {
            ret.add((R) fromV1(databaseServerInstance));
          }
          return ret;
        } catch(com.cloudera.director.spi.v1.model.exception.AbstractPluginException ex) {
          throw ExceptionsAdapter.fromV1(ex);
        }
      }

      @Override
      public void delete(T template, Collection<String> resourceIds) throws InterruptedException {
        try {
          databaseServerProvider.delete((V) toV1(template), resourceIds);
        } catch(com.cloudera.director.spi.v1.model.exception.AbstractPluginException ex) {
          throw ExceptionsAdapter.fromV1(ex);
        }
      }
    };
  }

  public static DatabaseServerProviderMetadata fromV1(
      final com.cloudera.director.spi.v1.database.DatabaseServerProviderMetadata databaseServerProviderMetadata) {
    return new DatabaseServerProviderMetadata() {
      @Override
      public Set<DatabaseType> getSupportedDatabaseTypes() {
        // todo return abstract set
        Set<DatabaseType> supportedDatabaseTypes = new HashSet<DatabaseType>();
        for (com.cloudera.director.spi.v1.database.DatabaseType supportedDatabaseType
            : databaseServerProviderMetadata.getSupportedDatabaseTypes()) {
          supportedDatabaseTypes.add(fromV1(supportedDatabaseType));
        }
        return supportedDatabaseTypes;
      }

      @Override
      public Class<? extends ResourceProvider<?, ?>> getProviderClass() {
        Class<? extends com.cloudera.director.spi.v1.provider.ResourceProvider<?, ?>> providerClass
            = databaseServerProviderMetadata.getProviderClass();


        Class<? extends ResourceProvider> ret;

        if (!com.cloudera.director.spi.v1.database.DatabaseServerProvider.class.isAssignableFrom(providerClass)) {
          throw new IllegalStateException("Unexpected provider class: " + providerClass);
        } else {
          ret = DatabaseServerProvider.class;
        }

        //noinspection unchecked
        return (Class<? extends ResourceProvider<?, ?>>) ret;
      }

      @Override
      public List<ConfigurationProperty> getResourceTemplateConfigurationProperties() {
        return FromV1.fromV1(databaseServerProviderMetadata.getProviderConfigurationProperties());
      }

      @Override
      public List<DisplayProperty> getResourceDisplayProperties() {
        final List<com.cloudera.director.spi.v1.model.DisplayProperty> resourceDisplayProperties
            = databaseServerProviderMetadata.getResourceDisplayProperties();
        return new AbstractList<DisplayProperty>() {
          @Override
          public DisplayProperty get(int index) {
            return FromV1.fromV1(resourceDisplayProperties.get(index));
          }

          @Override
          public int size() {
            return resourceDisplayProperties.size();
          }
        };
      }

      @Override
      public ConfigurationValidator getResourceTemplateConfigurationValidator() {
        return FromV1.fromV1(databaseServerProviderMetadata.getResourceTemplateConfigurationValidator());
      }

      @Override
      public String getId() {
        return databaseServerProviderMetadata.getId();
      }

      @Override
      public LocalizationContext getLocalizationContext(LocalizationContext parentContext) {
        return FromV1.fromV1(databaseServerProviderMetadata.getLocalizationContext(ToV1.toV1(parentContext)));
      }

      @Override
      public String getName(LocalizationContext localizationContext) {
        return databaseServerProviderMetadata.getName(ToV1.toV1(localizationContext));
      }

      @Override
      public String getDescription(LocalizationContext localizationContext) {
        return databaseServerProviderMetadata.getDescription(ToV1.toV1(localizationContext));
      }

      @Override
      public List<ConfigurationProperty> getProviderConfigurationProperties() {
        return FromV1.fromV1(databaseServerProviderMetadata.getProviderConfigurationProperties());
      }

      @Override
      public ConfigurationValidator getProviderConfigurationValidator() {
        return FromV1.fromV1(databaseServerProviderMetadata.getProviderConfigurationValidator());
      }
    };
  }

  private static DatabaseServerInstance fromV1(
      final com.cloudera.director.spi.v1.database.DatabaseServerInstance databaseServerInstance) {
    return new DatabaseServerInstance() {
      @Override
      public Integer getPort() {
        return databaseServerInstance.getPort();
      }

      @Override
      public InetAddress getPrivateIpAddress() {
        return databaseServerInstance.getPrivateIpAddress();
      }

      @Override
      public Type getType() {
        return FromV1.fromV1(databaseServerInstance.getType());
      }

      @Override
      public ResourceTemplate getTemplate() {
        return DatabaseServerProviderAdapter.fromV1(
            (com.cloudera.director.spi.v1.database.DatabaseServerInstanceTemplate) databaseServerInstance.getTemplate()
        );
      }

      @Override
      public String getId() {
        return databaseServerInstance.getId();
      }

      @Override
      public String getDescription(LocalizationContext localizationContext) {
        return databaseServerInstance.getDescription(ToV1.toV1(localizationContext));
      }

      @Override
      public Map<String, String> getProperties() {
        return databaseServerInstance.getProperties();
      }

      @Override
      public Object unwrap() {
        return databaseServerInstance.unwrap();
      }
    };
  }

  public static class DatabaseServerInstanceTemplateWrapper extends DatabaseServerInstanceTemplate {
    private com.cloudera.director.spi.v1.database.DatabaseServerInstanceTemplate delegate;

    public DatabaseServerInstanceTemplateWrapper(
        String name, Configured configuration, Map<String, String> tags,
        LocalizationContext localizationContext,
        com.cloudera.director.spi.v1.database.DatabaseServerInstanceTemplate delegate) {
      super(name, configuration, tags, localizationContext);
      this.delegate = Preconditions.checkNotNull(delegate, "delegate is null");
    }

    public com.cloudera.director.spi.v1.database.DatabaseServerInstanceTemplate getDelegate() {
      return delegate;
    }
  }

  public static DatabaseServerInstanceTemplateWrapper fromV1(
      final com.cloudera.director.spi.v1.database.DatabaseServerInstanceTemplate databaseServerInstanceTemplate) {
    Configured configured = FromV1.fromV1(databaseServerInstanceTemplate);
    DatabaseServerInstanceTemplateWrapper wrapper =
        new DatabaseServerInstanceTemplateWrapper(databaseServerInstanceTemplate.getName(),
            configured, databaseServerInstanceTemplate.getTags(),
            FromV1.fromV1(databaseServerInstanceTemplate.getLocalizationContext()), databaseServerInstanceTemplate);
    return wrapper;
  }

  private static DatabaseType fromV1(com.cloudera.director.spi.v1.database.DatabaseType supportedDatabaseType) {
    Preconditions.checkNotNull(supportedDatabaseType, "supportedDatabaseType is null");
    switch (supportedDatabaseType) {
      case POSTGRESQL:
        return DatabaseType.POSTGRESQL;
      case MYSQL:
        return DatabaseType.MYSQL;
      case ORACLE:
        return DatabaseType.ORACLE;
      default:
        throw new IllegalArgumentException("Unknown supportedDatabaseType: " + supportedDatabaseType);
    }
  }

  public static com.cloudera.director.spi.v1.database.DatabaseServerInstanceTemplate toV1(
      final DatabaseServerInstanceTemplate databaseServerInstanceTemplate) {
    DatabaseServerInstanceTemplateWrapper wrapper =
        (DatabaseServerInstanceTemplateWrapper) databaseServerInstanceTemplate;
    return wrapper.getDelegate();
  }
}
