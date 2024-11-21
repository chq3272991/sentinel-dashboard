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

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CHQ
 * @since 1.0.0
 * 从Nacos配置中心动态获取熔断规则
 */
@Component("degradeRuleNacosProvider")
public class DegradeRuleNacosProvider implements DynamicRuleProvider<List<DegradeRuleEntity>> {
    private static Logger logger = LoggerFactory.getLogger(DegradeRuleNacosProvider.class);

    @Autowired
    private NacosProperties nacosProperties;

    @Autowired
    private ConfigService configService;
    @Autowired
    private Converter<String, List<DegradeRuleEntity>> converter;

    /**
     * 从Nacos配置中心拉取流控制规则配置文件
     * @param appName 配置文件名
     * @return
     * @throws Exception
     */
    @Override
    public List<DegradeRuleEntity> getRules(String appName) throws Exception {
        logger.info("Nacos server status:{}", configService.getServerStatus());
        String dataId = appName + NacosConfigUtil.DEGRADE_DATA_ID_POSTFIX;
        String group = nacosProperties.getGroup();
        String rules = configService.getConfig(dataId, group, 3000);
        logger.info("Nacos config dataId:{}, group:{}",dataId, group);
        logger.info("Nacos Config:{}", rules);
        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }
        return converter.convert(rules);
    }
}
