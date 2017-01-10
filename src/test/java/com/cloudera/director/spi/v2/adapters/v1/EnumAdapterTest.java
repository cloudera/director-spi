// (c) Copyright 2017 Cloudera, Inc.
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

package com.cloudera.director.spi.v2.adapters.v1;

import com.cloudera.director.spi.v1.compute.VirtualizationType;
import com.cloudera.director.spi.v1.model.ConfigurationProperty;
import com.cloudera.director.spi.v1.model.InstanceStatus;
import com.cloudera.director.spi.v1.model.DisplayProperty;
import com.cloudera.director.spi.v1.model.Property;
import com.google.common.base.Function;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumAdapterTest {

  @Test
  public void testInstanceStatusFromV1() {
    testEnum(
        InstanceStatus.values(),
        new Function<InstanceStatus, com.cloudera.director.spi.v2.model.InstanceStatus>() {
          @Override
          public com.cloudera.director.spi.v2.model.InstanceStatus apply(InstanceStatus original) {
            return EnumAdapter.fromV1(original);
          }
        }
    );
  }

  @Test
  public void testDisplayPropertyWidgetFromV1() {
    testEnum(
        DisplayProperty.Widget.values(),
        new Function<DisplayProperty.Widget, com.cloudera.director.spi.v2.model.DisplayProperty.Widget>() {
          @Override
          public com.cloudera.director.spi.v2.model.DisplayProperty.Widget apply(DisplayProperty.Widget original) {
            return EnumAdapter.fromV1(original);
          }
        }
    );
  }

  @Test
  public void testPropertyTypeFromV1() {
    testEnum(
        Property.Type.values(),
        new Function<Property.Type, com.cloudera.director.spi.v2.model.Property.Type>() {
          @Override
          public com.cloudera.director.spi.v2.model.Property.Type apply(Property.Type original) {
            return EnumAdapter.fromV1(original);
          }
        }
    );
  }

  @Test
  public void testConfigurationPropertyWidgetFromV1() {
    testEnum(
        ConfigurationProperty.Widget.values(),
        new Function<ConfigurationProperty.Widget, com.cloudera.director.spi.v2.model.ConfigurationProperty.Widget>() {
          @Override
          public com.cloudera.director.spi.v2.model.ConfigurationProperty.Widget apply(ConfigurationProperty.Widget original) {
            return EnumAdapter.fromV1(original);
          }
        }
    );
  }

  @Test
  public void testVirtualizationTypeFromV1() {
    testEnum(
        VirtualizationType.values(),
        new Function<VirtualizationType, com.cloudera.director.spi.v2.compute.VirtualizationType>() {
          @Override
          public com.cloudera.director.spi.v2.compute.VirtualizationType apply(VirtualizationType original) {
            return EnumAdapter.fromV1(original);
          }
        }
    );
  }

  @Test
  public void testInstanceStatusToV1() {
    testEnum(
        com.cloudera.director.spi.v2.model.InstanceStatus.values(),
        new Function<com.cloudera.director.spi.v2.model.InstanceStatus, InstanceStatus>() {
          @Override
          public InstanceStatus apply(com.cloudera.director.spi.v2.model.InstanceStatus original) {
            return EnumAdapter.toV1(original);
          }
        }
    );
  }

  @Test
  public void testConfigurationPropertyWidgetToV1() {
    testEnum(
        com.cloudera.director.spi.v2.model.ConfigurationProperty.Widget.values(),
        new Function<com.cloudera.director.spi.v2.model.ConfigurationProperty.Widget, ConfigurationProperty.Widget>() {
          @Override
          public ConfigurationProperty.Widget apply(
              com.cloudera.director.spi.v2.model.ConfigurationProperty.Widget original) {
            return EnumAdapter.toV1(original);
          }
        }
    );
  }

  @Test
  public void testDisplayPropertyWidgetToV1() {
    testEnum(
        com.cloudera.director.spi.v2.model.DisplayProperty.Widget.values(),
        new Function<com.cloudera.director.spi.v2.model.DisplayProperty.Widget, DisplayProperty.Widget>() {
          @Override
          public DisplayProperty.Widget apply(com.cloudera.director.spi.v2.model.DisplayProperty.Widget original) {
            return EnumAdapter.toV1(original);
          }
        }
    );
  }

  @Test
  public void testPropertyTypeToV1() {
    testEnum(
        com.cloudera.director.spi.v2.model.Property.Type.values(),
        new Function<com.cloudera.director.spi.v2.model.Property.Type, Property.Type>() {
          @Override
          public Property.Type apply(com.cloudera.director.spi.v2.model.Property.Type original) {
            return EnumAdapter.toV1(original);
          }
        }
    );
  }

  @Test
  public void testVirtualizationTypeToV1() {
    testEnum(
        com.cloudera.director.spi.v2.compute.VirtualizationType.values(),
        new Function<com.cloudera.director.spi.v2.compute.VirtualizationType, VirtualizationType>() {
          @Override
          public VirtualizationType apply(com.cloudera.director.spi.v2.compute.VirtualizationType original) {
            return EnumAdapter.toV1(original);
          }
        }
    );
  }

  private <E1 extends Enum, E2 extends Enum> void testEnum(E1[] enum1, Function<E1, E2> adapterFn) {
    for (E1 original : enum1) {
      E2 converted = adapterFn.apply(original);
      assertThat(original.ordinal()).isEqualTo(converted.ordinal());
      assertThat(original.name()).isEqualTo(converted.name());
      assertThat(original.toString()).isEqualTo(converted.toString());
    }
  }
}
