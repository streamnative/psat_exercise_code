---
title: 简介
id: overview
category: connect
---

StreamNative Cloud 支持用户使用 Pulsar CLI 工具或 Pulsar 客户端，通过 OAuth2 或 Token 认证方式访问 Pulsar 集群。

> **说明**  
> 目前，除 Pulsar Node.js 客户端以外，其他 Pulsar CLI 工具和 Pulsar 客户端均支持 OAuth2 或 Token 认证方式。Pulsar Node.js 客户端只支持 Token 认证方式。

- 支持的 Pulsar CLI 工具
  - [pulsarctl](/connect/cli/connect-pulsarctl.md)
  - [pulsar-admin](/connect/cli/connect-pulsaradmin.md)
  - [pulsar-client](/connect/cli/connect-pulsarclient.md)
  - [pulsar-perf](/connect/cli/connect-pulsarperf.md)

- 支持的 Pulsar 客户端
  - [Java 客户端](/connect/client/connect-java.md)
  - [C++ 客户端](/connect/client/connect-cpp.md)
  - [Go 客户端](/connect/client/connect-go.md)
  - [Python 客户端](/connect/client/connect-python.md)
  - [Node.js 客户端](/connect/client/connect-nodejs.md)

使用 StreamNative Cloud 创建集群后，如果用户使用 Pulsar CLI 工具或 Pulsar 客户端访问该 Pulsar 集群，需要获取以下信息：

- Service URL：访问 StreamNative Cloud 中的目标集群。
- OAuth2 或 Token 认证参数：认证用户身份。

# 获取 Service URL

Service URL 指 Pulsar 集群的 URL。用户可以使用该 Service URL 访问指定的 Pulsar 集群。

- `SERVICE_URL`：指定集群的 Service URL。Service URL 由协议名称、主机名、以及端口号组成，例如 pulsar+ssl://streamnative.cloud:6651。
- `WEB_SERVICE_URL`：指定集群的 Web Service URL。Web Service URL 由协议名称、主机名以及端口号组成，例如 https://streamnative.cloud:443。

运行以下命令，即可获取 Service URL 和 Web Service URL 中主机名字段的取值。

```shell script
snctl get pulsarcluster [PULSAR_CLUSTER_NAME] -n [PULSAT_CLUSTER_NAMESPACE] -o json | jq '.spec.serviceEndpoints[0].dnsName'
```

# 获取 OAuth2 认证参数

如需通过 OAuth2 认证方式访问 Pulsar 集群，需要指定以下参数的取值。

- `type`：OAuth2 认证类型。目前，该参数只能配置为 `client_credentials`。
- `clientId`：客户端 ID。
- `issuerUrl`：OAuth2 认证服务器的 URL。
- `privateKey`：JSON 密钥文件的 URL。
- `audience`：Pulsar 实例的标识符。

对于 `privateKey` 参数，用户可以使用以下命令，获取 OAuth2 密钥文件的路径。

```shell script
snctl auth export-service-account [SERVICE_ACCOUNT_NAME] -n [SERVICE_ACCOUNT_NAMESPACE] [flags]
```

对于 `clientId` 和 `issuerUrl` 参数，用户可以在已 OAuth2 密钥文件中获取相关取值，以下是 `clientId` 和 `issuerUrl` 参数取值的示例。

```shell
{
"type":"sn_service_account",
"client_id":"client-id",
"client_secret":"client-secret",
"client_email":"test@auth.streamnative.cloud.cn",
"issuer_url":"https://auth.cloud.streamnative.cn"
}
```

`audience` 参数由 `urn:sn:pulsar`、命名空间名称、Pulsar 实例的名称组成。有关 URN 的详细信息，参见 [URN](/conectps/overview.md#集群#urn)。以下是 `audience` 参数取值的示例。

```shell
urn:sn:pulsar:test-pulsar-instance-namespace:test-pulsar-instance-name
```

# 获取 Token 认证参数

如需通过 Token 认证方式访问 Pulsar 集群，需要使用获取的 Token 指定 `AUTH_PARAMS` 参数的取值。用户可以使用以下命令获取访问 Pulsar 集群的 Token。

```shell script
snctl auth get-token [PULSAR_INSTANCE_NAME] -n [PULSAR_INSTANCE_NAMESPACE] [flags]
```

以下示例说明如何使用 snctl 命令获取访问指定 Pulsar 集群的 Token。

```
snctl auth get-token test-pulsar-instance-name -n test-pulsar-instance-namespace --login
```

**命令回显**

```shell
We've launched your web browser to complete the login process.
Verification code: ABCD-EFGH

Waiting for login to complete...
Logged in as cloud@streamnative.io.
Welcome to Apache Pulsar!

Use the following access token to access Pulsar instance 'test-pulsar-instance-namespace/test-pulsar-instance-name':

abcdefghijklmnopqrstuiwxyz0123456789
```

> **说明**  
> 在代码编写过程中，为了代码的安全性和易用性，可以将 `AUTH_PARAMS` 参数设置为用户的环境变量。