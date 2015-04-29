//  (c) Copyright 2015 Cloudera, Inc.

package com.cloudera.director.spi.v1.provider;

import com.cloudera.director.spi.v1.model.Configured;

/**
 * A cloud provider, which may support multiple cloud resource types.
 */
public interface CloudProvider {

  /**
   * Returns the cloud provider metadata.
   *
   * @return the cloud provider metadata
   */
  CloudProviderMetadata getMetadata();

  /**
   * Returns the specified resource provider, using the specified configuration
   *
   * @param resourceProviderId the resource provider ID, as returned by its metadata
   * @param configuration      the configuration
   * @return the specified resource provider, using the specified configuration
   * @throws java.util.NoSuchElementException if the cloud provider does not have a
   *                                          resource provider with the specified ID
   */
  ResourceProvider createResourceProvider(String resourceProviderId, Configured configuration);
}
