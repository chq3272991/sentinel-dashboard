/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.dashboard.rule.nacos;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
public final class NacosConfigUtil {

    // 默认groupId
    public static final String GROUP_ID = "DEFAULT_GROUP";

    // 流控规则后缀
    public static final String FLOW_DATA_ID_POSTFIX = "-flow-rules";
    // 熔断规则后缀
    public static final String DEGRADE_DATA_ID_POSTFIX = "-degrade-rules";
    // 热点规则后缀
    public static final String PARAM_FLOW_DATA_ID_POSTFIX = "-param-rules";
    // 系统规则
    public static final String SYSTEM_DATA_ID_POSTFIX = "-system-rules";
    // 授权规则
    public static final String AUTHORITY_DATA_ID_POSTFIX = "-authority-rules";

    public static final String CLUSTER_MAP_DATA_ID_POSTFIX = "-cluster-map";

    /**
     * cc for `cluster-client`
     */
    public static final String CLIENT_CONFIG_DATA_ID_POSTFIX = "-cc-config";
    /**
     * cs for `cluster-server`
     */
    public static final String SERVER_TRANSPORT_CONFIG_DATA_ID_POSTFIX = "-cs-transport-config";
    public static final String SERVER_FLOW_CONFIG_DATA_ID_POSTFIX = "-cs-flow-config";
    public static final String SERVER_NAMESPACE_SET_DATA_ID_POSTFIX = "-cs-namespace-set";

    private NacosConfigUtil() {}
}
