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

/**
 * Represents a failure interacting with the cloud provider that is unlikely
 * to be overcome by retrying the operation.
 */
@SuppressWarnings("UnusedDeclaration")
public class UnrecoverableProviderException extends AbstractPluginException {

  private static final long serialVersionUID = 1L;

  /**
   * Creates an unrecoverable provider exception with the specified parameters.
   */
  public UnrecoverableProviderException() {
    this(null, null, null);
  }

  /**
   * Creates an unrecoverable provider exception with the specified parameters.
   *
   * @param message the detail message
   */
  public UnrecoverableProviderException(String message) {
    this(message, null, null);
  }

  /**
   * Creates an unrecoverable provider exception with the specified parameters.
   *
   * @param message the detail message
   * @param cause   the cause, or {@code null} if the cause is unknown
   */
  public UnrecoverableProviderException(String message, Throwable cause) {
    this(message, cause, null);
  }

  /**
   * Creates an unrecoverable provider exception with the specified parameters.
   *
   * @param cause the cause, or {@code null} if the cause is unknown
   */
  public UnrecoverableProviderException(Throwable cause) {
    this(null, cause, null);
  }

  /**
   * Creates an unrecoverable provider exception with the specified parameters.
   *
   * @param message the detail message
   * @param details the exception details
   */
  public UnrecoverableProviderException(String message, PluginExceptionDetails details) {
    this(message, null, details);
  }

  /**
   * Creates an unrecoverable provider exception with the specified parameters.
   *
   * @param message the detail message
   * @param cause   the cause, or {@code null} if the cause is unknown
   * @param details the exception details
   */
  public UnrecoverableProviderException(String message, Throwable cause,
      PluginExceptionDetails details) {
    super(message, cause, details);
  }
}
