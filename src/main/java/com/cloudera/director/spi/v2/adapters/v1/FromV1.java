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

import static com.cloudera.director.spi.v2.adapters.v1.ToV1.toV1;

import com.cloudera.director.spi.v2.common.http.HttpProxyParameters;
import com.cloudera.director.spi.v2.compute.ComputeProvider;
import com.cloudera.director.spi.v2.database.DatabaseServerProvider;
import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.ConfigurationPropertyToken;
import com.cloudera.director.spi.v2.model.ConfigurationPropertyValue;
import com.cloudera.director.spi.v2.model.ConfigurationValidator;
import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.DisplayProperty;
import com.cloudera.director.spi.v2.model.InstanceState;
import com.cloudera.director.spi.v2.model.InstanceStatus;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.Resource;
import com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition;
import com.cloudera.director.spi.v2.model.exception.PluginExceptionConditionAccumulator;
import com.cloudera.director.spi.v2.provider.CloudProvider;
import com.cloudera.director.spi.v2.provider.CloudProviderMetadata;
import com.cloudera.director.spi.v2.provider.CredentialsProviderMetadata;
import com.cloudera.director.spi.v2.provider.Launcher;
import com.cloudera.director.spi.v2.provider.ResourceProvider;
import com.cloudera.director.spi.v2.provider.ResourceProviderMetadata;

import java.io.File;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Contains functions to convert V1 objects to V2 objects.
 */
public class FromV1 {

  private FromV1() {
  }

  public static Launcher fromV1(final com.cloudera.director.spi.v1.provider.Launcher launcher) {

    return new Launcher() {
      @Override
      public void initialize(File configurationDirectory, HttpProxyParameters httpProxyParameters) {
        launcher.initialize(configurationDirectory, toV1(httpProxyParameters));
      }

      @Override
      public List<CloudProviderMetadata> getCloudProviderMetadata() {
        final List<com.cloudera.director.spi.v1.provider.CloudProviderMetadata> cloudProviderMetadatas =
            launcher.getCloudProviderMetadata();
        return new AbstractList<CloudProviderMetadata>() {
          @Override
          public CloudProviderMetadata get(int index) {
            return fromV1(cloudProviderMetadatas.get(index));
          }

          @Override
          public int size() {
            return cloudProviderMetadatas.size();
          }
        };
      }

      @Override
      public CloudProvider createCloudProvider(
          String cloudProviderId, Configured configuration, Locale locale) {
        return fromV1(launcher.createCloudProvider(cloudProviderId, toV1(configuration), locale));
      }

      @Override
      public LocalizationContext getLocalizationContext(Locale locale) {
        return fromV1(launcher.getLocalizationContext(locale));
      }
    };
  }

  public static CloudProviderMetadata fromV1(
      final com.cloudera.director.spi.v1.provider.CloudProviderMetadata cloudProviderMetadata) {
    return new CloudProviderMetadata() {
      @Override
      public List<ResourceProviderMetadata> getResourceProviderMetadata() {
        final List<com.cloudera.director.spi.v1.provider.ResourceProviderMetadata>
            resourceProviderMetadatas = cloudProviderMetadata.getResourceProviderMetadata();
        return new AbstractList<ResourceProviderMetadata>() {
          @Override
          public ResourceProviderMetadata get(int index) {
            com.cloudera.director.spi.v1.provider.ResourceProviderMetadata resourceProviderMetadata =
                resourceProviderMetadatas.get(index);

            if (com.cloudera.director.spi.v1.database.DatabaseServerProviderMetadata.class.isAssignableFrom(
                resourceProviderMetadata.getClass())) {
              return DatabaseServerProviderAdapter.fromV1(
                  (com.cloudera.director.spi.v1.database.DatabaseServerProviderMetadata) resourceProviderMetadata
              );
            } else {
              return fromV1(resourceProviderMetadatas.get(index));
            }
          }

          @Override
          public int size() {
            return resourceProviderMetadatas.size();
          }
        };
      }

      @Override
      public ResourceProviderMetadata getResourceProviderMetadata(String resourceProviderId) {
        return fromV1(cloudProviderMetadata.getResourceProviderMetadata(resourceProviderId));
      }

      @Override
      public CredentialsProviderMetadata getCredentialsProviderMetadata() {
        return fromV1(cloudProviderMetadata.getCredentialsProviderMetadata());
      }

      @Override
      public String getId() {
        return cloudProviderMetadata.getId();
      }

      @Override
      public LocalizationContext getLocalizationContext(LocalizationContext parentContext) {
        return fromV1(cloudProviderMetadata.getLocalizationContext(toV1(parentContext)));
      }

      @Override
      public String getName(LocalizationContext localizationContext) {
        return cloudProviderMetadata.getName(toV1(localizationContext));
      }

      @Override
      public String getDescription(LocalizationContext localizationContext) {
        return cloudProviderMetadata.getDescription(toV1(localizationContext));
      }

      @Override
      public List<ConfigurationProperty> getProviderConfigurationProperties() {
        final List<com.cloudera.director.spi.v1.model.ConfigurationProperty> configurationProperties =
            cloudProviderMetadata.getProviderConfigurationProperties();
        return new AbstractList<ConfigurationProperty>() {
          @Override
          public ConfigurationProperty get(int index) {
            return fromV1(configurationProperties.get(index));
          }

          @Override
          public int size() {
            return configurationProperties.size();
          }
        };
      }

      @Override
      public ConfigurationValidator getProviderConfigurationValidator() {
        return fromV1(cloudProviderMetadata.getProviderConfigurationValidator());
      }
    };
  }

  public static ConfigurationValidator fromV1(
      final com.cloudera.director.spi.v1.model.ConfigurationValidator providerConfigurationValidator) {
    return new ConfigurationValidator() {
      @Override
      public void validate(String name, Configured configuration,
          PluginExceptionConditionAccumulator accumulator, LocalizationContext localizationContext) {
        com.cloudera.director.spi.v1.model.exception.PluginExceptionConditionAccumulator accumulatorV1 =
            toV1(accumulator);
        providerConfigurationValidator.validate(name, toV1(configuration), accumulatorV1, toV1(localizationContext));
        addNewConditions(accumulator, fromV1(accumulatorV1));
      }
    };
  }

  public static void addNewConditions(PluginExceptionConditionAccumulator accumulator,
                                       PluginExceptionConditionAccumulator accumulatorNew) {
    Map<String, Collection<PluginExceptionCondition>> newConditionsByKey  = accumulatorNew.getConditionsByKey();
    for (Map.Entry<String, Collection<PluginExceptionCondition>> entry : newConditionsByKey.entrySet()) {
      String conditionKey = entry.getKey();
      Collection<PluginExceptionCondition> conditionsInNewAccumulator = entry.getValue();
      addNewConditions(accumulator, conditionKey, conditionsInNewAccumulator);
    }
  }

  private static void addNewConditions(PluginExceptionConditionAccumulator accumulator,
                                       String key, Collection<PluginExceptionCondition> conditions) {
    // add conditions that don't already exist in the accumulator
    Collection<PluginExceptionCondition> existingConditions = accumulator.getConditionsByKey().get(key);
    for (PluginExceptionCondition condition : conditions) {
      if (existingConditions == null || !existingConditions.contains(condition)) {
        PluginExceptionCondition.Type type = condition.getType();
        switch (type) {
          case WARNING:
            accumulator.addWarning(key, condition.getMessage());
            break;
          case ERROR:
            accumulator.addError(key, condition.getMessage());
            break;
          default:
            throw new IllegalArgumentException("Unknown type: " + type);
        }
      }
    }
  }

  private static CredentialsProviderMetadata fromV1(
      final com.cloudera.director.spi.v1.provider.CredentialsProviderMetadata credentialsProviderMetadata) {
    return new CredentialsProviderMetadata() {
      @Override
      public LocalizationContext getLocalizationContext(LocalizationContext cloudLocalizationContext) {
        return fromV1(credentialsProviderMetadata.getLocalizationContext(toV1(cloudLocalizationContext)));
      }

      @Override
      public List<ConfigurationProperty> getCredentialsConfigurationProperties() {
        return fromV1(credentialsProviderMetadata.getCredentialsConfigurationProperties());
      }
    };
  }

  public static List<ConfigurationProperty> fromV1(
      final List<com.cloudera.director.spi.v1.model.ConfigurationProperty> configurationProperties) {
    return new AbstractList<ConfigurationProperty>() {
      @Override
      public ConfigurationProperty get(int index) {
        return fromV1(configurationProperties.get(index));
      }

      @Override
      public int size() {
        return configurationProperties.size();
      }
    };
  }

  public static CloudProvider fromV1(
      final com.cloudera.director.spi.v1.provider.CloudProvider cloudProvider) {
    return new CloudProvider() {
      @Override
      public CloudProviderMetadata getProviderMetadata() {
        return fromV1(cloudProvider.getProviderMetadata());
      }

      @Override
      public LocalizationContext getLocalizationContext() {
        return fromV1(cloudProvider.getLocalizationContext());
      }

      @Override
      public void validateResourceProviderConfiguration(String name, ResourceProviderMetadata resourceProviderMetadata,
          Configured configuration, PluginExceptionConditionAccumulator accumulator) {
        com.cloudera.director.spi.v1.model.exception.PluginExceptionConditionAccumulator accumulatorV1
            = toV1(accumulator);
        cloudProvider.validateResourceProviderConfiguration(
            name, toV1(resourceProviderMetadata), toV1(configuration), accumulatorV1);
        addNewConditions(accumulator, fromV1(accumulatorV1));
      }

      @Override
      public ResourceProvider createResourceProvider(String resourceProviderId, Configured configuration) {
        com.cloudera.director.spi.v1.provider.ResourceProvider resourceProvider
            = cloudProvider.createResourceProvider(resourceProviderId, toV1(configuration));

        Class<? extends com.cloudera.director.spi.v1.provider.ResourceProvider> resourceProviderClass
            = resourceProvider.getClass();

        if (com.cloudera.director.spi.v1.compute.ComputeProvider.class.isAssignableFrom(
            resourceProviderClass)) {
          return ComputeProviderAdapter.fromV1((com.cloudera.director.spi.v1.compute.ComputeProvider) resourceProvider);
        } else if (com.cloudera.director.spi.v1.database.DatabaseServerProvider.class.isAssignableFrom(
            resourceProviderClass)) {
          return DatabaseServerProviderAdapter.fromV1(
              (com.cloudera.director.spi.v1.database.DatabaseServerProvider) resourceProvider);
        } else {
          throw new IllegalArgumentException("Unexpected resource provider class: " + resourceProviderClass);
        }
      }
    };
  }

  public static Resource.Type fromV1(final com.cloudera.director.spi.v1.model.Resource.Type resourceType) {
    return new Resource.Type() {
      @Override
      public String getDescription(LocalizationContext localizationContext) {
        return resourceType.getDescription(toV1(localizationContext));
      }
    };
  }

  public static ResourceProviderMetadata fromV1(
      final com.cloudera.director.spi.v1.provider.ResourceProviderMetadata resourceProviderMetadata) {
    return new ResourceProviderMetadata() {
      @Override
      public Class<? extends ResourceProvider<?, ?>> getProviderClass() {
        Class<? extends com.cloudera.director.spi.v1.provider.ResourceProvider<?, ?>> providerClass
            = resourceProviderMetadata.getProviderClass();

        Class<? extends ResourceProvider> ret;

        if (com.cloudera.director.spi.v1.compute.ComputeProvider.class.isAssignableFrom(providerClass)) {
          ret = ComputeProvider.class;
        } else if (com.cloudera.director.spi.v1.database.DatabaseServerProvider.class.isAssignableFrom(providerClass)) {
          ret =  DatabaseServerProvider.class;
        } else {
          ret = ResourceProvider.class;
        }

        //noinspection unchecked
        return (Class<? extends ResourceProvider<?, ?>>) ret;
      }

      @Override
      public List<ConfigurationProperty> getResourceTemplateConfigurationProperties() {
        final List<com.cloudera.director.spi.v1.model.ConfigurationProperty> configurationProperties =
            resourceProviderMetadata.getResourceTemplateConfigurationProperties();
        return new AbstractList<ConfigurationProperty>() {
          @Override
          public ConfigurationProperty get(int index) {
            return fromV1(configurationProperties.get(index));
          }

          @Override
          public int size() {
            return configurationProperties.size();
          }
        };
      }

      @Override
      public List<DisplayProperty> getResourceDisplayProperties() {
        final List<com.cloudera.director.spi.v1.model.DisplayProperty> displayProperties =
            resourceProviderMetadata.getResourceDisplayProperties();
        return new AbstractList<DisplayProperty>() {
          @Override
          public DisplayProperty get(int index) {
            return fromV1(displayProperties.get(index));
          }

          @Override
          public int size() {
            return displayProperties.size();
          }
        };
      }

      @Override
      public ConfigurationValidator getResourceTemplateConfigurationValidator() {
        return fromV1(resourceProviderMetadata.getResourceTemplateConfigurationValidator());
      }

      @Override
      public String getId() {
        return resourceProviderMetadata.getId();
      }

      @Override
      public LocalizationContext getLocalizationContext(LocalizationContext parentContext) {
        return fromV1(resourceProviderMetadata.getLocalizationContext(toV1(parentContext)));
      }

      @Override
      public String getName(LocalizationContext localizationContext) {
        return resourceProviderMetadata.getName(toV1(localizationContext));
      }

      @Override
      public String getDescription(LocalizationContext localizationContext) {
        return resourceProviderMetadata.getDescription(toV1(localizationContext));
      }

      @Override
      public List<ConfigurationProperty> getProviderConfigurationProperties() {
        final List<com.cloudera.director.spi.v1.model.ConfigurationProperty> providerConfigurationProperties =
            resourceProviderMetadata.getProviderConfigurationProperties();
        return new AbstractList<ConfigurationProperty>() {
          @Override
          public ConfigurationProperty get(int index) {
            return fromV1(providerConfigurationProperties.get(index));
          }

          @Override
          public int size() {
            return providerConfigurationProperties.size();
          }
        };
      }

      @Override
      public ConfigurationValidator getProviderConfigurationValidator() {
        return fromV1(resourceProviderMetadata.getProviderConfigurationValidator());
      }
    };
  }

  public static ConfigurationProperty fromV1(
      final com.cloudera.director.spi.v1.model.ConfigurationProperty configurationProperty) {
    return new ConfigurationProperty() {
      @Override
      public String getConfigKey() {
        return configurationProperty.getConfigKey();
      }

      @Override
      public boolean isRequired() {
        return configurationProperty.isRequired();
      }

      @Override
      public String getDefaultValue() {
        return configurationProperty.getDefaultValue();
      }

      @Override
      public String getMissingValueErrorMessage(LocalizationContext localizationContext) {
        return configurationProperty.getMissingValueErrorMessage(toV1(localizationContext));
      }

      @Override
      public List<ConfigurationPropertyValue> getValidValues(
          LocalizationContext localizationContext) {
        final List<com.cloudera.director.spi.v1.model.ConfigurationPropertyValue> validValues =
            configurationProperty.getValidValues(toV1(localizationContext));
        return new AbstractList<ConfigurationPropertyValue>() {
          @Override
          public ConfigurationPropertyValue get(int index) {
            return fromV1(validValues.get(index));
          }

          @Override
          public int size() {
            return validValues.size();
          }
        };
      }

      @Override
      public Type getType() {
        return EnumAdapter.fromV1(configurationProperty.getType());
      }

      @Override
      public Widget getWidget() {
        return EnumAdapter.fromV1(configurationProperty.getWidget());
      }

      @Override
      public String getName(LocalizationContext localizationContext) {
        return configurationProperty.getName(toV1(localizationContext));
      }

      @Override
      public String getDescription(LocalizationContext localizationContext) {
        return configurationProperty.getDescription(toV1(localizationContext));
      }

      @Override
      public boolean isSensitive() {
        return configurationProperty.isSensitive();
      }

      @Override
      public boolean isHidden() {
        return configurationProperty.isHidden();
      }
    };
  }

  public static ConfigurationPropertyToken fromV1(
      final com.cloudera.director.spi.v1.model.ConfigurationPropertyToken configurationPropertyToken) {
    return new ConfigurationPropertyToken() {
      @Override
      public ConfigurationProperty unwrap() {
        return fromV1(configurationPropertyToken.unwrap());
      }
    };
  }

  public static ConfigurationPropertyValue fromV1(
      final com.cloudera.director.spi.v1.model.ConfigurationPropertyValue configurationPropertyValue) {
    return new ConfigurationPropertyValue() {
      @Override
      public String getValue() {
        return configurationPropertyValue.getValue();
      }

      @Override
      public String getLabel() {
        return configurationPropertyValue.getLabel();
      }
    };
  }

  public static DisplayProperty fromV1(
      final com.cloudera.director.spi.v1.model.DisplayProperty displayProperty) {
    return new DisplayProperty() {
      @Override
      public String getDisplayKey() {
        return displayProperty.getDisplayKey();
      }

      @Override
      public Type getType() {
        return EnumAdapter.fromV1(displayProperty.getType());
      }

      @Override
      public Widget getWidget() {
        return EnumAdapter.fromV1(displayProperty.getWidget());
      }

      @Override
      public String getName(LocalizationContext localizationContext) {
        return displayProperty.getName(toV1(localizationContext));
      }

      @Override
      public String getDescription(LocalizationContext localizationContext) {
        return displayProperty.getDescription(toV1(localizationContext));
      }

      @Override
      public boolean isSensitive() {
        return displayProperty.isSensitive();
      }

      @Override
      public boolean isHidden() {
        return displayProperty.isHidden();
      }
    };
  }
  public static LocalizationContext fromV1(
      final com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
    return new LocalizationContext() {
      @Override
      public Locale getLocale() {
        return localizationContext.getLocale();
      }

      @Override
      public String getKeyPrefix() {
        return localizationContext.getKeyPrefix();
      }

      @Override
      public String localize(String defaultValue, String... keyComponents) {
        return localizationContext.localize(defaultValue, keyComponents);
      }
    };
  }

  public static Configured fromV1(final com.cloudera.director.spi.v1.model.Configured configured) {
    return new Configured() {
      @Override
      public Map<String, String> getConfiguration(LocalizationContext localizationContext) {
        return configured.getConfiguration(toV1(localizationContext));
      }

      @Override
      public String getConfigurationValue(ConfigurationPropertyToken token,
          LocalizationContext localizationContext) {
        return configured.getConfigurationValue(toV1(token), toV1(localizationContext));
      }

      @Override
      public String getConfigurationValue(ConfigurationProperty property,
          LocalizationContext localizationContext) {
        return configured.getConfigurationValue(toV1(property), toV1(localizationContext));
      }
    };
  }

  public static InstanceState fromV1(final com.cloudera.director.spi.v1.model.InstanceState instanceState) {
    return new InstanceState() {
      @Override
      public InstanceStatus getInstanceStatus() {
        return EnumAdapter.fromV1(instanceState.getInstanceStatus());
      }

      @Override
      public String getInstanceStateDescription(LocalizationContext localizationContext) {
        return instanceState.getInstanceStateDescription(toV1(localizationContext));
      }

      @Override
      public Object unwrap() {
        return instanceState.unwrap();
      }
    };
  }

  public static PluginExceptionConditionAccumulator fromV1(
      com.cloudera.director.spi.v1.model.exception.PluginExceptionConditionAccumulator accumulator) {
    PluginExceptionConditionAccumulator newAccumulator = new PluginExceptionConditionAccumulator();

    for (Map.Entry<String, Collection<com.cloudera.director.spi.v1.model.exception.PluginExceptionCondition>> entry
        : accumulator.getConditionsByKey().entrySet()) {
      String key = entry.getKey();
      Collection<com.cloudera.director.spi.v1.model.exception.PluginExceptionCondition> conditions = entry.getValue();

      for (com.cloudera.director.spi.v1.model.exception.PluginExceptionCondition condition : conditions) {
        com.cloudera.director.spi.v1.model.exception.PluginExceptionCondition.Type type = condition.getType();
        switch (type) {
          case WARNING:
            newAccumulator.addWarning(key, condition.getMessage());
            break;
          case ERROR:
            newAccumulator.addError(key, condition.getMessage());
            break;
          default:
            throw new IllegalArgumentException("Unknown type: " + type);
        }
      }
    }
    return newAccumulator;
  }
}
