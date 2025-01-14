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

package com.dtstack.chunjun.connector.doris.source;

import com.dtstack.chunjun.connector.doris.options.DorisConfig;
import com.dtstack.chunjun.connector.jdbc.source.JdbcInputFormat;
import com.dtstack.chunjun.connector.jdbc.source.JdbcInputFormatBuilder;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class DorisInputFormatBuilder extends JdbcInputFormatBuilder {
    public DorisInputFormatBuilder(JdbcInputFormat format) {
        super(format);
    }

    @Override
    protected void checkFormat() {
        DorisInputFormat format = (DorisInputFormat) this.format;
        DorisConfig config = format.getDorisConf();
        List<String> feNodes = config.getFeNodes();
        String url = config.getUrl();

        if (StringUtils.isEmpty(url) && (null == feNodes || feNodes.isEmpty())) {
            throw new IllegalArgumentException(
                    "Choose one of 'url' and 'feNodes', them can not be empty at same time.");
        }
    }
}
