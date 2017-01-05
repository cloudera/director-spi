package com.cloudera.director.spi.v2.model.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition;
import com.cloudera.director.spi.v2.model.exception.PluginExceptionConditionAccumulator;
import com.cloudera.director.spi.v2.provider.Launcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Tests {@link DefaultConfigurationValidator}.
 */
public class DefaultConfigurationValidatorTest {

  protected static final String KEY1 = "key1";
  protected static final String KEY2 = "key2";
  protected static final String KEY3 = "key3";
  protected static final String KEY4 = "key4";


  protected static final List<ConfigurationProperty> CONFIGURATION_PROPERTIES;

  static {
    List<ConfigurationProperty> list = new ArrayList<ConfigurationProperty>();
    list.add(buildMockConfigurationProperty(KEY1, true));
    list.add(buildMockConfigurationProperty(KEY2, false));
    list.add(buildMockConfigurationProperty(KEY3, true));
    list.add(buildMockConfigurationProperty(KEY4, true));
    CONFIGURATION_PROPERTIES = list;
  }

  private static ConfigurationProperty buildMockConfigurationProperty(String configKey,
      boolean required) {
    ConfigurationProperty configurationProperty = mock(ConfigurationProperty.class);
    when(configurationProperty.getConfigKey()).thenReturn(configKey);
    when(configurationProperty.isRequired()).thenReturn(required);
    when(configurationProperty.getMissingValueErrorMessage(any(LocalizationContext.class)))
        .thenReturn(configKey);
    return configurationProperty;
  }

  @Test
  public void testValidation() {
    Configured configuration = new SimpleConfiguration();
    PluginExceptionConditionAccumulator accumulator = new PluginExceptionConditionAccumulator();
    DefaultConfigurationValidator validator =
        new DefaultConfigurationValidator(CONFIGURATION_PROPERTIES);
    validator.validate(null, configuration, accumulator,
        Launcher.DEFAULT_PLUGIN_LOCALIZATION_CONTEXT);
    assertThat(accumulator.hasError()).isTrue();
    Map<String, Collection<PluginExceptionCondition>> conditionsByKey =
        accumulator.getConditionsByKey();
    assertThat(conditionsByKey).containsKey(KEY1);
    assertThat(conditionsByKey).doesNotContainKey(KEY2);
    assertThat(conditionsByKey).containsKey(KEY3);
    assertThat(conditionsByKey).containsKey(KEY4);
  }
}
