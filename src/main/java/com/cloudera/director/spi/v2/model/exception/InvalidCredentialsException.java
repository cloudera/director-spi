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

/**
 * Used to signal that supplied credential information does not
 * allow successful authentication.
 */
@SuppressWarnings("UnusedDeclaration")
public class InvalidCredentialsException extends UnrecoverableProviderException {

  private static final long serialVersionUID = 1L;

  /**
   * Creates an invalid credentials exception with the specified parameters.
   */
  public InvalidCredentialsException() {
    this(null, null, null);
  }

  /**
   * Creates an invalid credentials exception with the specified parameters.
   *
   * @param message the detail message
   */
  public InvalidCredentialsException(String message) {
    this(message, null, null);
  }

  /**
   * Creates an invalid credentials exception with the specified parameters.
   *
   * @param message the detail message
   * @param cause   the cause, or {@code null} if the cause is unknown
   */
  public InvalidCredentialsException(String message, Throwable cause) {
    this(message, cause, null);
  }

  /**
   * Creates an invalid credentials exception with the specified parameters.
   *
   * @param cause the cause, or {@code null} if the cause is unknown
   */
  public InvalidCredentialsException(Throwable cause) {
    this(null, cause, null);
  }

  /**
   * Creates an invalid credentials exception with the specified parameters.
   *
   * @param message the detail message
   * @param details the exception details
   */
  public InvalidCredentialsException(String message, PluginExceptionDetails details) {
    super(message, null, details);
  }

  /**
   * Creates an invalid credentials exception with the specified parameters.
   *
   * @param message the detail message
   * @param cause   the cause, or {@code null} if the cause is unknown
   * @param details the exception details
   */
  public InvalidCredentialsException(String message, Throwable cause,
      PluginExceptionDetails details) {
    super(message, cause, details);
  }
}
