/*
 * Copyright © 2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.plugin.memsql;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/**
 * MemSQL util methods.
 */
public final class MemsqlUtil {
  private MemsqlUtil() {
    throw new AssertionError("Should not instantiate static utility class.");
  }

  /**
   * Composes immutable map of the MemSQL specific arguments.
   *
   * @param autoReconnect should the driver try to re-establish stale and/or dead connections.
   * @param useCompression specifies if compression must be enabled.
   * @param useSSL specifies if SSL encryption must be turned on.
   * @param keyStore URL of the client certificate KeyStore.
   * @param keyStorePassword password for the client certificates KeyStore.
   * @param trustStore URL of the trusted root certificate KeyStore.
   * @param trustStorePassword  password for the trusted root certificates KeyStore.
   * @return immutable map of the MariaDB specific arguments
   */
  public static Map<String, String> composeDbSpecificArgumentsMap(Boolean autoReconnect,
                                                                  Boolean useCompression,
                                                                  String useSSL,
                                                                  String keyStore,
                                                                  String keyStorePassword,
                                                                  String trustStore,
                                                                  String trustStorePassword) {
    ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

    if (autoReconnect != null) {
      builder.put(MemsqlConstants.AUTO_RECONNECT, String.valueOf(autoReconnect));
    }
    if (useCompression != null) {
      builder.put(MemsqlConstants.USE_COMPRESSION, String.valueOf(useCompression));
    }
    if (MemsqlConstants.REQUIRE_SSL_OPTION.equals(useSSL)) {
      builder.put(MemsqlConstants.USE_SSL, "true");
    } else if (MemsqlConstants.NO_SSL_OPTION.equals(useSSL)) {
      builder.put(MemsqlConstants.USE_SSL, "false");
    }
    if (keyStore != null) {
      builder.put(MemsqlConstants.KEY_STORE, String.valueOf(keyStore));
    }
    if (keyStorePassword != null) {
      builder.put(MemsqlConstants.KEY_STORE_PASSWORD, String.valueOf(keyStorePassword));
    }
    if (trustStore != null) {
      builder.put(MemsqlConstants.TRUST_STORE, String.valueOf(trustStore));
    }
    if (trustStorePassword != null) {
      builder.put(MemsqlConstants.TRUST_STORE_PASSWORD, String.valueOf(trustStorePassword));
    }

    return builder.build();
  }

  /**
   * Composes immutable list of initial queries to the DB
   *
   * @param useAnsiQuotes ANSI_QUOTES mode
   * @return immutable list of initial commands
   */
  public static List<String> composeDbInitQueries(@Nullable Boolean useAnsiQuotes) {
    ImmutableList.Builder<String> builder = ImmutableList.builder();
    if (useAnsiQuotes != null  && useAnsiQuotes) {
      builder.add(MemsqlConstants.ANSI_QUOTES_QUERY);
    }

    return builder.build();
  }

  /**
   * Creates MemSQL specific JDBC connection string.
   *
   * @param host     server host.
   * @param port     server port.
   * @param database database name.
   * @return MemSQL specific JDBC connection string
   */
  public static String getConnectionString(String host, Integer port, String database) {
    return String.format(MemsqlConstants.MEMSQL_CONNECTION_STRING_FORMAT, host, port, database);
  }
}
