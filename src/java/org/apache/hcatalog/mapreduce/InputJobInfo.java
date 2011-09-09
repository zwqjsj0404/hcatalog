/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hcatalog.mapreduce;

import org.apache.hadoop.hive.metastore.MetaStoreUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/** The class used to serialize and store the information read from the metadata server */
public class InputJobInfo implements Serializable{

  /** The serialization version */
  private static final long serialVersionUID = 1L;

  /** The db and table names. */
  private final String databaseName;
  private final String tableName;

  /** meta information of the table to be read from */
  private HCatTableInfo tableInfo;

  /** The Metadata server uri */
  private final String serverUri;

  /** If the hcat server is configured to work with hadoop security, this
   * variable will hold the principal name of the server - this will be used
   * in the authentication to the hcat server using kerberos
   */
  private final String serverKerberosPrincipal;

  /** The partition filter */
  private String filter;

  /** The list of partitions matching the filter. */
  private List<PartInfo> partitions;

  /** implementation specific job properties */
  private Properties properties;

  /**
   * Initializes a new InputJobInfo
   * for reading data from a table.
   * @param databaseName the db name
   * @param tableName the table name
   * @param filter the partition filter
   * @param serverUri the Metadata server uri
   * @param serverKerberosPrincipal If the hcat server is configured to
   * work with hadoop security, the kerberos principal name of the server - else null
   * The principal name should be of the form:
   * <servicename>/_HOST@<realm> like "hcat/_HOST@myrealm.com"
   * The special string _HOST will be replaced automatically with the correct host name
   */
  public static InputJobInfo create(String databaseName,
                                                  String tableName,
                                                  String filter,
                                                  String serverUri,
                                                  String serverKerberosPrincipal) {
    return new InputJobInfo(databaseName,tableName,filter,serverUri,serverKerberosPrincipal);
  }

  private InputJobInfo(String databaseName,
                                String tableName,
                                String filter,
                                String serverUri,
                                String serverKerberosPrincipal) {
    this.databaseName = (databaseName == null) ? MetaStoreUtils.DEFAULT_DATABASE_NAME : databaseName;
    this.tableName = tableName;
    this.serverUri = serverUri;
    this.serverKerberosPrincipal = serverKerberosPrincipal;
    this.filter = filter;
    this.properties = new Properties();
  }

  /**
   * Gets the value of databaseName
   * @return the databaseName
   */
  public String getDatabaseName() {
    return databaseName;
  }

  /**
   * Gets the value of tableName
   * @return the tableName
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * Gets the table's meta information
   * @return the HCatTableInfo
   */
  public HCatTableInfo getTableInfo() {
    return tableInfo;
  }

  /**
   * set the tablInfo instance
   * this should be the same instance
   * determined by this object's DatabaseName and TableName
   * @param tableInfo
   */
  void setTableInfo(HCatTableInfo tableInfo) {
    this.tableInfo = tableInfo;
  }

  /**
   * @return the serverKerberosPrincipal
   */
  public String getServerKerberosPrincipal() {
    return serverKerberosPrincipal;
  }

  /**
   * Gets the value of serverUri
   * @return the serverUri
   */
  public String getServerUri() {
    return serverUri;
  }

  /**
   * Gets the value of partition filter
   * @return the filter string
   */
  public String getFilter() {
    return filter;
  }

  /**
   * @return partition info
   */
  public List<PartInfo> getPartitions() {
    return partitions;
  }

  /**
   * @return partition info  list
   */
  void setPartitions(List<PartInfo> partitions) {
    this.partitions = partitions;
  }

  /**
   * Set/Get Property information to be passed down to *StorageDriver implementation
   * put implementation specific storage driver configurations here
   * @return
   */
  public Properties getProperties() {
    return properties;
  }

}