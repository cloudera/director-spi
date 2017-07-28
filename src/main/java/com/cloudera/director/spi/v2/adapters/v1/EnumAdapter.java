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

import com.cloudera.director.spi.v2.compute.VirtualizationType;
import com.cloudera.director.spi.v2.model.ConfigurationProperty;
import com.cloudera.director.spi.v2.model.DisplayProperty;
import com.cloudera.director.spi.v2.model.InstanceStatus;
import com.cloudera.director.spi.v2.model.Property;
import com.cloudera.director.spi.v2.util.Preconditions;

/**
 * Contains functions to convert the SPI enum objects from V1 to V2 and vice versa.
 */
public class EnumAdapter {

  private EnumAdapter() {
  }

  // Functions to convert V1 -> V2

  public static InstanceStatus fromV1(com.cloudera.director.spi.v1.model.InstanceStatus instanceStatus) {
    switch (instanceStatus) {
      case PENDING:
        return InstanceStatus.PENDING;
      case RUNNING:
        return InstanceStatus.RUNNING;
      case STOPPING:
        return InstanceStatus.STOPPING;
      case STOPPED:
        return InstanceStatus.STOPPED;
      case DELETING:
        return InstanceStatus.DELETING;
      case DELETED:
        return InstanceStatus.DELETED;
      case FAILED:
        return InstanceStatus.FAILED;
      case UNKNOWN:
        return InstanceStatus.UNKNOWN;
      default:
        throw new IllegalArgumentException("Unknown instanceStatus: " + instanceStatus);
    }
  }

  public static DisplayProperty.Widget fromV1(
      com.cloudera.director.spi.v1.model.DisplayProperty.Widget widget) {
    Preconditions.checkNotNull(widget, "widget is null");
    switch (widget) {
      case RADIO:
        return DisplayProperty.Widget.RADIO;
      case CHECKBOX:
        return DisplayProperty.Widget.CHECKBOX;
      case TEXT:
        return DisplayProperty.Widget.TEXT;
      case TEXTAREA:
        return DisplayProperty.Widget.TEXTAREA;
      case FILE:
        return DisplayProperty.Widget.FILE;
      case MULTI:
        return DisplayProperty.Widget.MULTI;
      default:
        throw new IllegalArgumentException("Unknown widget: " + widget);
    }
  }

  public static Property.Type fromV1(
      com.cloudera.director.spi.v1.model.Property.Type type) {
    Preconditions.checkNotNull(type, "type is null");
    switch (type) {
      case BOOLEAN:
        return Property.Type.BOOLEAN;
      case INTEGER:
        return Property.Type.INTEGER;
      case DOUBLE:
        return Property.Type.DOUBLE;
      case STRING:
        return Property.Type.STRING;
      default:
        throw new IllegalArgumentException("Unknown type: " + type);
    }
  }

  public static ConfigurationProperty.Widget fromV1(
      com.cloudera.director.spi.v1.model.ConfigurationProperty.Widget widget) {
    Preconditions.checkNotNull(widget, "widget is null");
    switch (widget) {
      case RADIO:
        return ConfigurationProperty.Widget.RADIO;
      case CHECKBOX:
        return ConfigurationProperty.Widget.CHECKBOX;
      case TEXT:
        return ConfigurationProperty.Widget.TEXT;
      case PASSWORD:
        return ConfigurationProperty.Widget.PASSWORD;
      case NUMBER:
        return ConfigurationProperty.Widget.NUMBER;
      case TEXTAREA:
        return ConfigurationProperty.Widget.TEXTAREA;
      case FILE:
        return ConfigurationProperty.Widget.FILE;
      case LIST:
        return ConfigurationProperty.Widget.LIST;
      case OPENLIST:
        return ConfigurationProperty.Widget.OPENLIST;
      case MULTI:
        return ConfigurationProperty.Widget.MULTI;
      case OPENMULTI:
        return ConfigurationProperty.Widget.OPENMULTI;
      default:
        throw new IllegalArgumentException("Unknown widget: " + widget);
    }
  }

  public static VirtualizationType fromV1(
      com.cloudera.director.spi.v1.compute.VirtualizationType virtualizationType) {
    if (virtualizationType == null) return null;
    switch (virtualizationType) {
      case PARAVIRTUALIZATION:
        return VirtualizationType.PARAVIRTUALIZATION;
      case HARDWARE_ASSISTED:
        return VirtualizationType.HARDWARE_ASSISTED;
      case UNKNOWN:
        return VirtualizationType.UNKNOWN;
      default:
        throw new IllegalArgumentException("Unknown virtualizationType: " + virtualizationType);
    }
  }

  // Functions to convert V1 -> V2

  public static com.cloudera.director.spi.v1.model.InstanceStatus toV1(final InstanceStatus instanceStatus) {
    Preconditions.checkNotNull(instanceStatus, "instanceStatus is null");
    switch (instanceStatus) {
      case PENDING:
        return com.cloudera.director.spi.v1.model.InstanceStatus.PENDING;
      case RUNNING:
        return com.cloudera.director.spi.v1.model.InstanceStatus.RUNNING;
      case STOPPING:
        return com.cloudera.director.spi.v1.model.InstanceStatus.STOPPING;
      case STOPPED:
        return com.cloudera.director.spi.v1.model.InstanceStatus.STOPPED;
      case DELETING:
        return com.cloudera.director.spi.v1.model.InstanceStatus.DELETING;
      case DELETED:
        return com.cloudera.director.spi.v1.model.InstanceStatus.DELETED;
      case FAILED:
        return com.cloudera.director.spi.v1.model.InstanceStatus.FAILED;
      case UNKNOWN:
        return com.cloudera.director.spi.v1.model.InstanceStatus.UNKNOWN;
      default:
        throw new IllegalArgumentException("Unknown instanceStatus: " + instanceStatus);
    }
  }

  public static com.cloudera.director.spi.v1.model.ConfigurationProperty.Widget toV1(
      ConfigurationProperty.Widget widget) {
    Preconditions.checkNotNull(widget, "widget is null");
    switch (widget) {
      case RADIO:
        return com.cloudera.director.spi.v1.model.ConfigurationProperty.Widget.RADIO;
      case CHECKBOX:
        return com.cloudera.director.spi.v1.model.ConfigurationProperty.Widget.CHECKBOX;
      case TEXT:
        return com.cloudera.director.spi.v1.model.ConfigurationProperty.Widget.TEXT;
      case PASSWORD:
        return com.cloudera.director.spi.v1.model.ConfigurationProperty.Widget.PASSWORD;
      case NUMBER:
        return com.cloudera.director.spi.v1.model.ConfigurationProperty.Widget.NUMBER;
      case TEXTAREA:
        return com.cloudera.director.spi.v1.model.ConfigurationProperty.Widget.TEXTAREA;
      case FILE:
        return com.cloudera.director.spi.v1.model.ConfigurationProperty.Widget.FILE;
      case LIST:
        return com.cloudera.director.spi.v1.model.ConfigurationProperty.Widget.LIST;
      case OPENLIST:
        return com.cloudera.director.spi.v1.model.ConfigurationProperty.Widget.OPENLIST;
      case MULTI:
        return com.cloudera.director.spi.v1.model.ConfigurationProperty.Widget.MULTI;
      case OPENMULTI:
        return com.cloudera.director.spi.v1.model.ConfigurationProperty.Widget.OPENMULTI;
      default:
        throw new IllegalArgumentException("Unknown widget: " + widget);
    }
  }

  public static com.cloudera.director.spi.v1.model.DisplayProperty.Widget toV1(
      DisplayProperty.Widget widget) {
    Preconditions.checkNotNull(widget, "widget is null");
    switch (widget) {
      case RADIO:
        return com.cloudera.director.spi.v1.model.DisplayProperty.Widget.RADIO;
      case CHECKBOX:
        return com.cloudera.director.spi.v1.model.DisplayProperty.Widget.CHECKBOX;
      case TEXT:
        return com.cloudera.director.spi.v1.model.DisplayProperty.Widget.TEXT;
      case TEXTAREA:
        return com.cloudera.director.spi.v1.model.DisplayProperty.Widget.TEXTAREA;
      case FILE:
        return com.cloudera.director.spi.v1.model.DisplayProperty.Widget.FILE;
      case MULTI:
        return com.cloudera.director.spi.v1.model.DisplayProperty.Widget.MULTI;
      default:
        throw new IllegalArgumentException("Unknown widget: " + widget);
    }
  }

  public static com.cloudera.director.spi.v1.model.Property.Type toV1(Property.Type type) {
    Preconditions.checkNotNull(type, "type is null");
    switch (type) {
      case BOOLEAN:
        return com.cloudera.director.spi.v1.model.Property.Type.BOOLEAN;
      case INTEGER:
        return com.cloudera.director.spi.v1.model.Property.Type.INTEGER;
      case DOUBLE:
        return com.cloudera.director.spi.v1.model.Property.Type.DOUBLE;
      case STRING:
        return com.cloudera.director.spi.v1.model.Property.Type.STRING;
      default:
        throw new IllegalArgumentException("Unknown type: " + type);
    }
  }

  public static com.cloudera.director.spi.v1.compute.VirtualizationType toV1(
      VirtualizationType virtualizationType) {
    if (virtualizationType == null) return null;
    switch (virtualizationType) {
      case PARAVIRTUALIZATION:
        return com.cloudera.director.spi.v1.compute.VirtualizationType.PARAVIRTUALIZATION;
      case HARDWARE_ASSISTED:
        return com.cloudera.director.spi.v1.compute.VirtualizationType.HARDWARE_ASSISTED;
      case UNKNOWN:
        return com.cloudera.director.spi.v1.compute.VirtualizationType.UNKNOWN;
      default:
        throw new IllegalArgumentException("Unknown virtualizationType: " + virtualizationType);
    }
  }
}
