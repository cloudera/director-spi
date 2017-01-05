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

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudera.director.spi.v2.model.DisplayProperty;
import com.cloudera.director.spi.v2.model.DisplayPropertyToken;
import com.cloudera.director.spi.v2.model.util.SimpleDisplayPropertyBuilder;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;

public class DisplayPropertiesUtilTest {

  @Test
  public void testAsDisplayPropertyList() {
    final DisplayProperty display1 = makeDisplayProperty("displayKey1", "displayDesc1");
    final DisplayProperty display2 = makeDisplayProperty("displayKey2", "displayDesc2");
    List<DisplayProperty> displayProperties = Arrays.asList(display1, display2);
    List<DisplayPropertyToken> displayPropertyTokens = Arrays.asList(
        new DisplayPropertyToken() {
          @Override
          public DisplayProperty unwrap() {
            return display1;
          }
        },
        new DisplayPropertyToken() {
          @Override
          public DisplayProperty unwrap() {
            return display2;
          }
        }
    );
    assertThat(DisplayPropertiesUtil.asDisplayPropertyList(
        displayPropertyTokens.toArray(new DisplayPropertyToken[2]))).isEqualTo(displayProperties);
  }

  @Test
  public void testMerge() {
    DisplayProperty display1 = makeDisplayProperty("displayKey1", "displayDesc1");
    DisplayProperty display2 = makeDisplayProperty("displayKey2", "displayDesc2");
    DisplayProperty display3 = makeDisplayProperty("displayKey3", "displayDesc3");

    DisplayProperty display4 = makeDisplayProperty("displayKey4", "displayDesc4");
    DisplayProperty display5 = makeDisplayProperty("displayKey5", "displayDesc5");
    DisplayProperty display6 = makeDisplayProperty("displayKey6", "displayDesc6");

    assertThat(
        DisplayPropertiesUtil.merge(Lists.newArrayList(display1, display2, display3),
            Lists.newArrayList(display4, display5, display6)))
        .containsOnly(display1, display2, display3, display4, display5, display6);
  }

  @Test
  public void testMergeReplace() {
    DisplayProperty display1 = makeDisplayProperty("displayKey1", "displayDesc1");
    DisplayProperty display2 = makeDisplayProperty("displayKey2", "displayDesc2");
    DisplayProperty display3 = makeDisplayProperty("displayKey3", "displayDesc3");

    DisplayProperty display1b = makeDisplayProperty("displayKey1", "displayDesc3");

    assertThat(
        DisplayPropertiesUtil.merge(Lists.newArrayList(display1, display2),
            Lists.newArrayList(display1b, display3)))
        .containsOnly(display1b, display2, display3);
  }

  private DisplayProperty makeDisplayProperty(String displayKey, String description) {
    return new SimpleDisplayPropertyBuilder()
        .displayKey(displayKey)
        .defaultDescription(description)
        .build();
  }
}
