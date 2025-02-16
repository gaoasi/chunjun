package com.dtstack.chunjun.connector.nebula.lookup.ngql;
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

import com.dtstack.chunjun.connector.nebula.conf.NebulaConf;
import com.dtstack.chunjun.throwable.UnsupportedTypeException;

import static com.dtstack.chunjun.connector.nebula.utils.NebulaConstant.DSTID;
import static com.dtstack.chunjun.connector.nebula.utils.NebulaConstant.RANK;
import static com.dtstack.chunjun.connector.nebula.utils.NebulaConstant.SRCID;
import static com.dtstack.chunjun.connector.nebula.utils.NebulaConstant.VID;

/**
 * @author: gaoasi
 * @email: aschaser@163.com
 * @date: 2022/11/9 4:40 下午
 */
public class LookupNGQLBuilder {

    /** 返回的字段 */
    private String[] fieldNames;
    /** 过滤的字段 */
    private String[] fiterFieldNames;
    /** nebula 配置对象 */
    private NebulaConf nebulaConf;

    public LookupNGQLBuilder setNebulaConf(NebulaConf nebulaConf) {
        this.nebulaConf = nebulaConf;
        return this;
    }

    public LookupNGQLBuilder setFiterFieldNames(String[] fiterFieldNames) {
        this.fiterFieldNames = fiterFieldNames;
        return this;
    }

    public LookupNGQLBuilder setFieldNames(String[] fieldNames) {
        this.fieldNames = fieldNames;
        return this;
    }

    public String buildVertexNgql() {
        StringBuilder builder = new StringBuilder();
        builder.append("match (v:").append(nebulaConf.getEntityName()).append(") ");
        if (fiterFieldNames.length > 0) {
            builder.append("where ");
            for (String fiterFieldName : fiterFieldNames) {
                if (VID.equalsIgnoreCase(fiterFieldName)) {
                    builder.append("id(v) == $").append(fiterFieldName).append(" and");
                } else {
                    builder.append(" v.")
                            .append(nebulaConf.getEntityName())
                            .append(".")
                            .append(fiterFieldName)
                            .append("==")
                            .append("$")
                            .append(fiterFieldName)
                            .append(" and");
                }
            }
            builder.delete(builder.length() - 3, builder.length());
        }
        if (fieldNames.length > 0) {
            builder.append(" return ");
            for (String fieldName : fieldNames) {
                if (VID.equalsIgnoreCase(fieldName)) {
                    builder.append("id(v),");
                    continue;
                }
                builder.append("v.")
                        .append(nebulaConf.getEntityName())
                        .append(".")
                        .append(fieldName)
                        .append(",");
            }
            builder.delete(builder.length() - 1, builder.length());
        }
        return builder.toString();
    }

    public String buildEdgeNgql() {
        StringBuilder builder = new StringBuilder();
        StringBuilder where = new StringBuilder(" where ");
        builder.append("match (f) - [e:").append(nebulaConf.getEntityName());
        if (fiterFieldNames.length > 0) {

            for (String fiterFieldName : fiterFieldNames) {
                if (SRCID.equalsIgnoreCase(fiterFieldName)) {
                    where.append("id(f) == ").append("$").append(fiterFieldName).append(" and ");
                    continue;
                }

                if (DSTID.equalsIgnoreCase(fiterFieldName)) {
                    where.append("id(t) == ").append("$").append(fiterFieldName).append(" and ");
                    continue;
                }
                if (RANK.equalsIgnoreCase(fiterFieldName)) {
                    where.append("rank(e) == ").append("$").append(fiterFieldName).append(" and ");
                    continue;
                }
                if (builder.indexOf("{") > -1) {
                    builder.append("\"")
                            .append(fiterFieldName)
                            .append("\":")
                            .append("$")
                            .append(fiterFieldName)
                            .append(",");
                    continue;
                }
                builder.append("{")
                        .append("\"")
                        .append(fiterFieldName)
                        .append("\":")
                        .append("$")
                        .append(fiterFieldName)
                        .append(",");
            }
            where.delete(where.length() - 4, where.length());
            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append("}]").append(" -> (t)");
        if (where.length() > 6) {
            builder.append(where);
        }
        if (fieldNames.length > 0) {
            builder.append(" return ");
            for (String fieldName : fieldNames) {
                if (SRCID.equalsIgnoreCase(fieldName)) {
                    builder.append("id(f),");
                    continue;
                }

                if (DSTID.equalsIgnoreCase(fieldName)) {
                    builder.append("id(t),");
                    continue;
                }
                if (RANK.equalsIgnoreCase(fieldName)) {
                    builder.append("rank(e),");
                    continue;
                }
                builder.append("e.").append(fieldName).append(",");
            }
            builder.delete(builder.length() - 1, builder.length());
        }
        return builder.toString();
    }

    public String build() {

        switch (nebulaConf.getSchemaType()) {
            case TAG:
            case VERTEX:
                return buildVertexNgql();
            case EDGE:
            case EDGE_TYPE:
                return buildEdgeNgql();
            default:
                throw new UnsupportedTypeException("unsupport schema type,check");
        }
    }
}
