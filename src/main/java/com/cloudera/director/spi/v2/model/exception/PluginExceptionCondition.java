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

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents an error or warning that contributed to an exception. A condition
 * has an enumerated type and a map of information about the underlying
 * exception.<p>
 *
 * The map should have a "message" key containing a non-empty message
 * drawn from the exception or some underlying cause. A default message is
 * provided if no message is available (either null or empty).
 */
public final class PluginExceptionCondition implements Comparable<PluginExceptionCondition>,
    Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The key for the exception message in the additional exception information.
   */
  public static final String KEY_MESSAGE = "message";

  /**
   * The default message for conditions where the corresponding exception lacks
   * a message.
   */
  public static final String DEFAULT_MESSAGE = "Message not available";

  /**
   * The type of condition.
   */
  public static enum Type {

    /**
     * Represents an error.
     */
    ERROR,

    /**
     * Represents a warning.
     */
    WARNING
  }

  /**
   * The type of condition.
   */
  private final Type type;


  /**
   * Key value pairs to provide detailed exception information.
   */
  private final Map<String, String> exceptionInfo;


  /**
   * Returns a map with a single message property. If the message is null, a
   * generic message is placed into the map.
   *
   * @param message the value for the message property
   * @return a map with a single message property
   */
  public static Map<String, String> toExceptionInfoMap(String message) {
    return Collections.singletonMap(KEY_MESSAGE, (message == null || message.isEmpty() ? DEFAULT_MESSAGE : message));
  }

  /**
   * Creates a plugin exception condition with only a message. If the message
   * is null or empty, a default is substituted.
   *
   * @param type    the type of condition
   * @param message the message
   */
  public PluginExceptionCondition(Type type, String message) {
    this(type, toExceptionInfoMap(message));
  }

  /**
   * Creates a plugin exception condition with detailed exception information.
   * If the detailed exception information does not include a non-empty
   * "message" value, a default is substituted.
   *
   * @param type          the type of condition
   * @param exceptionInfo detailed exception information
   * @throws NullPointerException if type or exceptionInfo is null
   */
  public PluginExceptionCondition(Type type, Map<String, String> exceptionInfo) {
    this.type = requireNonNull(type, "type is null");
    requireNonNull(exceptionInfo, "exceptionInfo is null");
    this.exceptionInfo = new HashMap<>(exceptionInfo);

    if (!this.exceptionInfo.containsKey(KEY_MESSAGE) ||
        this.exceptionInfo.get(KEY_MESSAGE) == null ||
        this.exceptionInfo.get(KEY_MESSAGE).isEmpty()) {
      this.exceptionInfo.put(KEY_MESSAGE, DEFAULT_MESSAGE);
    }
  }

  /**
   * Returns the type of condition.
   *
   * @return the type of condition
   */
  public Type getType() {
    return type;
  }

  /**
   * Returns the message.
   *
   * @return the message
   */
  public String getMessage() {
    return exceptionInfo.get(KEY_MESSAGE);
  }

  /**
   * Get detailed exception information.
   *
   * @return detailed exception information.
   */
  public Map<String, String> getExceptionInfo() {
    return new HashMap<>(exceptionInfo);
  }

  /**
   * Returns whether the condition is an error.
   *
   * @return whether the condition is an error
   */
  public boolean isError() {
    return Type.ERROR == type;
  }

  /**
   * Returns whether the condition is a warning.
   *
   * @return whether the condition is a warning
   */
  public boolean isWarning() {
    return Type.WARNING == type;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    PluginExceptionCondition that = (PluginExceptionCondition) o;

    if (type != that.type) return false;
    return exceptionInfo.equals(that.exceptionInfo);
  }

  @Override
  public int hashCode() {
    int result = type.hashCode();
    result = 31 * result + exceptionInfo.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "PluginExceptionCondition{" +
        "type=" + type +
        ", exceptionInfo=" + exceptionInfo +
        '}';
  }

  @Override
  public int compareTo(PluginExceptionCondition o) {
    int result = type.compareTo(o.type);
    if (result == 0) {
      result = compare(exceptionInfo, o.exceptionInfo);
    }
    return result;
  }

  private int compare(Map<String, String> map1, Map<String, String> map2) {
    TreeMap<String, String> tMap1 = new TreeMap<>(map1);
    TreeMap<String, String> tMap2 = new TreeMap<>(map2);
    return tMap1.toString().compareTo(tMap2.toString());
  }

}
