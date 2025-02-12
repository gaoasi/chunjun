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

package com.dtstack.chunjun.connector.sqlservercdc.config;

import com.dtstack.chunjun.config.CommonConfig;
import com.dtstack.chunjun.config.FieldConfig;

import java.util.List;
import java.util.StringJoiner;

public class SqlServerCdcConfig extends CommonConfig {

    private String username;
    private String password;
    private String url;
    private String databaseName;
    private String cat;
    private boolean pavingData;
    private List<String> tableList;
    private Long pollInterval = 1000L;
    private String lsn;
    private boolean splitUpdate;
    private String timestampFormat = "sql";
    private List<FieldConfig> column;
    private boolean autoCommit = false;
    private boolean autoResetConnection = false;

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public boolean isAutoResetConnection() {
        return autoResetConnection;
    }

    public void setAutoResetConnection(boolean autoResetConnection) {
        this.autoResetConnection = autoResetConnection;
    }

    public String getTimestampFormat() {
        return timestampFormat;
    }

    public void setTimestampFormat(String timestampFormat) {
        this.timestampFormat = timestampFormat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public boolean isPavingData() {
        return pavingData;
    }

    public void setPavingData(boolean pavingData) {
        this.pavingData = pavingData;
    }

    public List<String> getTableList() {
        return tableList;
    }

    public void setTableList(List<String> tableList) {
        this.tableList = tableList;
    }

    public Long getPollInterval() {
        return pollInterval;
    }

    public void setPollInterval(Long pollInterval) {
        this.pollInterval = pollInterval;
    }

    public String getLsn() {
        return lsn;
    }

    public void setLsn(String lsn) {
        this.lsn = lsn;
    }

    public boolean isSplitUpdate() {
        return splitUpdate;
    }

    public void setSplitUpdate(boolean splitUpdate) {
        this.splitUpdate = splitUpdate;
    }

    @Override
    public List<FieldConfig> getColumn() {
        return column;
    }

    @Override
    public void setColumn(List<FieldConfig> column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SqlServerCdcConfig.class.getSimpleName() + "[", "]")
                .add("username='" + username + "'")
                .add("password='" + password + "'")
                .add("url='" + url + "'")
                .add("databaseName='" + databaseName + "'")
                .add("cat='" + cat + "'")
                .add("pavingData=" + pavingData)
                .add("tableList=" + tableList)
                .add("pollInterval=" + pollInterval)
                .add("lsn='" + lsn + "'")
                .add("splitUpdate=" + splitUpdate)
                .add("timestampFormat='" + timestampFormat + "'")
                .add("column=" + column)
                .add("autoCommit=" + autoCommit)
                .add("autoResetConnection=" + autoResetConnection)
                .toString();
    }
}
