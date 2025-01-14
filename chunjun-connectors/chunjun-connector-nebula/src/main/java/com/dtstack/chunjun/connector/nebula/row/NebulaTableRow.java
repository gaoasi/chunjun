package com.dtstack.chunjun.connector.nebula.row;
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
import com.dtstack.chunjun.throwable.ChunJunRuntimeException;
import com.dtstack.chunjun.throwable.UnsupportedTypeException;

import com.vesoft.nebula.client.graph.data.ValueWrapper;
import com.vesoft.nebula.client.storage.data.BaseTableRow;
import com.vesoft.nebula.client.storage.data.EdgeTableRow;
import com.vesoft.nebula.client.storage.data.VertexTableRow;
import com.vesoft.nebula.client.storage.scan.ScanEdgeResultIterator;
import com.vesoft.nebula.client.storage.scan.ScanResultIterator;
import com.vesoft.nebula.client.storage.scan.ScanVertexResultIterator;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

/**
 * @author: gaoasi
 * @email: aschaser@163.com
 * @date: 2022/11/4 4:47 下午
 */
public class NebulaTableRow implements Serializable {

    private ScanResultIterator scanResult;

    private NebulaConf nebulaConf;

    private Boolean hasNextScan;

    private Iterator<VertexTableRow> vertexTableRow;

    private Iterator<EdgeTableRow> edgeTableRow;

    public NebulaTableRow(ScanResultIterator scanResult, NebulaConf conf) {
        this.nebulaConf = conf;
        this.scanResult = scanResult;
        hasNextScan = hasNextScan();
        if (hasNextScan) {
            scanNext();
        }
    }

    public Boolean hasNextScan() {
        return scanResult.hasNext();
    }

    public void scanNext() {
        try {
            switch (nebulaConf.getSchemaType()) {
                case TAG:
                case VERTEX:
                    vertexTableRow =
                            ((ScanVertexResultIterator) scanResult)
                                    .next()
                                    .getVertexTableRows()
                                    .iterator();
                    break;
                case EDGE:
                case EDGE_TYPE:
                    edgeTableRow =
                            ((ScanEdgeResultIterator) scanResult)
                                    .next()
                                    .getEdgeTableRows()
                                    .iterator();
                    break;
            }
        } catch (Exception e) {
            throw new ChunJunRuntimeException(e.getMessage(), e);
        }
    }

    public Boolean hasNext() {
        Boolean has;
        switch (nebulaConf.getSchemaType()) {
            case TAG:
            case VERTEX:
                has = vertexTableRow.hasNext();
                break;
            case EDGE:
            case EDGE_TYPE:
                has = edgeTableRow.hasNext();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + nebulaConf.getSchemaType());
        }
        if (!has) {
            if (hasNextScan()) {
                scanNext();
                has = hasNext();
            }
        }
        return has;
    }

    public BaseTableRow next() {
        switch (nebulaConf.getSchemaType()) {
            case TAG:
            case VERTEX:
                return vertexTableRow.next();
            case EDGE:
            case EDGE_TYPE:
                return edgeTableRow.next();
            default:
                throw new IllegalStateException("Unexpected value: " + nebulaConf.getSchemaType());
        }
    }

    public static Object getValue(ValueWrapper valueWrapper) throws UnsupportedEncodingException {
        if (valueWrapper.isBoolean()) {
            return valueWrapper.asBoolean();
        }
        if (valueWrapper.isDate()) {
            return valueWrapper.asDate();
        }
        if (valueWrapper.isDouble()) {
            return valueWrapper.asDouble();
        }
        if (valueWrapper.isDuration()) {
            return valueWrapper.asDuration();
        }
        if (valueWrapper.isDateTime()) {
            valueWrapper.asDateTime();
            return valueWrapper.asDateTime();
        }
        if (valueWrapper.isGeography()) {
            return valueWrapper.asGeography().toString();
        }
        if (valueWrapper.isList()) {
            return valueWrapper.asList();
        }
        if (valueWrapper.isLong()) {
            return valueWrapper.asLong();
        }
        if (valueWrapper.isMap()) {
            return valueWrapper.asMap();
        }
        if (valueWrapper.isNull()) {
            return valueWrapper.asNull();
        }
        if (valueWrapper.isSet()) {
            return valueWrapper.asSet();
        }
        if (valueWrapper.isString()) {
            return valueWrapper.asString();
        }

        if (valueWrapper.isPath()) {
            return valueWrapper.asPath();
        }
        if (valueWrapper.isTime()) {
            return valueWrapper.asTime();
        }
        throw new UnsupportedTypeException("unsupported nebula type");
    }
}
