package com.cloudera.director.spi.v2.model.exception;

import static com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition.Type.ERROR;
import static com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition.Type.WARNING;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Tests {@link PluginExceptionConditionAccumulator}.
 */
public class PluginExceptionConditionAccumulatorTest {

  private static final String KEY1 = "k1";

  private static final PluginExceptionCondition ERROR0 = new PluginExceptionCondition(ERROR, "e0");
  private static final PluginExceptionCondition ERROR1 = new PluginExceptionCondition(ERROR, "e1");

  private static final PluginExceptionCondition WARNING0 = new PluginExceptionCondition(WARNING, "w0");
  private static final PluginExceptionCondition WARNING1 = new PluginExceptionCondition(WARNING, "w1");

  private static final List<PluginExceptionCondition> GLOBAL_CONDITIONS = Arrays.asList(
      new PluginExceptionCondition(WARNING1.getType(), WARNING1.getMessage()),
      new PluginExceptionCondition(ERROR1.getType(), ERROR1.getMessage()),
      new PluginExceptionCondition(WARNING0.getType(), WARNING0.getMessage()));

  private static final List<PluginExceptionCondition> KEY1_CONDITIONS = Arrays.asList(
      new PluginExceptionCondition(ERROR0.getType(), ERROR0.getMessage()),
      new PluginExceptionCondition(WARNING1.getType(), WARNING1.getMessage()),
      new PluginExceptionCondition(ERROR1.getType(), ERROR1.getMessage()),
      new PluginExceptionCondition(WARNING0.getType(), WARNING0.getMessage()));

  private static final Map<String, Collection<PluginExceptionCondition>> EXPECTED_CONDITIONS_BY_KEY;

  static {
    Map<String, Collection<PluginExceptionCondition>> map =
        new HashMap<String, Collection<PluginExceptionCondition>>();
    map.put(null, GLOBAL_CONDITIONS);
    map.put(KEY1, KEY1_CONDITIONS);
    EXPECTED_CONDITIONS_BY_KEY = map;
  }

  @Test
  public void testAccumulator() {
    PluginExceptionConditionAccumulator accumulator = new PluginExceptionConditionAccumulator();
    assertThat(accumulator.getConditionsByKey()).isEmpty();
    accumulator.addError(KEY1, ERROR0.getMessage());
    accumulator.addWarning(KEY1, WARNING1.getMessage());
    accumulator.addWarning(null, WARNING1.getMessage());
    accumulator.addError(null, ERROR1.getMessage());
    accumulator.addError(KEY1, ERROR1.getMessage());
    accumulator.addWarning(KEY1, WARNING0.getMessage());
    accumulator.addWarning(null, WARNING0.getMessage());
    assertThat(accumulator.getConditionsByKey()).isEqualTo(EXPECTED_CONDITIONS_BY_KEY);
  }

  @Test
  public void testAccumulatorTypes() {
    PluginExceptionConditionAccumulator accumulator = new PluginExceptionConditionAccumulator();

    assertThat(accumulator.hasWarning()).isFalse();
    assertThat(accumulator.hasError()).isFalse();

    accumulator.addWarning("key", "warning");
    assertThat(accumulator.hasWarning()).isTrue();
    assertThat(accumulator.hasError()).isFalse();

    accumulator.addError(null, "error");
    assertThat(accumulator.hasWarning()).isTrue();
    assertThat(accumulator.hasError()).isTrue();
  }
}
