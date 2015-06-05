// (c) Copyright 2015 Cloudera, Inc.
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

package com.cloudera.director.spi.v1.model.exception;

import static com.cloudera.director.spi.v1.util.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Provides additional exception details to enable better UI feedback when
 * exceptions occur.
 */
public class PluginExceptionDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The default empty map of conditions by key.
   */
  public static final Map<String, ? extends Collection<PluginExceptionCondition>>
      DEFAULT_CONDITIONS_BY_KEY = Collections.emptyMap();

  /**
   * Default plugin exception details.
   */
  public static final PluginExceptionDetails DEFAULT_DETAILS = new PluginExceptionDetails(
      DEFAULT_CONDITIONS_BY_KEY
  );

  /**
   * Builds an unmodifiable map from keys to sorted sets of conditions, based on the specified
   * map from keys to collections of conditions.
   *
   * @param conditionsByKey the conditions contributing to the exception, partitioned by key.
   *                        The {@code null} key represents general conditions not tied to a
   *                        specific key.
   * @return the unmodifiable map from keys to sorted sets of conditions, based on the specified
   * map from keys to collections of conditions
   */
  private static Map<String, SortedSet<PluginExceptionCondition>> buildConditionsByKey(
      Map<String, ? extends Collection<PluginExceptionCondition>> conditionsByKey
  ) {
    checkNotNull(conditionsByKey, "conditionsByKey is null");
    Map<String, SortedSet<PluginExceptionCondition>> map;
    if (conditionsByKey.isEmpty()) {
      map = Collections.emptyMap();
    } else {
      map = new HashMap<String, SortedSet<PluginExceptionCondition>>();
      for (Map.Entry<String, ? extends Collection<PluginExceptionCondition>> entry
          : conditionsByKey.entrySet()) {
        SortedSet<PluginExceptionCondition> sortedConditions =
            new TreeSet<PluginExceptionCondition>();
        String key = entry.getKey();
        Collection<PluginExceptionCondition> conditions = entry.getValue();
        if (conditions != null) {
          sortedConditions.addAll(conditions);
        }
        map.put(key, Collections.unmodifiableSortedSet(sortedConditions));
      }
      map = Collections.unmodifiableMap(map);
    }
    return map;
  }

  /**
   * The conditions contributing to the exception, partitioned by key and
   * ordered by condition. The {@code null} key represents general conditions
   * not tied to a specific key.
   */
  private final Map<String, SortedSet<PluginExceptionCondition>> conditionsByKey;

  /**
   * Creates plugin exception details with the specified parameters.
   *
   * @param conditionsByKey the conditions contributing to the exception, partitioned by key.
   *                        The {@code null} key represents general conditions not tied to a
   *                        specific key.
   */
  public PluginExceptionDetails(
      Map<String, ? extends Collection<PluginExceptionCondition>> conditionsByKey) {
    this.conditionsByKey = buildConditionsByKey(conditionsByKey);
  }

  /**
   * Returns the conditions contributing to the exception, partitioned by key and
   * ordered by condition. The {@code null} key represents general conditions
   * not tied to a specific key.
   *
   * @return the conditions contributing to the exception, partitioned by key and
   * ordered by condition. The {@code null} key represents general conditions
   * not tied to a specific key.
   */
  public Map<String, SortedSet<PluginExceptionCondition>> getConditionsByKey() {
    return conditionsByKey;
  }
}
