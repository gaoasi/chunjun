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

package com.dtstack.chunjun.connector.s3.source;

import org.apache.flink.core.io.GenericInputSplit;

import java.util.ArrayList;
import java.util.List;

public class S3InputSplit extends GenericInputSplit {

    private static final long serialVersionUID = 8350870573057970895L;

    private List<String> splits;

    public S3InputSplit(int partitionNumber, int totalNumberOfPartitions) {
        super(partitionNumber, totalNumberOfPartitions);
        this.splits = new ArrayList<>();
    }

    /**
     * Creates a generic input split with the given split number.
     *
     * @param partitionNumber The number of the split's partition.
     * @param totalNumberOfPartitions The total number of the splits (partitions).
     */
    public S3InputSplit(int partitionNumber, int totalNumberOfPartitions, List<String> splits) {
        super(partitionNumber, totalNumberOfPartitions);
        this.splits = splits;
    }

    public List<String> getSplits() {
        return splits;
    }

    public void setSplits(List<String> splits) {
        this.splits = splits;
    }

    public void addSplit(String split) {
        splits.add(split);
    }
}
