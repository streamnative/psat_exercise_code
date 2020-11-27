---
title: 访问集群（pulsar-client）
id: connect-pulsarclient
category: connect
---

本文介绍如何使用 pulsar-client CLI 工具访问 Pulsar 集群。

# 前提条件

- 安装 Pulsar 客户端 2.6.1 或以上版本。

# 访问 Pulsar 集群（OAuth2）

如需使用 pulsar-client CLI 工具，通过 OAuth2 认证方式访问 Pulsar 集群，遵循以下步骤。

1. 获取 Service URL。有关如何获取 Service URL 的详细信息，参见[获取 Service URL](/connect/overview.md#获取-service-url)。

2. 获取 OAuth2 认证参数。有关如何获取 OAuth2 认证参数的详细信息，参见[获取 OAuth2 认证参数](/connect/overview.md#获取-oauth2-认证参数)。

3. 使用 OAuth2 认证方式，访问指定的 Pulsar 集群。

    ```shell script
    bin/pulsar-client \
    --url SERVICE_URL \
    --auth-plugin org.apache.pulsar.client.impl.auth.oauth2.AuthenticationOAuth2 \
    --auth-params '{"privateKey":"file:///path/to/key/file.json",
        "issuerUrl":"https://auth.cloud.streamnative.cn",
        "audience":"urn:sn:pulsar:test-pulsar-instance-namespace:test-pulsar-instance"}' \
    produce test-topic -m "test-message" -n 10
    ```

    将 `SERVICE_URL` 参数配置为已获取的 service URL。  
    根据获取的 OAuth2 认证参数，配置 `issuerUrl`、`privateKey`、`audience` 参数的取值。

# 访问 Pulsar 集群（Token）

如需使用 pulsar-client CLI 工具，通过 Token 认证方式访问 Pulsar 集群，遵循以下步骤。

1. 获取 Service URL。有关如何获取 Service URL 的详细信息，参见[获取 Service URL](/connect/overview.md#获取-service-url)。

2. 获取 Token 认证参数。有关如何获取 Token 认证参数的详细信息，参见[获取 Token 认证参数](/connect/overview.md#获取-token-认证参数)。

3. 使用 Token 认证方式，访问指定的 Pulsar 集群。

    ```shell script
    ./bin/pulsar-client \
        --url SERVICE_URL \
        --auth-plugin org.apache.pulsar.client.impl.auth.AuthenticationToken \
        --auth-params token:AUTH_PARAMS \
        produce test-topic -m "test-message" -n 10
    ```

    将 `SERVICE_URL` 参数配置为已获取的 Service URL。  
    将 `AUTH_PARAMS` 参数配置为已获取的 Token。

有关如何使用 pulsar-client CLI 工具管理 Pulsar 组件的详细信息，参见 [pulsar-client 举例](https://pulsar.apache.org/docs/en/next/reference-cli-tools#pulsar-client)。