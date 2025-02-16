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

package com.dtstack.chunjun.connector.ftp.client.excel;

import com.alibaba.excel.ExcelReader;

public class ExcelReaderExecutor implements Runnable {

    private final ExcelReader reader;
    private final ExcelSubExceptionCarrier ec;

    public ExcelReaderExecutor(ExcelReader reader, ExcelSubExceptionCarrier ec) {
        this.reader = reader;
        this.ec = ec;
    }

    @Override
    public void run() {
        try {
            reader.readAll();
        } catch (Exception e) {
            ec.setThrowable(e);
        } finally {
            close();
        }
    }

    private void close() {
        if (reader != null) {
            // Don’t forget to close it here. A temporary file will be
            // created when you read it, and the disk will crash.
            reader.finish();
        }
    }
}
