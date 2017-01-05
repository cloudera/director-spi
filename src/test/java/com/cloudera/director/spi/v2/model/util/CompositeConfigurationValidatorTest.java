package com.cloudera.director.spi.v2.model.util;

import static com.cloudera.director.spi.v2.model.util.DefaultConfigurationValidatorTest.CONFIGURATION_PROPERTIES;
import static com.cloudera.director.spi.v2.model.util.DefaultConfigurationValidatorTest.KEY1;
import static com.cloudera.director.spi.v2.model.util.DefaultConfigurationValidatorTest.KEY2;
import static com.cloudera.director.spi.v2.model.util.DefaultConfigurationValidatorTest.KEY3;
import static com.cloudera.director.spi.v2.model.util.DefaultConfigurationValidatorTest.KEY4;
import static org.assertj.core.api.Assertions.assertThat;

import com.cloudera.director.spi.v2.model.ConfigurationValidator;
import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition;
import com.cloudera.director.spi.v2.model.exception.PluginExceptionConditionAccumulator;
import com.cloudera.director.spi.v2.provider.Launcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Tests {@link CompositeConfigurationValidator}.
 */
public class CompositeConfigurationValidatorTest {

  private static final List<ConfigurationValidator> VALIDATORS =
      Arrays.<ConfigurationValidator>asList(
          new DefaultConfigurationValidator(Arrays.asList(
              CONFIGURATION_PROPERTIES.get(0)
          )),
          new DefaultConfigurationValidator(Arrays.asList(
              CONFIGURATION_PROPERTIES.get(1),
              CONFIGURATION_PROPERTIES.get(2),
              CONFIGURATION_PROPERTIES.get(3)
          )),
          new DefaultConfigurationValidator(Arrays.asList(
              CONFIGURATION_PROPERTIES.get(1)
          )),
          new DefaultConfigurationValidator(Arrays.asList(
              CONFIGURATION_PROPERTIES.get(2),
              CONFIGURATION_PROPERTIES.get(3)
          ))
      );

  @Test
  public void testConstructor() {
    CompositeConfigurationValidator validator = new CompositeConfigurationValidator(
        VALIDATORS.get(0), VALIDATORS.get(1));
    assertThat(validator.getValidators()).hasSize(2);
  }

  @Test(expected = NullPointerException.class)
  public void testConstructor_NullValidators() {
    new CompositeConfigurationValidator((List<ConfigurationValidator>) null);
  }

  @Test
  public void testValidation_EmptyValidators() {
    Configured configuration = new SimpleConfiguration();
    PluginExceptionConditionAccumulator accumulator = new PluginExceptionConditionAccumulator();
    CompositeConfigurationValidator validator =
        new CompositeConfigurationValidator(Collections.<ConfigurationValidator>emptyList());
    assertThat(validator.getValidators()).hasSize(0);
    validator.validate(null, configuration, accumulator, Launcher.DEFAULT_PLUGIN_LOCALIZATION_CONTEXT);
    assertThat(accumulator.hasError()).isFalse();
    Map<String, Collection<PluginExceptionCondition>> conditionsByKey =
        accumulator.getConditionsByKey();
    assertThat(conditionsByKey).doesNotContainKey(KEY1);
    assertThat(conditionsByKey).doesNotContainKey(KEY2);
    assertThat(conditionsByKey).doesNotContainKey(KEY3);
    assertThat(conditionsByKey).doesNotContainKey(KEY4);
  }

  @Test
  public void testValidation() {
    // Tests a successful validator followed by a failing validator with multiple failures
    CompositeConfigurationValidator validator = new CompositeConfigurationValidator(
        VALIDATORS.get(2), VALIDATORS.get(3));
    Configured configuration = new SimpleConfiguration();
    PluginExceptionConditionAccumulator accumulator = new PluginExceptionConditionAccumulator();
    validator.validate(null, configuration, accumulator, Launcher.DEFAULT_PLUGIN_LOCALIZATION_CONTEXT);
    assertThat(accumulator.hasError()).isTrue();
    Map<String, Collection<PluginExceptionCondition>> conditionsByKey =
        accumulator.getConditionsByKey();
    assertThat(conditionsByKey).doesNotContainKey(KEY1);
    assertThat(conditionsByKey).doesNotContainKey(KEY2);
    assertThat(conditionsByKey).containsKey(KEY3);
    assertThat(conditionsByKey).containsKey(KEY4);
  }

  @Test
  public void testValidationShortCircuits() {
    // Tests a failing validator followed by another failing validator
    CompositeConfigurationValidator validator = new CompositeConfigurationValidator(
        VALIDATORS.get(0), VALIDATORS.get(3));
    Configured configuration = new SimpleConfiguration();
    PluginExceptionConditionAccumulator accumulator = new PluginExceptionConditionAccumulator();
    validator.validate(null, configuration, accumulator, Launcher.DEFAULT_PLUGIN_LOCALIZATION_CONTEXT);
    assertThat(accumulator.hasError()).isTrue();
    Map<String, Collection<PluginExceptionCondition>> conditionsByKey =
        accumulator.getConditionsByKey();
    assertThat(conditionsByKey).containsKey(KEY1);
    assertThat(conditionsByKey).doesNotContainKey(KEY2);
    assertThat(conditionsByKey).doesNotContainKey(KEY3);
    assertThat(conditionsByKey).doesNotContainKey(KEY4);
  }
}
