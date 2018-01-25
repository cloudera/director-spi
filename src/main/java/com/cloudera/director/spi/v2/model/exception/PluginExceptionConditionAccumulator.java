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

package com.cloudera.director.spi.v2.model.exception;

import static com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition.Type;
import static com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition.Type.ERROR;
import static com.cloudera.director.spi.v2.model.exception.PluginExceptionCondition.Type.WARNING;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Accumulator for plugin exception conditions.
 */
public class PluginExceptionConditionAccumulator {

  /**
   * The conditions contributing to the exception, partitioned by key. The {@code null} key
   * represents general conditions not tied to a specific key.
   */
  private final Map<String, Collection<PluginExceptionCondition>> conditionsByKey;

  /**
   * The condition types observed so far.
   */
  private final Set<Type> conditionTypes;

  /**
   * Creates a plugin exception condition accumulator with the specified parameters.
   */
  public PluginExceptionConditionAccumulator() {
    conditionsByKey = new HashMap<String, Collection<PluginExceptionCondition>>();
    conditionTypes = EnumSet.noneOf(Type.class);
  }

  /**
   * Returns the conditions contributing to the exception, partitioned by key. The {@code null} key
   * represents general conditions not tied to a specific key.
   *
   * @return the conditions contributing to the exception, partitioned by key. The {@code null} key
   * represents general conditions not tied to a specific key.
   */
  public Map<String, Collection<PluginExceptionCondition>> getConditionsByKey() {
    return conditionsByKey;
  }

  /**
   * Returns whether the accumulator has at least one error.
   *
   * @return whether the accumulator has at least one error
   */
  public boolean hasError() {
    return conditionTypes.contains(ERROR);
  }

  /**
   * Adds an error condition with only a message to the accumulator.
   *
   * @param key     the key
   * @param message the message
   */
  public void addError(String key, String message) {
    addCondition(key, ERROR, message);
  }

  /**
   * Adds an error condition with detailed exception information to the accumulator.
   *
   * @param key           the key
   * @param exceptionInfo detailed exception information
   */
  public void addError(String key, Map<String, String> exceptionInfo) {
    addCondition(key, ERROR, exceptionInfo);
  }

  /**
   * Adds a general error condition (using key of null) with detailed exception
   * information to the accumulator.
   *
   * @param exceptionInfo detailed exception information
   */
  public void addError(Map<String, String> exceptionInfo) {
    addCondition(null, ERROR, exceptionInfo);
  }

  /**
   * Returns whether the accumulator has at least one warning.
   *
   * @return whether the accumulator has at least one warning
   */
  public boolean hasWarning() {
    return conditionTypes.contains(WARNING);
  }

  /**
   * Adds a warning condition with only a message to the accumulator.
   *
   * @param key     the key
   * @param message the message
   */
  public void addWarning(String key, String message) {
    addCondition(key, WARNING, message);
  }

  /**
   * Adds a warning condition with detailed exception information to the accumulator.
   *
   * @param key           the key
   * @param exceptionInfo detailed exception information
   */
  public void addWarning(String key, Map<String, String> exceptionInfo) {
    addCondition(key, WARNING, exceptionInfo);
  }

  /**
   * Adds a general warning condition (using key of null) with detailed exception
   * information to the accumulator.
   *
   * @param exceptionInfo detailed exception information
   */
  public void addWarning(Map<String, String> exceptionInfo) {
    addCondition(null, WARNING, exceptionInfo);
  }

  private synchronized void addCondition(String key, Type type, String message) {
    addCondition(key, type, PluginExceptionCondition.toExceptionInfoMap(message));
  }

  private synchronized void addCondition(String key, Type type, Map<String, String> exceptionInfo) {
    Collection<PluginExceptionCondition> keyConditions = conditionsByKey.get(key);
    if (keyConditions == null) {
      keyConditions = new ArrayList<PluginExceptionCondition>();
      conditionsByKey.put(key, keyConditions);
    }
    PluginExceptionCondition condition =
        new PluginExceptionCondition(type, exceptionInfo);
    keyConditions.add(condition);
    conditionTypes.add(type);
  }
}
