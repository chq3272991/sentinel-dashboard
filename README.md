# Sentinel 控制台

## 0. 概述

Sentinel 控制台是流量控制、熔断降级规则统一配置和管理的入口，它为用户提供了机器自发现、簇点链路自发现、监控、规则配置等功能。在 Sentinel 控制台上，我们可以配置规则并实时查看流量控制效果。

## 1. 编译和启动

### 1.1 如何编译

使用如下命令将代码打包成一个 fat jar:

```bash
mvn clean package
```

### 1.2 如何启动

使用如下命令启动编译后的控制台：

```bash
java -Dserver.port=8858 \
-Dcsp.sentinel.dashboard.server=localhost:8858 \
-Dproject.name=nacos-sentinel-dashboard \
-Dsentinel.nacos.serverAddr=localhost:8848 \
-Dsentinel.nacos.namespace=032d1419-b3c4-48e9-bc0e-f83997dae13c \
-Dsentinel.nacos.groupId=DEFAULT_GROUP \
-Dsentinel.nacos.username=nacos \
-Dsentinel.nacos.password=nacos \
-Dauth.username=sentinel \
-Dauth.password=sentinel \
-jar target/nacos-sentinel-dashboard.jar
```

上述命令中我们指定几个 JVM 参数，其中 `-Dserver.port=8858` 是 Spring Boot 的参数，
用于指定 Spring Boot 服务端启动端口为 `8858`。其余几个是 Sentinel 客户端的参数。

为便于演示，我们对控制台本身加入了流量控制功能，具体做法是引入 Sentinel 提供的 `CommonFilter` 这个 Servlet Filter。
上述 JVM 参数的含义是：

| 参数                                               | 作用                                                   |
|--------------------------------------------------|------------------------------------------------------|
| `-Dcsp.sentinel.dashboard.server=localhost:8858` | 向 Sentinel 接入端指定控制台的地址                               |
| `-Dproject.name=nacos-sentinel-dashboard`          | 向 Sentinel 指定应用名称，比如上面对应的应用名称就为 `sentinel-dashboard` |
| `-Dsentinel.nacos.serverAddr`                    | Sentinel的nacos数据源地址(利用nacos实现Sentinel配置持久化)          |
| `-Dsentinel.nacos.username`                      | 非必填，默认nacos                                          |
| `-Dsentinel.nacos.password`                      | 非必填，默认nacos                                          |
| `-Dsentinel.nacos.namespace`                     | 非必填，命名空间ID，建议nacos设置独立命名空间供Sentinel使用                |
| `-Dsentinel.nacos.groupId`                       | 非必填，默认DEFAULT_GROUP                                  |
| `-Dauth.username`                                | 非必填，sentinel控制台登录账户名                                 |
| `-Dauth.password`                                | 非必填，sentinel控制台登录密码                                  |

全部的配置项可以参考 [启动配置项文档](https://github.com/alibaba/Sentinel/wiki/%E5%90%AF%E5%8A%A8%E9%85%8D%E7%BD%AE%E9%A1%B9)。

经过上述配置，控制台启动后会自动向自己发送心跳。程序启动后浏览器访问 `localhost:8858` 即可访问 Sentinel 控制台。

从 Sentinel 1.6.0 开始，Sentinel 控制台支持简单的**登录**功能，默认用户名和密码都是 `sentinel`。用户可以通过如下参数进行配置：

- `-Dserver.servlet.session.timeout=7200` 用于指定 Spring Boot 服务端 session 的过期时间，如 `7200` 表示 7200 秒；`60m` 表示 60 分钟，默认为 30 分钟；

## 2. 客户端接入

选择合适的方式接入 Sentinel，然后在应用启动时加入 JVM 参数 `-Dcsp.sentinel.dashboard.server=consoleIp:port` 指定控制台地址和端口。
确保客户端有访问量，**Sentinel 会在客户端首次调用的时候进行初始化，开始向控制台发送心跳包**，将客户端纳入到控制台的管辖之下。

客户端接入的详细步骤请参考 [Wiki 文档](https://github.com/alibaba/Sentinel/wiki/%E6%8E%A7%E5%88%B6%E5%8F%B0#3-%E5%AE%A2%E6%88%B7%E7%AB%AF%E6%8E%A5%E5%85%A5%E6%8E%A7%E5%88%B6%E5%8F%B0)。

## 3. 验证是否接入成功

客户端正确配置并启动后，会**在初次调用后**主动向控制台发送心跳包，汇报自己的存在；
控制台收到客户端心跳包之后，会在左侧导航栏中显示该客户端信息。如果控制台能够看到客户端的机器信息，则表明客户端接入成功了。


# 其他
## 构建Docker镜像

```bash
docker build -t nacos-sentinel-dashboard:1.8.7 --no-cache .
```

## docker方式快速启动
```bash
docker run -d --name nacos-sentinel-dashboard  \
-p 8858:8858 \
-e SENTINEL_NACOS_SERVERADDR=my-nacos:8848 \
-e SENTINEL_NACOS_NAMESPACE=032d1419-b3c4-48e9-bc0e-f83997dae13c \
-e SENTINEL_NACOS_USERNAME=nacos \
-e SENTINEL_NACOS_PASSWORD=nacos \
-e AUTH_USERNAME=sentinel \
-e AUTH_PASSWORD=sentinel \
nacos-sentinel-dashboard:1.8.7
```

*注意：Sentinel 控制台目前仅支持单机部署。Sentinel 控制台项目提供 Sentinel 功能全集示例，不作为开箱即用的生产环境控制台，不提供安全可靠保障。若希望在生产环境使用请根据[文档](https://github.com/alibaba/Sentinel/wiki/%E5%9C%A8%E7%94%9F%E4%BA%A7%E7%8E%AF%E5%A2%83%E4%B8%AD%E4%BD%BF%E7%94%A8-Sentinel)自行进行定制和改造。*

更多：[控制台功能介绍](./Sentinel_Dashboard_Feature.md)。
