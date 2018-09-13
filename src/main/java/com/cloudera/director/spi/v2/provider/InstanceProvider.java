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

package com.cloudera.director.spi.v2.provider;

import com.cloudera.director.spi.v2.model.Instance;
import com.cloudera.director.spi.v2.model.InstanceState;
import com.cloudera.director.spi.v2.model.InstanceTemplate;

import java.util.Collection;
import java.util.Map;

/**
 * Represents a provider of instances.
 */
public interface InstanceProvider<I extends Instance<T>, T extends InstanceTemplate>
    extends ResourceProvider<I, T> {

  /**
   * Returns a map from instance identifiers to instance state for the specified instances.
   *
   * Preconditions:
   * 1. if template.isAutomatic is true, then instanceIds are a collection of provider-specific instance Ids
   * 2. if template.isAutomatic is false, currently the instances are a collection of virtual instance Ids
   *
   * @param template    the resource template used for create the instances
   * @param instanceIds the unique identifiers for the instances
   * @return the map from instance identifiers to instance state for the specified batch of instances
   */
  Map<String, InstanceState> getInstanceState(T template, Collection<String> instanceIds);

}
