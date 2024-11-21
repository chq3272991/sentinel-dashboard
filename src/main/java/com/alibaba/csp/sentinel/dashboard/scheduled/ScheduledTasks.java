package com.alibaba.csp.sentinel.dashboard.scheduled;

import com.alibaba.csp.sentinel.dashboard.controller.FlowControllerV1;
import com.alibaba.csp.sentinel.dashboard.controller.v2.*;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.dashboard.discovery.*;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.util.SpringUtils;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author CHQ
 * @version 1.0
 * @date 2024/6/5
 */

@EnableScheduling
@Component
public class ScheduledTasks {
    private final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    @Autowired
    private AppManagement appManagement;

    @Scheduled(initialDelay = 1000 * 60 , fixedRate = 1000 * 1800)  // 延长1分钟执行同步操作， 每30分钟加载一次nacos配置
    public void reportCurrentTime() throws NacosException {
        Set<AppInfo> appInfos =  appManagement.getBriefApps();
        for (AppInfo appInfo : appInfos) {
            // 应用名
            String appName = appInfo.getApp();
            if(!StringUtils.hasText(appName)) {
                logger.warn("no app info ...");
                break;
            }
            // 基于APP 获取机器信息
            AppInfo appDetailInfo = appManagement.getDetailApp(appName);
            List<MachineInfo> machineInfoList = new ArrayList<>(appDetailInfo.getMachines());
            machineInfoList.forEach(machineInfo -> {
                String ip = machineInfo.getIp();  // 主机ip
                int port = machineInfo.getPort();  // 主机端口
                // 基于app+ip+port加载其他内容
                try {
                    loadConfig(appName, ip, port);
                } catch (NacosException e) {
                    logger.error("error: ", e.getMessage());
                }
            });
        }
    }

    private void loadConfig(String app, String ip, int port) throws NacosException {
        // 加载控流规则（app）
        logger.info("======= 加载流控规则 =======");
        Result<List<FlowRuleEntity>> result = SpringUtils.getBean(FlowControllerV1.class).apiQueryMachineRules(app, ip, port);
        logger.info(String.valueOf(result));
        logger.info("======= 加载流控规则 =======");
        // 加载熔断规则（app+ip+port）
        logger.info("======= 加载熔断规则 =======");
        Result<List<DegradeRuleEntity>> degradeRuleResult = SpringUtils.getBean(DegradeControllerV2.class).apiQueryMachineRules(app, ip, port);
        logger.info(String.valueOf(degradeRuleResult));
        logger.info("======= 加载熔断规则 =======");

        // 加载热点规则（app+ip+port）
        logger.info("======= 加载热点规则 =======");
        Result<List<ParamFlowRuleEntity>> paramFlowRuleResult = SpringUtils.getBean(ParamFlowRuleControllerV2.class).apiQueryAllRulesForMachine(app, ip, port);
        logger.info(String.valueOf(paramFlowRuleResult));
        logger.info("======= 加载热点规则 =======");

        // 加载系统规则（app+ip+port）
        logger.info("======= 加载系统规则 =======");
        Result<List<SystemRuleEntity>> systemRuleResult = SpringUtils.getBean(SystemControllerV2.class).apiQueryMachineRules(app, ip, port);
        logger.info(String.valueOf(systemRuleResult));
        logger.info("======= 加载系统规则 =======");

        // 加载授权规则（app+ip+port）
        logger.info("======= 加载授权规则 =======");
        Result<List<AuthorityRuleEntity>> authorityRuleResult = SpringUtils.getBean(AuthorityRuleControllerV2.class).apiQueryAllRulesForMachine(app, ip, port);
        logger.info(String.valueOf(authorityRuleResult));
        logger.info("======= 加载授权规则 =======");
    }

}
