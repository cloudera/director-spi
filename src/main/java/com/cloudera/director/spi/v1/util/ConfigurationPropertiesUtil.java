/*
 * Copyright (c) 2015 Cloudera, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudera.director.spi.v1.util;

import com.cloudera.director.spi.v1.model.ConfigurationProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ConfigurationPropertiesUtil {

  private ConfigurationPropertiesUtil() {
  }

  @SuppressWarnings("unchecked")
  public static <T extends ConfigurationProperty> List<ConfigurationProperty> asList(T[] properties) {
    return (List<ConfigurationProperty>) Collections.unmodifiableList(Arrays.asList(properties));
  }

  public static List<ConfigurationProperty> merge(List<? extends ConfigurationProperty>... parts) {
    List<ConfigurationProperty> result = new ArrayList<ConfigurationProperty>();
    for (List<? extends ConfigurationProperty> part : parts) {
      result.addAll(part);
    }
    return Collections.unmodifiableList(result);
  }
}
