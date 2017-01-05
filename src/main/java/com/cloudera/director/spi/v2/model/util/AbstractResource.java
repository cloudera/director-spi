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

import static com.cloudera.director.spi.v2.util.Preconditions.checkNotNull;

import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.Resource;
import com.cloudera.director.spi.v2.model.ResourceTemplate;

/**
 * Abstract base class for resource implementations.
 *
 * @param <T> the type of resource template from which resources are constructed
 * @param <D> the type of resource details
 */
public abstract class AbstractResource<T extends ResourceTemplate, D> implements Resource<T> {

  /**
   * The resource type representing a generic resource.
   */
  public static final Type TYPE = new ResourceType("Resource");

  /**
   * Resource type.
   */
  protected static class ResourceType implements Type {

    /**
     * The description of the resource type.
     */
    private final String description;

    public ResourceType(String description) {
      if (description == null) {
        throw new NullPointerException("description is null");
      }
      this.description = description;
    }

    @Override
    // NOTE: This implementation does not do any actual localization, and simply returns the fixed description.
    // Plugin implementers may override to perform localization.
    public String getDescription(LocalizationContext localizationContext) {
      return description;
    }
  }

  /**
   * The template from which the resource was created.
   */
  private final T template;

  /**
   * The resource id.
   */
  private final String id;

  /**
   * The provider-specific resource details.
   */
  private D details;

  /**
   * Creates an abstract resource with the specified parameters.
   *
   * @param template   the template from which the resource was created
   * @param identifier the resource identifier
   */
  protected AbstractResource(T template, String identifier) {
    this(template, identifier, null);
  }

  /**
   * Creates an abstract resource with the specified parameters.
   *
   * @param resourceId the resource id
   * @param details    the provider-specific resource details
   */
  protected AbstractResource(T template, String resourceId, D details) {
    this.template = checkNotNull(template, "template is null");
    this.id = checkNotNull(resourceId, "resourceId is null");
    this.details = details;
  }

  @Override
  public Resource.Type getType() {
    return TYPE;
  }

  @Override
  public T getTemplate() {
    return template;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public D unwrap() {
    return details;
  }

  /**
   * Sets the provider-specific resource details.
   *
   * @param details the provider-specific resource details
   */
  protected void setDetails(D details) {
    this.details = details;
  }

  @Override
  public String getDescription(LocalizationContext localizationContext) {
    return getType().getDescription(localizationContext) + "[" + getId() + "]";
  }
}
