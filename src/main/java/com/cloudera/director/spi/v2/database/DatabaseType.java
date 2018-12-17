//  (c) Copyright 2015 Cloudera, Inc.

package com.cloudera.director.spi.v2.database;

/**
 * Database types supported by Cloudera Manager and CDH components.
 *
 * @see <a href="http://www.cloudera.com/content/cloudera-content/cloudera-docs/CM5/latest/Cloudera-Manager-Installation-Guide/cm5ig_installing_configuring_dbs.html">Installing and Configuring DBs</a>
 */
public enum DatabaseType {

  /**
   * @see <a href="http://www.cloudera.com/content/cloudera-content/cloudera-docs/CM5/latest/Cloudera-Manager-Installation-Guide/cm5ig_extrnl_pstgrs.html">External Postgres</a>
   */
  POSTGRESQL,

  /**
   * @see <a href="http://www.cloudera.com/content/cloudera-content/cloudera-docs/CM5/latest/Cloudera-Manager-Installation-Guide/cm5ig_mysql.html">MySQL</a>
   */
  MYSQL,

  /**
   * @see <a href="http://www.cloudera.com/content/cloudera-content/cloudera-docs/CM5/latest/Cloudera-Manager-Installation-Guide/cm5ig_oracle.html">Oracle</a>
   */
  ORACLE
}
