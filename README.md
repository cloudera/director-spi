# director-spi
## Cloudera Director Service Provider Interface

* [Introduction](#introduction)
* [Getting started](#getting-started)
* [Implementing the plugin](#implementing-the-plugin)
  * [Implementing the launcher](#implementing-the-launcher)
  * [Implementing the cloud provider](#implementing-the-cloud-provider)
  * [Implementing a resource provider](#implementing-a-resource-provider)
     * [Implementing an instance provider](#implementing-an-instance-provider)
         * [Implementing a compute resource provider](#implementing-a-compute-resource-provider)
         * [Implementing a database server resource provider](#implementing-a-database-server-resource-provider)
  * [Implementing a resource template](#implementing-a-resource-template)
     * [Implementing an instance template](#implementing-an-instance-template)
         * [Implementing a compute instance template](#implementing-a-compute-instance-template)
         * [Implementing a database server instance template](#implementing-a-database-server-instance-template)
  * [Implementing a resource](#implementing-a-resource)
     * [Implementing an instance](#implementing-an-instance)
         * [Implementing a compute instance](#implementing-a-compute-instance)
         * [Implementing a database server instance](#implementing-a-database-server-instance)
  * [Implementing configuration properties](#implementing-configuration-properties)
  * [Implementing display properties](#implementing-display-properties)
  * [Implementing validation](#implementing-validation)
  * [Implementing localization](#implementing-localization)
  * [Implementing logging](#implementing-logging)
* [Packaging the plugin](#packaging-the-plugin)
  * [Example](#example)
* [Testing the plugin](#testing-the-plugin)
* [Installing the plugin](#installing-the-plugin)
* [Important notice](#important-notice)

### Introduction
[Cloudera Director](http://www.cloudera.com/content/cloudera/en/products-and-services/director.html) enables deployment of Hadoop clusters in cloud environments.

The [Cloudera Director Service Provider Interface](https://github.com/cloudera/director-spi) (Director SPI) defines an [open source](https://github.com/cloudera/director-spi/blob/master/LICENSE.txt) Java interface that plugins implement to add support for additional cloud providers to Cloudera Director.

Plugin packaging, installation, and usage are discussed in detail below, but at a high level, a plugin and its dependencies are packaged in a single jar with metadata that allows Cloudera Director to recognize and load the plugin.

### Getting started

These instructions are geared towards plugin authors, and assume familiarity with git, github, maven, etc. You can create your plugin project from scratch, or clone one of these open-source plugin implementations:

* [AWS (Amazon Web Services)](https://github.com/cloudera/director-aws-plugin), a plugin supporting both compute (EC2) and database server (RDS) resource providers;
* [Microsoft Azure (Azure)](https://github.com/cloudera/director-azure-plugin), a plugin supporting compute resource providers;
* [GCP (Google Cloud Platform)](https://github.com/cloudera/director-google-plugin), a plugin supporting a compute (GCE) resource provider; and
* [BYON (Bring Your Own Nodes)](https://github.com/cloudera/director-byon-plugin-example), a simple but not fully-functional example plugin.

Your plugin will need to declare a maven "provided" dependency on the Director SPI, such as:

```xml
<properties>
  ...
  <director-spi-v1.version>1.0.0</director-spi-v1.version>
  ...
</properties>
...
<dependencies>
  ...
  <dependency>
    <groupId>com.cloudera.director</groupId>
    <artifactId>director-spi-v1</artifactId>
    <version>${director-spi-v1.version}</version>
    <scope>provided</scope>
  </dependency>
  ...
<dependencies>
```

When building your plugin, maven will need access to the Director SPI, either via a local build, or via the Cloudera Repository:

```xml
<repositories>
  ...
  <repository>
    <id>cloudera.repo</id>
    <url>https://repository.cloudera.com/artifactory/cloudera-repos</url>
    <name>Cloudera Repository</name>
  </repository>
  ...
</repositories>
```

### Implementing the plugin

These instructions are organized top-down, although you may choose to implement bottom-up.

Additional details can be found in the [Director SPI javadoc](https://cloudera.github.io/director-spi/apidocs/index.html).

#### Implementing the launcher

The main entry point from Cloudera Director into the plugin is the [Launcher](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/provider/Launcher.html) interface. These instructions assume the plugin author will subclass [AbstractLauncher](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/provider/util/AbstractLauncher.html), which makes it easier to implement the interface.

The plugin launcher implementation must provide a no-argument constructor so that it can be instantiated by the Java ServiceLoader framework.

It will likely override the [initialize](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/provider/Launcher.html#initialize(java.io.File,%20com.cloudera.director.spi.v1.common.http.HttpProxyParameters)) method, which receives a configuration directory (for loading any additional configuration information from the file system) and HTTP proxy parameters.

It may override the [getLocalizationContext](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/provider/Launcher.html#getLocalizationContext(java.util.Locale)) method, in order to provide support for localization in the Cloudera Director UI and in error messages. In the current release, neither Cloudera Director nor any of the existing plugins do localization, so the abstract launcher superclass returns a default localization context implementation that returns specified default string values. In a subsequent release we expect the default behavior to support properties file based lookup of localization keys.

Most importantly, the launcher must pass cloud provider metadata to the superclass constructor, to specify the cloud providers (typically only one) supported by the plugin, and implement [createCloudProvider](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/provider/Launcher.html#createCloudProvider(java.lang.String,%20com.cloudera.director.spi.v1.model.Configured,%20java.util.Locale)), to instantiate a cloud provider. The cloud provider metadata contains all the information that Cloudera Director needs in order to gather provider configuration information (such as credentials) from the user in the UI. This information is passed to the create method.

#### Implementing the cloud provider

The [CloudProvider](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/provider/CloudProvider.html) interface is a high-level abstraction of a cloud provider that allows Cloudera Director to interact with the cloud environment. These instructions assume the plugin author will subclass [AbstractCloudProvider](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/provider/util/AbstractCloudProvider.html), which makes it easier to implement the interface.

The cloud provider implementation must pass its metadata to the superclass constructor, to specify the resource providers (typically one for compute resources, and optionally one for database server resources) supported by the cloud provider, and implement [createResourceProvider](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/provider/CloudProvider.html#createResourceProvider(java.lang.String,%20com.cloudera.director.spi.v1.model.Configured)), to instantiate a resource provider. The resource provider metadata contains all the information that Cloudera Director needs in order to gather resource provider configuration information (such as regions) from the user in the UI. This information is passed to the create method.

It may also override the [getResourceProviderConfigurationValidator](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/provider/util/AbstractCloudProvider.html#getResourceProviderConfigurationValidator(com.cloudera.director.spi.v1.provider.ResourceProviderMetadata)) method to provide custom validation of resource provider configuration information. If implemented appropriately, this can lead to highlighted fields in the Cloudera Director UI with custom error messages. The default validation simply enforces the presence of required configuration properties.

#### Implementing a resource provider

The [ResourceProvider](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/provider/ResourceProvider.html) interface is a high-level abstraction of a cloud provider service that can provision, manage, and destroy some kind of resource in a cloud environment. Currently Cloudera Director supports two kinds of resource providers: compute providers and database server providers, described below. The instructions in this section describe implementation details that are common to any kind of resource provider, and assume the plugin author will subclass one of the subclasses of [AbstractResourceProvider](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/provider/util/AbstractResourceProvider.html), which make it easier to implement the interface.

The resource provider implementation must implement [getResourceType](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/provider/ResourceProvider.html#getResourceType()) to describe the type of resource it provides.

It must pass its metadata to the superclass constructor, to specify the configuration information required for instantiating resource templates, and implement [createResourceTemplate](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/provider/ResourceProvider.html#createResourceTemplate(java.lang.String,%20com.cloudera.director.spi.v1.model.Configured,%20java.util.Map)), to instantiate a resource template. The template configuration metadata contains all the information that Cloudera Director needs in order to gather resource template configuration information from the user in the UI. This information is passed to the create method.

It may also override the [getResourceTemplateConfigurationValidator](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/provider/ResourceProviderMetadata.html#getResourceTemplateConfigurationValidator()) method to provide custom validation of resource template configuration information. If implemented appropriately, this can lead to highlighted fields in the Cloudera director UI with custom error messages. The default validation simply enforces the presence of required configuration properties.

Finally, it must implement the resource lifecycle methods:

* [allocate](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/provider/ResourceProvider.html#allocate(T,%20java.util.Collection,%20int)), to provision resources;
* [find](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/provider/ResourceProvider.html#find(T,%20java.util.Collection)), to return detailed information about existing resources; and
* [delete](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/provider/ResourceProvider.html#delete(T,%20java.util.Collection)), to destroy resources

Typically, the implementation of these methods use a cloud-specific, or cloud service-specific SDK to interact with the cloud environment.

##### Implementing an instance provider

The [InstanceProvider](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/provider/InstanceProvider.html) interface extends [ResourceProvider](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/provider/ResourceProvider.html), and is a high-level abstraction of a cloud provider service that can provision, manage, and destroy some kind of server instance in a cloud environment. Both of the currently supported resource providers are also instance providers. The instructions in this section describe implementation details that are common to any kind of instance provider, and assume the plugin author will subclass one of the subclasses of [AbstractInstanceProvider](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/provider/util/AbstractInstanceProvider.html), which make it easier to implement the interface.

The only additional method to implement, beyond the methods that all resource providers must implement, is [getInstanceState](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/provider/InstanceProvider.html#getInstanceState(T,%20java.util.Collection)), to return the current state of a collection of instances.

The resource templates and resources returned by an instance provider must be instances of [InstanceTemplate](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/InstanceTemplate.html) and [Instance](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/Instance.html), respectively.

###### Implementing a compute resource provider

The [ComputeProvider](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/compute/ComputeProvider.html) interface is an abstraction of a cloud provider service that can provision and destroy computational resources (*e.g.* virtual machines). These instructions assume the plugin author will subclass [AbstractComputeProvider](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/compute/util/AbstractComputeProvider.html), which makes it easier to implement the interface.

There are no additional methods to implement, but the resource templates and resources returned by a compute provider must be instances of [ComputeInstanceTemplate](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/compute/ComputeInstanceTemplate.html) and [ComputeInstance](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/compute/ComputeInstance.html), respectively.

###### Implementing a database server resource provider

The [DatabaseServerProvider](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/database/DatabaseServerProvider.html) interface is an abstraction of a cloud provider service that can provision and destroy database server resources. These instructions assume the plugin author will subclass [AbstractDatabaseServerProvider](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/database/util/AbstractDatabaseServerProvider.html), which makes it easier to implement the interface.

There are no additional methods to implement, but the resource templates and resources returned by a database server provider must be instances of [DatabaseServerInstanceTemplate](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/database/DatabaseServerInstanceTemplate.html) and [DatabaseServerInstance](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/database/DatabaseServerInstance.html), respectively.

#### Implementing a resource template

The [ResourceTemplate](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/ResourceTemplate.html) interface represents the cloud service-specific details required to provision a specific kind of resource in the cloud environment. The instructions in this section describe implementation details that are common to any kind of resource template, and assume the plugin author will subclass one of the subclasses of [SimpleResourceTemplate](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/util/SimpleResourceTemplate.html), which makes it easier to implement the interface.

There are no methods to implement. Most implementations are simply beans that do all the work in the constructor, passing arguments along to the superclass constructor and then extracting property values from the supplied configuration using the superclass methods from the [Configured](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/Configured.html) interface.

##### Implementing an instance template

The [InstanceTemplate](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/InstanceTemplate.html) class extends the [ResourceTemplate](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/ResourceTemplate.html) interface, adding properties and behavior that are common to all server instances in a cloud environment. There are no additional responsibilities for subclasses.

###### Implementing a compute instance template

The [ComputeInstanceTemplate](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/compute/ComputeInstanceTemplate.html) class extends the [InstanceTemplate](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/InstanceTemplate.html) class, adding properties and behavior that are common to compute instances in a cloud environment. There are no additional responsibilities for subclasses.

###### Implementing a database server instance template

The [DatabaseServerInstanceTemplate](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/database/DatabaseServerInstanceTemplate.html) class extends the [InstanceTemplate](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/InstanceTemplate.html) class, adding properties and behavior that are common to database server instances in a cloud environment. There are no additional responsibilities for subclasses.

#### Implementing a resource

The [Resource](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/Resource.html) interface represents a resource in a cloud environment. The instructions in this section describe implementation details that are common to any kind of resource, and assume the plugin author will subclass one of the subclasses of [AbstractResource](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/util/AbstractResource.html), which makes it easier to implement the interface.

The interface and abstract superclass include a service-specific detail object, passed in through the constructor and retrievable via the [unwrap](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/model/Resource.html#unwrap()) method.

The resource implementation must implement [getType](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/model/Resource.html#getType()) to describe the type of resource it represents.

It must implement [getProperties](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/model/Resource.html#getProperties()) to return a map of property values for display in the Cloudera Director UI.

##### Implementing an instance

The [Instance](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/Instance.html) interface extends the [Resource](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/Resource.html) interface, and represents a server instance in a cloud environment. The instructions in this section describe implementation details that are common to any kind of instance, and assume the plugin author will subclass one of the subclasses of [AbstractInstance](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/util/AbstractInstance.html), which makes it easier to implement the interface.

The only additional responsibility for instance implementations is to provide the instance's private IP address, either in the constructor or by calling the [setPrivateIpAddress](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/model/util/AbstractInstance.html#setPrivateIpAddress(java.net.InetAddress)) method.

###### Implementing a compute instance

The [ComputeInstance](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/compute/ComputeInstance.html) interface extends the [Instance](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/Instance.html) interface, and represents a compute instance in a cloud environment. The instructions in this section describe implementation details that are common to any kind of compute instance, and assume the plugin author will subclass [AbstractComputeInstance](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/compute/util/AbstractComputeInstance.html), which makes it easier to implement the interface.

The only additional responsibility for compute instance implementations is to provide the instance's [VirtualizationType](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/compute/VirtualizationType.html) to the superclass constructor.

###### Implementing a database server instance

The [DatabaseServerInstance](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/database/DatabaseServerInstance.html) interface extends the [Instance](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/Instance.html) interface, and represents a database server instance in a cloud environment. The instructions in this section describe implementation details that are common to any kind of database server instance, and assume the plugin author will subclass [AbstractDatabaseServerInstance](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/database/util/AbstractDatabaseServerInstance.html), which makes it easier to implement the interface.

The only additional responsibility for database server instance implementations is to provide an administrative port to the superclass constructor that Cloudera Director can use to provision databases.

#### Implementing configuration properties

The [ConfigurationProperty](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/ConfigurationProperty.html) interface represents an input property whose value needs to be provided by the user. It holds the metadata that Cloudera Director needs in order to set up a UI element (widget) to get the value from the user. Each configuration property has a configuration key that uniquely identifies it within its scope for localization and validation.

The [ConfigurationPropertyToken](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/ConfigurationPropertyToken.html) interface is a simple wrapper around a configuration property that helps eliminate boilerplate code in enumerations of configuration properties.

The Director SPI provides builders and utility classes for working with configuration properties and configuration property tokens. Typical usage is illustrated by this example from the [InstanceTemplate](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/InstanceTemplate.html) class:

```java
/**
 * String to use as prefix for instance names.
 */
INSTANCE_NAME_PREFIX(new SimpleConfigurationPropertyBuilder()
  .configKey("instanceNamePrefix")
  .name("Instance name prefix")
  .defaultValue("director")
  .defaultDescription("Prefix that Cloudera Director should use when naming the instances"
      + " (this is not part of the hostname)")
  .build());
```

#### Implementing display properties

The [DisplayProperty](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/DisplayProperty.html) interface represents an output property whose value needs to be displayed to the user. It holds the metadata that Cloudera Director needs in order to set up a UI element (widget) to display the value.<sup>1</sup> Each display property has a display key that uniquely identifies it within its scope for localization.

The [DisplayPropertyToken](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/DisplayPropertyToken.html) interface is a simple wrapper around a display property that helps eliminate boilerplate code in enumerations of display properties.

The Director SPI provides builders and utility classes for working with display properties and display property tokens. Typical usage is similar to that for configuration properties, except that the enum constant may also know how to extract the value of the property from an underlying cloud-specific detail object.

<sup>1</sup>*Note:* The current version of Cloudera Director does not display resource properties, but future versions will, so the plugin should define them correctly.

#### Implementing validation

The [ConfigurationValidator](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/ConfigurationValidator.html) interface enables cloud-specific validation logic of configuration parameters, and provides a way for Cloudera Director to highlight specific fields in the UI with associated validation errors.

The [DefaultConfigurationValidator](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/util/DefaultConfigurationValidator.html) class is an implementation that verifies that required configuration properties are present in a configuration.

The [CompositeConfigurationValidator](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/util/CompositeConfigurationValidator.html) class is an implementation that applies a sequence of validators in order. It stops when it encounters an error, so that a downstream validator can assume that all upstream validators have been satisfied. This allows a validator to make simplifying assumptions, like not having duplicate logic to ensure that a required configuration property value is present before checking its validity.

The [Validations](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/util/Validations.html) class provides utility methods for validator implementations. These methods automatically handle some of the tricky aspects of letting Cloudera Director know how to associate validation errors on configuration properties with the appropriate UI elements.

#### Implementing localization

The [LocalizationContext](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/LocalizationContext.html) interface encapsulates three things: a locale, a hierarchical context namespace, and a mechanism for resolution of locale-specific string values. This class and the surrounding infrastructure helps plugins provide support for localization of configuration properties, display properties, and error messages. Although Cloudera Director and the existing plugins do not yet do localization, a plugin which follows the conventions around localization can take advantage of automatic localization support in the future. A localization context has a string key prefix that can be used for namespacing in localization lookups.

The [DefaultLocalizationContext](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/util/DefaultLocalizationContext.html) class provides a default implementation that does not actually do any localization.

The [ChildLocalizationContext](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/util/ChildLocalizationContext.html) class represents a nested scope. It appends one or more key components to the key prefix of its parent, and delegates to the parent to perform the actual localization.

The [LocalizationContext.Factory](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/LocalizationContext.Factory.html) interface represents a factory for localization contexts. A cloud provider has an opportunity to pass a localization context factory to its abstract superclass. The default localization context implementation currently does not do any localization, but support for property-based localization may be added in the future. A plugin implementation can pass `null` for the localization context factory to its superclass to take advantage of this, if the plugin is not doing its own localization.

The abstract provider framework generally passes localization contexts up the constructor chain, and constructs appropriate child localization contexts using the ID of the constructed provider as a key component. Similarly, the template localization context for a resource provider is by convention a child context of the resource provider's localization context (see [SimpleResourceTemplate.getTemplateLocalizationContext](https://cloudera.github.io/director-spi/apidocs/com/cloudera/director/spi/v1/model/util/SimpleResourceTemplate.html#getTemplateLocalizationContext(com.cloudera.director.spi.v1.model.LocalizationContext))).

The Cloudera Director UI expects the keys associated with validation conditions to be a concatenation of a localization context key prefix and a configuration property key. This is what allows UI element highlighting in response to validation conditions. A plugin that follows the conventions and uses the utility methods in the [Validations](https://cloudera.github.io/director-spi/apidocs/index.html?com/cloudera/director/spi/v1/model/util/Validations.html) class will get this behavior automatically.

#### Implementing logging

The plugin should use the [SLF4J](http://www.slf4j.org) logging API for any logging, by declaring a 'provided' dependency on version 1.7.5. Logging for the entire application will be centrally configured, and logging output will be sent to the appropriate Cloudera Director log files.

The plugin should not include any SLF4J logging adapters, even if one of its dependencies uses a different logging framework. Instead, the plugin author should document the additional logging frameworks, and refer the user to Cloudera Director documentation for information about any supported logging adapters.

### Packaging the plugin

All dependencies of the plugin code, other than the 'provided' Director SPI and slf4j logging dependencies, must be included within the plugin JAR. To avoid conflicts between dependencies used by the plugin and dependencies used by other plugins, as well as by Cloudera Director itself, the dependencies must be relocated into new packages that are unique to the plugin. The Maven shade plugin can perform this transformation.

Additionally, all code in the plugin JAR must be in either the same package as the plugin launcher class or a subpackage of that package.

Cloudera Director uses Java’s service loading mechanism to locate launcher classes in a plugin JAR. Therefore, a plugin JAR must include a provider-configuration file named `META-INF/services/com.cloudera.director.spi.v1.provider.Launcher`. Each ordinary line in that file must contain the fully-qualified name of a launcher class (for typical plugins, this will be only one line). On startup, Cloudera Director will scan the plugin JAR for launcher classes named in this way and instantiate them. See the Javadoc for java.util.ServiceLoader for more information on the format of the provider-configuration file.

#### Example

Suppose the launcher class for a plugin is `com.foo.director.Launcher`. Then the contents of the provider-configuration file should be:

```
com.foo.director.Launcher
```

All classes in the plugin JAR must be in the package `com.foo.director` or a subpackage.

### Testing the plugin

In addition to unit tests provided by the plugin author, an open-source [Director SPI TCK](https://github.com/cloudera/director-spi-tck) (technology compatibility kit) is available to ensure that the plugin is packaged appropriately and behaves as expected. See the TCK readme for detailed instructions on how to run the TCK.

### Installing the plugin

Detailed information about plugin installation, and about the plugins that come pre-installed, is part of the Cloudera Director documentation. This section contains only an overview, and concentrates on information of interest to plugin authors.

Cloudera Director contains a directory for plugins. The user creates a subdirectory for each plugin. This subdirectory contains the plugin jar and an `etc` directory for additional filesystem-based plugin configuration information. Cloudera Director will create an empty `etc` directory if it is absent.

A plugin author may want to provide an archive file which mirrors this structure and provides sample or starter configuration files in the nested configuration directory.

### Important notice

Copyright &copy; 2015 Cloudera, Inc. Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

Cloudera, the Cloudera logo, and any other product or service names or slogans contained in this document are trademarks of Cloudera and its suppliers or licensors, and may not be copied, imitated or used, in whole or in part, without the prior written permission of Cloudera or the applicable trademark holder.

Hadoop and the Hadoop elephant logo are trademarks of the Apache Software Foundation. Amazon Web Services, the "Powered by Amazon Web Services" logo, Amazon Elastic Compute Cloud, EC2, Amazon Relational Database Service, and RDS are trademarks of Amazon.com, Inc. or its affiliates in the United States and/or other countries. Google, Google Cloud Platform, GCP, Google Compute Engine, and GCE are registered trademarks of Google Inc. All other trademarks, registered trademarks, product names and company names or logos mentioned in this document are the property of their respective owners. Reference to any products, services, processes or other information, by trade name, trademark, manufacturer, supplier or otherwise does not constitute or imply endorsement, sponsorship or recommendation thereof by us.

Complying with all applicable copyright laws is the responsibility of the user. Without limiting the rights under copyright, no part of this document may be reproduced, stored in or introduced into a retrieval system, or transmitted in any form or by any means (electronic, mechanical, photocopying, recording, or otherwise), or for any purpose, without the express written permission of Cloudera.

Cloudera may have patents, patent applications, trademarks, copyrights, or other intellectual property rights covering subject matter in this document. Except as expressly provided in any written license agreement from Cloudera, the furnishing of this document does not give you any license to these patents, trademarks, copyrights, or other intellectual property. For information about patents covering Cloudera products, see http://tiny.cloudera.com/patents.

The information in this document is subject to change without notice. Cloudera shall not be liable for any damages resulting from technical errors or omissions which may be present in this document, or from use of this document.
