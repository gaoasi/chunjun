/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dtstack.chunjun.connector.jdbc.config;

import java.util.List;
import java.util.StringJoiner;

public class SourceConnectionConfig extends ConnectionConfig {

    protected List<String> jdbcUrl;

    @Override
    public String obtainJdbcUrl() {
        return jdbcUrl.get(0);
    }

    @Override
    public void putJdbcUrl(String jdbcUrl) {
        this.jdbcUrl.set(0, jdbcUrl);
    }

    public List<String> getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(List<String> jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SourceConnectionConfig.class.getSimpleName() + "[", "]")
                .add("jdbcUrl=" + jdbcUrl)
                .add("table=" + table)
                .add("schema='" + schema + "'")
                .add("username='" + username + "'")
                .add("password='" + password + "'")
                .toString();
    }
}