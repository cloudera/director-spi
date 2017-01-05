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

package com.cloudera.director.spi.v2.util;

import com.cloudera.director.spi.v2.model.DisplayProperty;
import com.cloudera.director.spi.v2.model.DisplayPropertyToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides utility methods for manipulating display properties.
 */
public final class DisplayPropertiesUtil {

  /**
   * Private constructor to prevent instantiation.
   */
  private DisplayPropertiesUtil() {
  }

  /**
   * Returns an unmodifiable list containing the display properties identified by the
   * specified tokens.
   *
   * @param tokens the display property tokens
   * @param <T>    the type of display property token
   * @return an unmodifiable list containing the specified display properties
   */
  public static <T extends DisplayPropertyToken>
  List<DisplayProperty> asDisplayPropertyList(T[] tokens) {
    int len = tokens.length;
    DisplayProperty[] displayProperties = new DisplayProperty[len];
    for (int i = 0; i < len; i++) {
      displayProperties[i] = tokens[i].unwrap();
    }
    return asList(displayProperties);
  }

  /**
   * Returns an unmodifiable list containing the specified display properties.
   *
   * @param properties the display properties
   * @param <T>        the type of display property
   * @return an unmodifiable list containing the specified display properties
   */
  @SuppressWarnings("unchecked")
  public static <T extends DisplayProperty> List<DisplayProperty> asList(T[] properties) {
    return (List<DisplayProperty>) Collections.unmodifiableList(Arrays.asList(properties));
  }

  /**
   * Returns an unmodifiable sequential composition of the specified lists of display properties.
   * If there are duplicate display keys among the given properties, the last property
   * encountered sequentially will be part of the returned list, and the others will be discarded.
   *
   * @param components the component lists
   * @return an unmodifiable sequential composition of the specified lists of display properties
   */
  public static List<DisplayProperty> merge(List<? extends DisplayProperty>... components) {
    Map<String, DisplayProperty> result = new LinkedHashMap<String, DisplayProperty>();
    for (List<? extends DisplayProperty> part : components) {
      for (DisplayProperty property : part) {
        result.put(property.getDisplayKey(), property);
      }
    }

    return Collections.unmodifiableList(new ArrayList<DisplayProperty>(result.values()));
  }
}
