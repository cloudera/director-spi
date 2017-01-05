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

package com.cloudera.director.spi.v2.model.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudera.director.spi.v2.model.DisplayProperty;

import org.junit.Test;

/**
 * Tests {@link public class AbstractDisplayProperty}.
 */
public class AbstractDisplayPropertyTest {

  private static class TestDisplayProperty extends AbstractDisplayProperty {

    private TestDisplayProperty(String displayKey, Type type, String name,
        Widget widget, String defaultDescription,
        boolean sensitive, boolean hidden) {
      super(displayKey, type, name, widget, defaultDescription, sensitive, hidden);
    }
  }

  @Test
  public void testGeneral() {
    TestDisplayProperty testDisplayProperty = new TestDisplayProperty(
        "displayKey", DisplayProperty.Type.STRING, "name",
        DisplayProperty.Widget.TEXT, "defaultDescription",
        false, false);
    assertThat(testDisplayProperty.getDisplayKey()).isEqualTo("displayKey");
    assertThat(testDisplayProperty.getType()).isEqualTo(DisplayProperty.Type.STRING);
    assertThat(testDisplayProperty.getName()).isEqualTo("name");
    assertThat(testDisplayProperty.getWidget()).isEqualTo(DisplayProperty.Widget.TEXT);
    assertThat(testDisplayProperty.getDefaultDescription()).isEqualTo("defaultDescription");
    assertThat(testDisplayProperty.isSensitive()).isEqualTo(false);
    assertThat(testDisplayProperty.isHidden()).isEqualTo(false);
  }

  @Test
  public void testNullParameters() {
    new TestDisplayProperty(
        "displayKey", null, null, null, "defaultDescription", false,
        false);
  }

  @Test(expected = NullPointerException.class)
  public void testDisplayKey_nullNotAllowed() {
    new TestDisplayProperty(null, DisplayProperty.Type.STRING, "name",
        DisplayProperty.Widget.TEXT, "defaultDescription",
        false, false);
  }

  @Test(expected = NullPointerException.class)
  public void testDefaultDescription_nullNotAllowed() {
    new TestDisplayProperty("displayKey", DisplayProperty.Type.STRING, "name",
        DisplayProperty.Widget.TEXT, null,
        false, false);
  }
}
