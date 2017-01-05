package com.cloudera.director.spi.v2.model.exception;

import static com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition.Type.ERROR;
import static com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition.Type.WARNING;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Test;

/**
 * Tests {@link PluginExceptionDetails}.
 */
public class PluginExceptionDetailsTest {

  private static final String KEY1 = "k1";
  private static final String KEY2 = "k2";

  private static final PluginExceptionCondition ERROR1 = new PluginExceptionCondition(ERROR, "e1");
  private static final PluginExceptionCondition ERROR2 = new PluginExceptionCondition(ERROR, "e0");
  private static final PluginExceptionCondition ERROR3 = new PluginExceptionCondition(ERROR, "e2");

  private static final List<PluginExceptionCondition> ERROR_CONDITIONS = Arrays.asList(
      ERROR1,
      ERROR2,
      ERROR3);

  private static final PluginExceptionCondition WARNING1 = new PluginExceptionCondition(WARNING, "w1");
  private static final PluginExceptionCondition WARNING2 = new PluginExceptionCondition(WARNING, "w0");
  private static final PluginExceptionCondition WARNING3 = new PluginExceptionCondition(WARNING, "w1");

  private static final List<PluginExceptionCondition> WARNING_CONDITIONS = Arrays.asList(
      WARNING1,
      WARNING2,
      WARNING3);

  private static final Map<String, Collection<PluginExceptionCondition>> CONDITIONS_BY_KEY;

  static {
    Map<String, Collection<PluginExceptionCondition>> map =
        new HashMap<String, Collection<PluginExceptionCondition>>();
    map.put(null, WARNING_CONDITIONS);
    List<PluginExceptionCondition> conditions = new ArrayList<PluginExceptionCondition>();
    conditions.addAll(WARNING_CONDITIONS);
    conditions.addAll(ERROR_CONDITIONS);
    map.put(KEY1, conditions);
    map.put(KEY2, null);
    CONDITIONS_BY_KEY = map;
  }

  private static final Set<PluginExceptionCondition> EXPECTED_KEY_CONDITIONS =
      new HashSet<PluginExceptionCondition>(
          Arrays.asList(ERROR1, ERROR2, ERROR3, WARNING1, WARNING2, WARNING3));

  private static final Set<PluginExceptionCondition> EXPECTED_GLOBAL_CONDITIONS =
      new HashSet<PluginExceptionCondition>(Arrays.asList(WARNING1, WARNING2, WARNING3));

  @Test
  public void testConstructor() {
    PluginExceptionDetails pluginExceptionDetails = new PluginExceptionDetails(CONDITIONS_BY_KEY);
    Map<String, SortedSet<PluginExceptionCondition>> conditionsByKey =
        pluginExceptionDetails.getConditionsByKey();

    SortedSet<PluginExceptionCondition> conditions = conditionsByKey.get(KEY1);
    assertThat(conditions).isEqualTo(EXPECTED_KEY_CONDITIONS);

    conditions = conditionsByKey.get(KEY2);
    assertThat(conditions).isEmpty();

    conditions = conditionsByKey.get(null);
    assertThat(conditions).isEqualTo(EXPECTED_GLOBAL_CONDITIONS);
  }
}
