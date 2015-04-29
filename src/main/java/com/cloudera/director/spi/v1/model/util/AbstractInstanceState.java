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

import com.cloudera.director.spi.v1.model.InstanceState;
import com.cloudera.director.spi.v1.model.InstanceStatus;
import com.cloudera.director.spi.v1.model.LocalizationContext;

/**
 * Abstract base class for instance state implementations.
 */
public class AbstractInstanceState<T> implements InstanceState {

  /**
   * The instance status.
   */
  private final InstanceStatus instanceStatus;

  /**
   * The provider-specific instance state details.
   */
  private final T instanceStateDetails;

  /**
   * Creates an abstract instance state with the specified parameters.
   *
   * @param instanceStatus       the instance status
   * @param instanceStateDetails the provider-specific instance state details
   */
  public AbstractInstanceState(InstanceStatus instanceStatus, T instanceStateDetails) {
    if (instanceStatus == null) {
      throw new NullPointerException("instanceStatus is null");
    }
    this.instanceStatus = instanceStatus;
    this.instanceStateDetails = instanceStateDetails;
  }

  @Override
  public InstanceStatus getInstanceStatus() {
    return instanceStatus;
  }

  @Override
  public T unwrap() {
    return instanceStateDetails;
  }

  @Override
  // NOTE: This implementation does not do any actual localization, and simply calls toString()
  // on the instance state details. Plugin implementors may override to perform localization.
  public String getInstanceStateDescription(LocalizationContext localizationContext) {
    return instanceStateDetails.toString();
  }
}
