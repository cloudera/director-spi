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

package com.cloudera.director.spi.v1.model.util;

import static com.cloudera.director.spi.v1.util.Preconditions.checkNotNull;

import com.cloudera.director.spi.v1.model.InstanceState;
import com.cloudera.director.spi.v1.model.InstanceStatus;

import java.util.Locale;

/**
 * A simple wrapper around {@code InstanceStatus} for instance state
 */
public class SimpleInstanceState implements InstanceState {

  /**
   * The instance status.
   */
  private final InstanceStatus instanceStatus;

  /**
   * Creates an abstract instance state with the specified parameters.
   *
   * @param instanceStatus the instance status
   */
  public SimpleInstanceState(InstanceStatus instanceStatus) {
    this.instanceStatus = checkNotNull(instanceStatus, "instanceStatus is null");
  }

  @Override
  public InstanceStatus getInstanceStatus() {
    return instanceStatus;
  }

  @Override
  // NOTE: This implementation does not do any actual localization, and simply calls toString()
  // on the instance status details. Plugin implementers may override to perform localization.
  public String getInstanceStateDescription(Locale locale) {
    return instanceStatus.toString();
  }

  @Override
  public Object unwrap() {
    return null;
  }
}
