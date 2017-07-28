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

import com.cloudera.director.spi.v2.model.ConfigurationValidator;
import com.cloudera.director.spi.v2.model.Configured;
import com.cloudera.director.spi.v2.model.LocalizationContext;
import com.cloudera.director.spi.v2.model.exception.PluginExceptionConditionAccumulator;
import com.cloudera.director.spi.v2.util.Preconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A validator that applies a sequence of other validators.
 */
public class CompositeConfigurationValidator implements ConfigurationValidator {

  /**
   * The component validators.
   */
  private final List<ConfigurationValidator> validators;

  /**
   * Creates a composite validator with the specified parameters.
   *
   * @param validators the component validators
   */
  public CompositeConfigurationValidator(ConfigurationValidator... validators) {
    this(Arrays.asList(validators));
  }

  /**
   * Creates a composite validator with the specified parameters.
   *
   * @param validators the component validators
   */
  public CompositeConfigurationValidator(List<ConfigurationValidator> validators) {
    Preconditions.checkNotNull(validators, "validators is null");
    this.validators = validators.isEmpty()
        ? Collections.<ConfigurationValidator>emptyList()
        : Collections.unmodifiableList(new ArrayList<ConfigurationValidator>(validators));
  }

  /**
   * Returns the component validators.
   *
   * @return the component validators
   */
  public List<ConfigurationValidator> getValidators() {
    return validators;
  }

  @Override
  public void validate(String name, Configured configuration,
      PluginExceptionConditionAccumulator accumulator, LocalizationContext localizationContext) {
    for (ConfigurationValidator validator : validators) {
      if (accumulator.hasError()) {
        return;
      }
      validator.validate(name, configuration, accumulator, localizationContext);
    }
  }
}
