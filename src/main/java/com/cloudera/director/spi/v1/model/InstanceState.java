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

import java.util.Locale;

/**
 * Describes the last known state of an instance.
 */
public interface InstanceState {

  /**
   * Returns the instance status.
   *
   * @return the instance status
   */
  InstanceStatus getInstanceStatus();

  /**
   * Returns a human-readable version of the instance state.
   *
   * @param locale the locale
   * @return a human-readable version of the instance state
   */
  String getInstanceStateDescription(Locale locale);

  /**
   * Return the provider specific version of this state object or null
   * <p/>
   * Intended for internal provider use only. The consumer of the API will not try
   * to use this in any way.
   */
  Object unwrap();

}
