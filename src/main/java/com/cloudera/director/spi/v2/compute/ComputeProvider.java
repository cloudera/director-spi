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

package com.cloudera.director.spi.v2.compute;

import com.cloudera.director.spi.v2.provider.InstanceProvider;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Represents a provider of compute instances.
 *
 * @param <R> type of the instance created by this compute provider
 * @param <T> type of the template needed to create an instance
 */
public interface ComputeProvider<R extends ComputeInstance<T>, T extends ComputeInstanceTemplate>
    extends InstanceProvider<R, T> {

  /**
   * Returns a map from instance identifiers to a list of host key fingerprints for the specified
   * instances. The implementation can return an empty map to indicate that it cannot find the host
   * key fingerprints or that it does not support retrieving host key fingerprints. In that case
   * Director may use other means of host key fingerprint retrieval or may skip host key
   * verification. It may also return a partial map for the fingerprints that it managed to
   * find.
   *
   * @param template    the resource template used to create the instance
   * @param instanceIds the unique identifiers for the instances
   * @return the map from instance identifiers to host key fingerprints for each instance
   * @throws InterruptedException if the operation is interrupted
   */
  Map<String, List<String>> getHostKeyFingerprints(T template, Collection<String> instanceIds)
      throws InterruptedException;
}
