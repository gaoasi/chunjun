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

package com.dtstack.chunjun.table.connector.source;

import org.apache.flink.table.connector.ParallelismProvider;
import org.apache.flink.table.connector.source.AsyncTableFunctionProvider;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.functions.AsyncTableFunction;

import java.util.Optional;

public interface ParallelAsyncTableFunctionProvider
        extends AsyncTableFunctionProvider, ParallelismProvider {

    /**
     * Helper method for creating a AsyncTableFunction provider with a provided lookup parallelism.
     */
    static AsyncTableFunctionProvider of(
            AsyncTableFunction<RowData> asyncTableFunction, Integer parallelism) {
        return new ParallelAsyncTableFunctionProvider() {

            @Override
            public AsyncTableFunction<RowData> createAsyncTableFunction() {
                return asyncTableFunction;
            }

            @Override
            public Optional<Integer> getParallelism() {
                return Optional.ofNullable(parallelism);
            }
        };
    }
}
