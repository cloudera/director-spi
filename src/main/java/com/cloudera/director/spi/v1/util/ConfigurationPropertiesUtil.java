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
import com.cloudera.director.spi.v1.model.ConfigurationPropertyToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Provides utility methods for manipulating configuration properties.
 */
public final class ConfigurationPropertiesUtil {

  /**
   * Private constructor to prevent instantiation.
   */
  private ConfigurationPropertiesUtil() {
  }

  /**
   * Returns an unmodifiable list containing the configuration properties identified by the
   * specified tokens.
   *
   * @param tokens the configuration property tokens
   * @param <T>    the type of configuration property token
   * @return an unmodifiable list containing the specified configuration properties
   */
  public static <T extends ConfigurationPropertyToken>
  List<ConfigurationProperty> asConfigurationPropertyList(T[] tokens) {
    int len = tokens.length;
    ConfigurationProperty[] configurationProperties = new ConfigurationProperty[len];
    for (int i = 0; i < len; i++) {
      configurationProperties[i] = tokens[i].unwrap();
    }
    return asList(configurationProperties);
  }

  /**
   * Returns an unmodifiable list containing the specified configuration properties.
   *
   * @param properties the configuration properties
   * @param <T>        the type of configuration property
   * @return an unmodifiable list containing the specified configuration properties
   */
  @SuppressWarnings("unchecked")
  public static <T extends ConfigurationProperty> List<ConfigurationProperty> asList(T[] properties) {
    return (List<ConfigurationProperty>) Collections.unmodifiableList(Arrays.asList(properties));
  }

  /**
   * Returns an unmodifiable sequential composition of the specified lists of configuration properties.
   *
   * @param components the component lists
   * @return an unmodifiable sequential composition of the specified lists of configuration properties
   */
  public static List<ConfigurationProperty> merge(List<? extends ConfigurationProperty>... components) {
    List<ConfigurationProperty> result = new ArrayList<ConfigurationProperty>();
    for (List<? extends ConfigurationProperty> part : components) {
      result.addAll(part);
    }
    return Collections.unmodifiableList(result);
  }
}
