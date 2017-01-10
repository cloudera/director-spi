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

import static com.cloudera.director.spi.v2.adapters.v1.FromV1.fromV1;

import com.cloudera.director.spi.v1.model.Resource;
import com.cloudera.director.spi.v1.model.exception.PluginExceptionCondition;
import com.cloudera.director.spi.v1.model.exception.PluginExceptionConditionAccumulator;
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
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.Resource.Type;
import com.cloudera.director.spi.v2.model.ResourceTemplate;
import com.cloudera.director.spi.v2.provider.ResourceProvider;
import com.cloudera.director.spi.v2.provider.ResourceProviderMetadata;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Contains functions to convert V2 objects to V1 objects.
 */
public class ToV1 {

  private ToV1() {
  }

  public static com.cloudera.director.spi.v1.common.http.HttpProxyParameters toV1(
      HttpProxyParameters httpProxyParameters) {
    return new com.cloudera.director.spi.v1.common.http.HttpProxyParameters(
        httpProxyParameters.getHost(),
        httpProxyParameters.getPort(),
        httpProxyParameters.getUsername(),
        httpProxyParameters.getPassword(),
        httpProxyParameters.getDomain(),
        httpProxyParameters.getWorkstation(),
        httpProxyParameters.isPreemptiveBasicProxyAuth()
    );
  }

  public static com.cloudera.director.spi.v1.provider.ResourceProviderMetadata toV1(
      final ResourceProviderMetadata resourceProviderMetadata) {

    return new com.cloudera.director.spi.v1.provider.ResourceProviderMetadata() {
      @Override
      public Class<? extends com.cloudera.director.spi.v1.provider.ResourceProvider<?, ?>>
      getProviderClass() {
        Class<? extends ResourceProvider<?, ?>> providerClass =
            resourceProviderMetadata.getProviderClass();

        Class<? extends com.cloudera.director.spi.v1.provider.ResourceProvider> ret;

        if (ComputeProvider.class.isAssignableFrom(providerClass)) {
          ret = com.cloudera.director.spi.v1.compute.ComputeProvider.class;
        } else if (DatabaseServerProvider.class.isAssignableFrom(providerClass)) {
          ret =  com.cloudera.director.spi.v1.database.DatabaseServerProvider.class;
        } else {
          ret = com.cloudera.director.spi.v1.provider.ResourceProvider.class;
        }

        //noinspection unchecked
        return (Class<? extends com.cloudera.director.spi.v1.provider.ResourceProvider<?, ?>>) ret;
      }

      @Override
      public List<com.cloudera.director.spi.v1.model.ConfigurationProperty>
      getResourceTemplateConfigurationProperties() {
        final List<ConfigurationProperty> resourceTemplateConfigurationProperties =
            resourceProviderMetadata.getResourceTemplateConfigurationProperties();
        return new AbstractList<com.cloudera.director.spi.v1.model.ConfigurationProperty>() {
          @Override
          public com.cloudera.director.spi.v1.model.ConfigurationProperty get(int index) {
            return toV1(resourceTemplateConfigurationProperties.get(index));
          }

          @Override
          public int size() {
            return resourceTemplateConfigurationProperties.size();
          }
        };
      }

      @Override
      public List<com.cloudera.director.spi.v1.model.DisplayProperty>
      getResourceDisplayProperties() {
        final List<DisplayProperty> resourceDisplayProperties =
            resourceProviderMetadata.getResourceDisplayProperties();
        return new AbstractList<com.cloudera.director.spi.v1.model.DisplayProperty>() {
          @Override
          public com.cloudera.director.spi.v1.model.DisplayProperty get(int index) {
            return toV1(resourceDisplayProperties.get(index));
          }

          @Override
          public int size() {
            return resourceDisplayProperties.size();
          }
        };
      }

      @Override
      public com.cloudera.director.spi.v1.model.ConfigurationValidator
      getResourceTemplateConfigurationValidator() {
        return toV1(resourceProviderMetadata.getResourceTemplateConfigurationValidator());
      }

      @Override
      public String getId() {
        return resourceProviderMetadata.getId();
      }

      @Override
      public com.cloudera.director.spi.v1.model.LocalizationContext getLocalizationContext(
          com.cloudera.director.spi.v1.model.LocalizationContext parentContext) {
        return toV1(resourceProviderMetadata.getLocalizationContext(fromV1(parentContext)));
      }

      @Override
      public String getName(
          com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
        return resourceProviderMetadata.getName(fromV1(localizationContext));
      }

      @Override
      public String getDescription(com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
        return resourceProviderMetadata.getDescription(fromV1(localizationContext));
      }

      @Override
      public List<com.cloudera.director.spi.v1.model.ConfigurationProperty> getProviderConfigurationProperties() {
        return toV1(resourceProviderMetadata.getProviderConfigurationProperties());
      }

      @Override
      public com.cloudera.director.spi.v1.model.ConfigurationValidator
      getProviderConfigurationValidator() {
        return toV1(resourceProviderMetadata.getProviderConfigurationValidator());
      }
    };
  }

  public static List<com.cloudera.director.spi.v1.model.ConfigurationProperty> toV1(
      final List<ConfigurationProperty> configurationProperties) {
    return new AbstractList<com.cloudera.director.spi.v1.model.ConfigurationProperty>() {
      @Override
      public com.cloudera.director.spi.v1.model.ConfigurationProperty get(int index) {
        return toV1(configurationProperties.get(index));
      }

      @Override
      public int size() {
        return configurationProperties.size();
      }
    };
  }

  public static com.cloudera.director.spi.v1.model.Configured toV1(final Configured configuration) {
    return new com.cloudera.director.spi.v1.model.Configured() {
      @Override
      public Map<String, String> getConfiguration(
          com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
        return configuration.getConfiguration(fromV1(localizationContext));
      }

      @Override
      public String getConfigurationValue(
          com.cloudera.director.spi.v1.model.ConfigurationPropertyToken token,
          com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
        return configuration.getConfigurationValue(fromV1(token), fromV1(localizationContext));
      }

      @Override
      public String getConfigurationValue(com.cloudera.director.spi.v1.model.ConfigurationProperty property,
                                          com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
        return configuration.getConfigurationValue(fromV1(property), fromV1(localizationContext));
      }
    };
  }

  private static com.cloudera.director.spi.v1.model.ConfigurationValidator toV1(
      final ConfigurationValidator configurationValidator) {
    return new com.cloudera.director.spi.v1.model.ConfigurationValidator() {
      @Override
      public void validate(String name,
          com.cloudera.director.spi.v1.model.Configured configuration,
          com.cloudera.director.spi.v1.model.exception.PluginExceptionConditionAccumulator accumulator,
          com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {

        com.cloudera.director.spi.v2.model.exception.PluginExceptionConditionAccumulator accumulatorV2 =
            fromV1(accumulator);
        configurationValidator.validate(name, fromV1(configuration), accumulatorV2, fromV1(localizationContext));
        addNewConditions(accumulator, toV1(accumulatorV2));
      }
    };
  }

  public static com.cloudera.director.spi.v1.model.ConfigurationProperty toV1(
      final ConfigurationProperty configurationProperty) {
    return new com.cloudera.director.spi.v1.model.ConfigurationProperty() {
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
      public String getMissingValueErrorMessage(
          com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
        return configurationProperty.getMissingValueErrorMessage(fromV1(localizationContext));
      }

      @Override
      public List<com.cloudera.director.spi.v1.model.ConfigurationPropertyValue> getValidValues(
          com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
        final List<ConfigurationPropertyValue> validValues =
            configurationProperty.getValidValues(fromV1(localizationContext));
        return new AbstractList<com.cloudera.director.spi.v1.model.ConfigurationPropertyValue>() {
          @Override
          public com.cloudera.director.spi.v1.model.ConfigurationPropertyValue get(int index) {
            return toV1(validValues.get(index));
          }

          @Override
          public int size() {
            return validValues.size();
          }
        };
      }

      @Override
      public Type getType() {
        return EnumAdapter.toV1(configurationProperty.getType());
      }

      @Override
      public Widget getWidget() {
        return EnumAdapter.toV1(configurationProperty.getWidget());
      }

      @Override
      public String getName(
          com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
        return configurationProperty.getName(fromV1(localizationContext));
      }

      @Override
      public String getDescription(
          com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
        return configurationProperty.getDescription(fromV1(localizationContext));
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

  public static com.cloudera.director.spi.v1.model.ConfigurationPropertyValue toV1(
      final ConfigurationPropertyValue configurationPropertyValue) {
    return new com.cloudera.director.spi.v1.model.ConfigurationPropertyValue() {
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

  public static com.cloudera.director.spi.v1.model.DisplayProperty toV1(
      final DisplayProperty displayProperty) {
    return new com.cloudera.director.spi.v1.model.DisplayProperty() {
      @Override
      public String getDisplayKey() {
        return displayProperty.getDisplayKey();
      }

      @Override
      public Type getType() {
        return EnumAdapter.toV1(displayProperty.getType());
      }

      @Override
      public Widget getWidget() {
        return EnumAdapter.toV1(displayProperty.getWidget());
      }

      @Override
      public String getName(
          com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
        return displayProperty.getName(fromV1(localizationContext));
      }

      @Override
      public String getDescription(
          com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
        return displayProperty.getDescription(fromV1(localizationContext));
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

  public static com.cloudera.director.spi.v1.model.LocalizationContext toV1(
      final LocalizationContext localizationContext) {
    return new com.cloudera.director.spi.v1.model.LocalizationContext() {
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

  public static com.cloudera.director.spi.v1.model.ConfigurationPropertyToken toV1(
      final ConfigurationPropertyToken configurationPropertyToken) {
    return new com.cloudera.director.spi.v1.model.ConfigurationPropertyToken() {
      @Override
      public com.cloudera.director.spi.v1.model.ConfigurationProperty unwrap() {
        return toV1(configurationPropertyToken.unwrap());
      }
    };
  }

  public static PluginExceptionConditionAccumulator toV1(
      com.cloudera.director.spi.v2.model.exception.PluginExceptionConditionAccumulator accumulator) {
    PluginExceptionConditionAccumulator newAccumulator = new PluginExceptionConditionAccumulator();

    for (Map.Entry<String, Collection<com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition>> entry
        : accumulator.getConditionsByKey().entrySet()) {
      String key = entry.getKey();
      Collection<com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition> conditions = entry.getValue();

      for (com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition condition : conditions) {
        com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition.Type type = condition.getType();
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

  public static com.cloudera.director.spi.v1.model.ResourceTemplate toV1(final ResourceTemplate resourceTemplate) {
    return new com.cloudera.director.spi.v1.model.ResourceTemplate() {
      @Override
      public String getName() {
        return resourceTemplate.getName();
      }

      @Override
      public Map<String, String> getTags() {
        return resourceTemplate.getTags();
      }

      @Override
      public Object unwrap() {
        return resourceTemplate.unwrap();
      }

      @Override
      public Map<String, String> getConfiguration(
          com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
        return resourceTemplate.getConfiguration(fromV1(localizationContext));
      }

      @Override
      public String getConfigurationValue(com.cloudera.director.spi.v1.model.ConfigurationPropertyToken token,
                                          com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
        return resourceTemplate.getConfigurationValue(fromV1(token), fromV1(localizationContext));
      }

      @Override
      public String getConfigurationValue(com.cloudera.director.spi.v1.model.ConfigurationProperty property,
                                          com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
        return resourceTemplate.getConfigurationValue(fromV1(property), fromV1(localizationContext));
      }
    };
  }

  public static Resource.Type toV1(final Type type) {
    return new Resource.Type() {
      @Override
      public String getDescription(com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
        return type.getDescription(fromV1(localizationContext));
      }
    };
  }

  public static com.cloudera.director.spi.v1.model.InstanceState toV1(final InstanceState instanceState) {
    return new com.cloudera.director.spi.v1.model.InstanceState() {
      @Override
      public com.cloudera.director.spi.v1.model.InstanceStatus getInstanceStatus() {
        return EnumAdapter.toV1(instanceState.getInstanceStatus());
      }

      @Override
      public String getInstanceStateDescription(
          com.cloudera.director.spi.v1.model.LocalizationContext localizationContext) {
        return instanceState.getInstanceStateDescription(fromV1(localizationContext));
      }

      @Override
      public Object unwrap() {
        return instanceState.unwrap();
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

}
