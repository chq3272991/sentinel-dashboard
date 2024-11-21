package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.junit.jupiter.api.Test;
import java.util.Properties;

/**
 * @author CHQ
 * @version 1.0
 * @date 2024/6/4
 */
public class NacosProviderTest {

    @Test
    public void test() throws NacosException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        ConfigService configService = NacosFactory.createConfigService(properties);
        System.out.println(configService.getConfig("org.example.Main-flow-rules", "DEFAULT_GROUP", 2000));
    }
}
