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

package com.cloudera.director.spi.v1.model;

/**
 * <p>Represents the status of an instance in a provider-agnostic way.</p>
 * <p/>
 * <p>Note that for a given provider, some statuses may be unreachable, and some may correspond to
 * multiple distinct provider-specific instance states.</p>
 */
public enum InstanceStatus {

  /**
   * The instance has been requested (initially or after being stopped), but is not yet known to be available.
   */
  PENDING,

  /**
   * The instance is running, although it may not be accessible.
   */
  RUNNING,

  /**
   * The instance is stopping, and may be restarted later.
   */
  STOPPING,

  /**
   * The instance is stopped, and may be restarted.
   */
  STOPPED,

  /**
   * The instance is being removed permanently.
   */
  DELETING,

  /**
   * The instance has been removed permanently.
   */
  DELETED,

  /**
   * The instance has failed, and cannot be reused.
   */
  FAILED,

  /**
   * The instance state cannot be determined.
   */
  UNKNOWN
}
