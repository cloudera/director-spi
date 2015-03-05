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

package com.cloudera.director.spi.v1.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Abstract base class for resource implementations.
 *
 * @param <D> the type of resource details
 * @param <T> the type of resource template from which resources are constructed
 */
public abstract class AbstractResource<D, T extends ResourceTemplate> implements Resource {

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
    // Plugin implementors may override to perform localization.
    public String getDescription(Locale locale) {
      return description;
    }
  }

  /**
   * A string to represent the value of a redacted resource property.
   */
  public static final String REDACTED = "REDACTED";

  /**
   * The template from which the resource was created.
   */
  private final T template;

  /**
   * The resource identifier.
   */
  private final String identifier;

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
   * @param identifier the resource identifier
   * @param details    the provider-specific resource details
   */
  protected AbstractResource(T template, String identifier, D details) {
    if (template == null) {
      throw new NullPointerException("template is null");
    }
    this.template = template;
    if (identifier == null) {
      throw new NullPointerException("identifier is null");
    }
    this.identifier = identifier;
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
    return identifier;
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
  public String getDescription(Locale locale) {
    return getType().getDescription(locale) + "[" + getId() + "]";
  }

  @Override
  public Map<String, String> getPropertyValues() {
    Map<String, String> properties = new LinkedHashMap<String, String>();
    addProperties(properties, false);
    return Collections.unmodifiableMap(properties);
  }

  /**
   * Adds resource properties to the specified accumulator.
   *
   * @param properties the property accumulator
   * @param redact     whether to replace sensitive property values by a placeholder value
   */
  protected void addProperties(Map<String, String> properties, boolean redact) {
  }
}
