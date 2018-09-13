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

package com.cloudera.director.spi.v2.provider;

import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.Resource;
import com.cloudera.director.spi.v2.model.ResourceTemplate;
import com.cloudera.director.spi.v2.model.exception.PluginExceptionConditionAccumulator;

import java.util.Collection;
import java.util.Map;

/**
 * Represents a provider of cloud resources.
 *
 * @param <R> type of the resource created by this provider
 * @param <T> type of the template required by this provider to create a resource
 */
public interface ResourceProvider<R extends Resource<T>, T extends ResourceTemplate> {

  /**
   * Returns the resource provider metadata.
   *
   * @return the resource provider metadata
   */
  ResourceProviderMetadata getProviderMetadata();

  /**
   * Returns the type of resource managed by the provider.
   *
   * @return the type of resource managed by the provider
   */
  Resource.Type getResourceType();

  /**
   * Returns the localization context.
   *
   * @return the localization context
   */
  LocalizationContext getLocalizationContext();

  /**
   * Returns the resource template localization context.
   *
   * @return the resource template localization context
   */
  LocalizationContext getResourceTemplateLocalizationContext();

  /**
   * Validates the specified configuration, accumulating errors and warnings in the specified
   * accumulator.
   *
   * @param name the name of the template
   * @param configuration the configuration to be validated
   * @param accumulator   the exception condition accumulator
   */
  void validateResourceTemplateConfiguration(String name, Configured configuration,
      PluginExceptionConditionAccumulator accumulator);

  /**
   * Create a new resource template instance for this provider using the configuration.
   *
   * @param name          the name of the template
   * @param configuration the configuration
   * @param tags          tags to be added to the resource
   * @return a configured resource template
   */
  T createResourceTemplate(String name, Configured configuration, Map<String, String> tags);

  /**
   * Atomically allocates multiple resources with the specified identifiers based on a single
   * resource template. More specifically, <p/>
   * if at least minCount resources can be allocated, the allocated amount of resources
   * will be returned.<p/>
   * if minCount resources cannot be allocated, the method should fail by throwing an appropriate
   * exception and should make a good-faith effort to not leak resources.
   *
   * @param template    the resource template
   * @param resourceIds the unique identifiers for the resources
   * @param minCount    the minimum number of resources to allocate if not all resources can be
   *                    allocated
   * @return the successfully allocated resources. If minCount can be satisfied, it will return a
   * collection with resources equal to or greater than <code>minCount</code> in size. Otherwise
   * it should throw an appropriate exception.
   * @throws InterruptedException if the operation is interrupted
   */
  /*
   * Note: resourceId or template.groupId are used for idempotency, implementation does not need to
   * map resourceId to each instance.
   *
   * In current implementation,
   * preconditions:
   * 1) if template.isAutomatic == true, the resourceIds passed here are placeholders. the actual content
   * does not matter, whereas the size of the collection matters.
   * 2) if template.isAutomatic == false, the resourceIds passed here are virtual instance Ids
   *
   * postconditions:
   * 0) if the same group Id or resource Ids are passed, no duplicate group or instances should be
   * created
   * 1) if template.isAutomatic == true, the returned object R does not contain resourceId, R::getId returns
   * provider-specific instance Id
   * 2) if template.isAutomatic == false, the returned object R contains resourceId, that R::getId returns
   * resourceId instead of provider-specific instance Id
   */
  Collection<? extends R> allocate(T template, Collection<String> resourceIds, int minCount)
      throws InterruptedException;

  /**
   * Returns current resource information for the specified resources, which are guaranteed to have
   * been created by this provider. This method takes resources, rather than resource identifiers,
   * so that any additional information associated with the resource, such as its template,
   * can be attached to the resulting resources.
   *
   * @param template    the template that was used to create those resources
   * @param resourceIds the unique identifiers for the resources
   * @return new resources, with the most currently available information, corresponding to the
   * subset of the resources which still exist. Some or all of them may have <code>null</code>
   * details if they are not fully ready for use
   * @throws InterruptedException if the operation is interrupted
   */
  /*
   * Preconditions:
   * 1) if template.isAutomatic == true, the resourceIds are provider-specific resource Ids
   * 2) if template.isAutomatic == false, the resourceIds are virtual instance Ids
   *
   * Postconditions:
   * 0) instances matching resourceIds will be returned
   * 1) if template.isAutomatic == true, the returned object R returns provider-specific resource Id
    * when R::getId is called. If the resourceIds is empty, all resources in the group will be listed
   * 2) if template.isAutomatic == false, the returned object R returns virtual instance Id when
   * R::getId is called. If the resourceIds is empty, no resource will be listed
   */
  Collection<? extends R> find(T template, Collection<String> resourceIds) throws InterruptedException;

  /**
   * Permanently removes the specified resources, which are guaranteed to have been created by
   * this provider.
   *
   * @param template    the template used to create those resources
   * @param resourceIds the unique identifiers for the resources
   * @throws InterruptedException if the operation is interrupted
   */
  /*
   * Preconditions:
   * 1) if template.isAutomatic == true, the resourceIds are provider-specific resource Ids
   * 2) if template.isAutomatic == false, the resourceIds are virtual instance Ids
   *
   * Postconditions:
   * 0) instances matching resourceIds is terminated
   * 1) if template.isAutomatic == true, and if the resourceIds is empty, the entire group is
   * terminated
   * 2) if template.isAutomatic == false, and if the resourceIds is empty, no instance is terminated
   */
  void delete(T template, Collection<String> resourceIds) throws InterruptedException;
}
