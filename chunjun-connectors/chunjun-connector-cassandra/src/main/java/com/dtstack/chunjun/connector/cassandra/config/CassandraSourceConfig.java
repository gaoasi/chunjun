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

package com.dtstack.chunjun.connector.cassandra.config;

import org.apache.flink.configuration.ReadableConfig;

import java.util.StringJoiner;

import static com.dtstack.chunjun.connector.cassandra.optinos.CassandraCommonOptions.WHERE;

public class CassandraSourceConfig extends CassandraCommonConfig {
    private String where;

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public static CassandraSourceConfig from(ReadableConfig readableConfig) {
        CassandraSourceConfig conf =
                (CassandraSourceConfig)
                        CassandraCommonConfig.from(readableConfig, new CassandraSourceConfig());

        conf.setWhere(readableConfig.get(WHERE));

        return conf;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CassandraSourceConfig.class.getSimpleName() + "[", "]")
                .add("where='" + where + "'")
                .add("host='" + host + "'")
                .add("port=" + port)
                .add("userName='" + userName + "'")
                .add("password='" + password + "'")
                .add("tableName='" + tableName + "'")
                .add("keyspaces='" + keyspaces + "'")
                .add("hostDistance='" + hostDistance + "'")
                .add("useSSL=" + useSSL)
                .add("clusterName='" + clusterName + "'")
                .add("consistency='" + consistency + "'")
                .add("coreConnectionsPerHost=" + coreConnectionsPerHost)
                .add("maxConnectionsPerHost=" + maxConnectionsPerHost)
                .add("maxRequestsPerConnection=" + maxRequestsPerConnection)
                .add("maxQueueSize=" + maxQueueSize)
                .add("readTimeoutMillis=" + readTimeoutMillis)
                .add("connectTimeoutMillis=" + connectTimeoutMillis)
                .add("poolTimeoutMillis=" + poolTimeoutMillis)
                .toString();
    }
}
