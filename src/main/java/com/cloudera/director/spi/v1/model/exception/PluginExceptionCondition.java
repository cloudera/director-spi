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

/**
 * Represents an error or warning that contributed to an exception.
 */
@SuppressWarnings("UnusedDeclaration")
public final class PluginExceptionCondition implements Comparable<PluginExceptionCondition>,
    Serializable {

  private static final long serialVersionUID = 1L;

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
   * The message.
   */
  private final String message;

  /**
   * Creates a plugin exception condition with the specified parameters.
   *
   * @param type    the type of condition
   * @param message the message
   */
  public PluginExceptionCondition(Type type, String message) {
    this.type = checkNotNull(type, "type is null");
    this.message = checkNotNull(message, "message is null");
    if (message.isEmpty()) {
      throw new IllegalArgumentException("message is empty");
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
    return message;
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
    if (!message.equals(that.message)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = type.hashCode();
    result = 31 * result + message.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "PluginExceptionCondition{" +
        "type=" + type +
        ", message='" + message + '\'' +
        '}';
  }

  @SuppressWarnings({"NullableProblems", "PMD.UselessParentheses"})
  @Override
  public int compareTo(PluginExceptionCondition o) {
    int result = type.compareTo(o.type);
    if (result == 0) {
      result = message.compareTo(o.message);
    }
    return result;
  }
}
