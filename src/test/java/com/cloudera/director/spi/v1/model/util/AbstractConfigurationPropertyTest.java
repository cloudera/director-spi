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

import com.cloudera.director.spi.v1.model.ConfigurationProperty;

import org.junit.Test;

/**
 * Tests {@link public class AbstractConfigurationProperty}.
 */
public class AbstractConfigurationPropertyTest {

  private static class TestConfigurationProperty extends AbstractConfigurationProperty {

    private TestConfigurationProperty(String configKey, Type type, String name, boolean required,
        Widget widget, String defaultValue, String defaultDescription, String defaultErrorMessage,
        boolean sensitive) {
      super(configKey, type, name, required, widget, defaultValue, defaultDescription, defaultErrorMessage,
          sensitive);
    }
  }

  @Test
  public void testGeneral() {
    TestConfigurationProperty testConfigurationProperty = new TestConfigurationProperty(
        "configKey", ConfigurationProperty.Type.STRING, "name", false,
        ConfigurationProperty.Widget.TEXT, "defaultValue", "defaultDescription",
        "defaultErrorMessage", false);
 }

  @Test
  public void testNullParameters() {
    TestConfigurationProperty testConfigurationProperty = new TestConfigurationProperty(
        "configKey", null, null, false, null, null, "defaultDescription", null, false);
  }

  @Test(expected = NullPointerException.class)
  public void testConfigKey_nullNotAllowed() {
    new TestConfigurationProperty(null, ConfigurationProperty.Type.STRING, "name", false,
        ConfigurationProperty.Widget.TEXT, "defaultValue", "defaultDescription",
        "defaultErrorMessage", false);
  }

  @Test(expected = NullPointerException.class)
  public void testDefaultDescription_nullNotAllowed() {
    new TestConfigurationProperty("configKey", ConfigurationProperty.Type.STRING, "name", false,
        ConfigurationProperty.Widget.TEXT, "defaultValue", null, "defaultErrorMessage", false);
  }
}
