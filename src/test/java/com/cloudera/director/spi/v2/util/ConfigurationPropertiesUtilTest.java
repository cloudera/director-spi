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

import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.ConfigurationPropertyToken;
import com.cloudera.director.spi.v2.model.util.SimpleConfigurationPropertyBuilder;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;

public class ConfigurationPropertiesUtilTest {

  @Test
  public void testAsConfigurationPropertyList() {
    final ConfigurationProperty config1 = makeConfigProperty("configKey1", "configDesc1");
    final ConfigurationProperty config2 = makeConfigProperty("configKey2", "configDesc2");
    List<ConfigurationProperty> configProperties = Arrays.asList(config1, config2);
    List<ConfigurationPropertyToken> configurationPropertyTokens = Arrays.asList(
        new ConfigurationPropertyToken() {
          @Override
          public ConfigurationProperty unwrap() {
            return config1;
          }
        },
        new ConfigurationPropertyToken() {
          @Override
          public ConfigurationProperty unwrap() {
            return config2;
          }
        }
    );
    assertThat(ConfigurationPropertiesUtil.asConfigurationPropertyList(
        configurationPropertyTokens.toArray(new ConfigurationPropertyToken[2])))
        .isEqualTo(configProperties);
  }

  @Test
  public void testMerge() {
    ConfigurationProperty config1 = makeConfigProperty("configKey1", "configDesc1");
    ConfigurationProperty config2 = makeConfigProperty("configKey2", "configDesc2");
    ConfigurationProperty config3 = makeConfigProperty("configKey3", "configDesc3");

    ConfigurationProperty config4 = makeConfigProperty("configKey4", "configDesc4");
    ConfigurationProperty config5 = makeConfigProperty("configKey5", "configDesc5");
    ConfigurationProperty config6 = makeConfigProperty("configKey6", "configDesc6");

    assertThat(
        ConfigurationPropertiesUtil.merge(Lists.newArrayList(config1, config2, config3),
            Lists.newArrayList(config4, config5, config6)))
        .containsOnly(config1, config2, config3, config4, config5, config6);
  }

  @Test
  public void testMergeReplace() {
    ConfigurationProperty config1 = makeConfigProperty("configKey1", "configDesc1");
    ConfigurationProperty config2 = makeConfigProperty("configKey2", "configDesc2");
    ConfigurationProperty config3 = makeConfigProperty("configKey3", "configDesc3");

    ConfigurationProperty config1b = makeConfigProperty("configKey1", "configDesc3");

    assertThat(
        ConfigurationPropertiesUtil.merge(Lists.newArrayList(config1, config2),
            Lists.newArrayList(config1b, config3)))
        .containsOnly(config1b, config2, config3);
  }

  private ConfigurationProperty makeConfigProperty(String configKey, String description) {
    return new SimpleConfigurationPropertyBuilder()
        .configKey(configKey)
        .defaultDescription(description)
        .build();
  }
}
