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

import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.ConfigurationPropertyValue;
import com.cloudera.director.spi.v2.model.Property;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.assertj.core.util.Lists;
import org.junit.Test;

/**
 * Tests {@link public class AbstractConfigurationProperty}.
 */
public class AbstractConfigurationPropertyTest {

  private static class TestConfigurationProperty extends AbstractConfigurationProperty {

    private TestConfigurationProperty(String configKey, Type type, String name, boolean required,
        Widget widget, String defaultValue, String defaultDescription, String defaultErrorMessage,
        List<String> validValues, boolean sensitive, boolean hidden) {
      super(configKey, type, name, required, widget, defaultValue, defaultDescription, defaultErrorMessage,
          validValues, sensitive, hidden);
    }
  }

  private static class TestLocalizer extends AbstractLocalizationContext {

    protected TestLocalizer(Locale locale, String keyPrefix) {
      super(locale, keyPrefix);
    }

    @Override
    public String localize(String defaultValue, String... keyComponents) {
      if (defaultValue.equals("testVal1")) {
        return "testLabel1";
      } else if (defaultValue.equals("testVal2")) {
        return "testLabel2";
      }
      return "Unknown";
    }
  }

  @Test
  public void testGeneral() {
    TestConfigurationProperty testConfigurationProperty = new TestConfigurationProperty(
        "configKey", Property.Type.STRING, "name", false,
        ConfigurationProperty.Widget.TEXT, "defaultValue", "defaultDescription",
        "defaultErrorMessage", Collections.<String>emptyList(), false, false);
 }

  @Test
  public void testNullParameters() {
    TestConfigurationProperty testConfigurationProperty = new TestConfigurationProperty(
        "configKey", null, null, false, null, null, "defaultDescription", null,  null, false,
        false);
    assert(testConfigurationProperty.getValidValues()).isEmpty();
  }

  @Test(expected = NullPointerException.class)
  public void testConfigKey_nullNotAllowed() {
    new TestConfigurationProperty(null, Property.Type.STRING, "name", false,
        ConfigurationProperty.Widget.TEXT, "defaultValue", "defaultDescription",
        "defaultErrorMessage", Collections.<String>emptyList(), false, false);
  }

  @Test(expected = NullPointerException.class)
  public void testDefaultDescription_nullNotAllowed() {
    new TestConfigurationProperty("configKey", Property.Type.STRING, "name", false,
        ConfigurationProperty.Widget.TEXT, "defaultValue", null, "defaultErrorMessage",
        Collections.<String>emptyList(), false, false);
  }

  @Test
  public void testValidValueLocalization() {
    List<String> validValues = Lists.newArrayList();
    validValues.add("testVal1");
    validValues.add("testVal2");
    validValues.add("testVal3");

    TestConfigurationProperty testConfigurationProperty = new TestConfigurationProperty(
        "configKey", Property.Type.STRING, "name", false,
        ConfigurationProperty.Widget.TEXT, "defaultValue", "defaultDescription",
        "defaultErrorMessage", validValues, false, false);

    List<ConfigurationPropertyValue> localizedValues =
        testConfigurationProperty.getValidValues(new TestLocalizer(Locale.getDefault(), ""));

    assertThat(localizedValues).hasSize(3);
    assertThat(localizedValues.get(0).getValue()).isEqualTo("testVal1");
    assertThat(localizedValues.get(0).getLabel()).isEqualTo("testLabel1");
    assertThat(localizedValues.get(1).getValue()).isEqualTo("testVal2");
    assertThat(localizedValues.get(1).getLabel()).isEqualTo("testLabel2");
    assertThat(localizedValues.get(2).getValue()).isEqualTo("testVal3");
    assertThat(localizedValues.get(2).getLabel()).isEqualTo("Unknown");
  }
}
