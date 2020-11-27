---
title: 访问集群（pulsarctl）
id: connect-pulsarctl
category: connect
---

pulsarctl 是一款基于 Pulsar REST API、使用 Go 语言的 CLI 工具。pulsarctl 用于控制和管理集群、租户、命名空间、主题、schema、source、sink、Pulsar Functions 等。

# 前提条件

- 安装 pulsarctl 0.5.0 或以上版本。

# 访问 Pulsar 集群（OAuth2）

`pulsarctl` 支持通过 CLI 命令行或 Go Admin API 访问 Pulsar 集群。

## CLI 命令行

如需通过 CLI 命令后访问集群，遵循以下步骤。

1. 获取 Web service URL。有关如何获取 Web service URL 的详细信息，参见[获取 Service URL](/connect/overview.md#获取-service-url)。

2. 获取 OAuth2 认证参数。有关如何获取 OAuth2 认证参数的详细信息，参见[获取 OAuth2 认证参数](/connect/overview.md#获取-oauth2-认证参数)。

3. 使用 OAuth2 密钥文件激活账户。

    ```shell script
    pulsarctl oauth2 activate \
    --issuer-endpoint https://api.cloud.streamnative.cn \
    --client-id abcdefghigk0123456789 \
    --audience urn:sn:pulsar:pulsar-instance-ns:pulsar-instance-name \
    --key-file /path/to/private/key/file.json
    ```

    将 `issuer-endpoint` 参数配置为已获取的 Web service URL。  
    根据获取的 OAuth2 认证参数，配置 `key-file`、`audience` 参数的取值。

4. 使用 pulsarctl CLI 命令获取 Pulsar 资源。

    ```shell script
    pulsarctl namespaces list public \
    --admin-service-url https://streamnative.cloud:443 \
    --issuer-endpoint https://api.cloud.streamnative.cn \
    --client-id abcdefghigk0123456789 \
    --audience urn:sn:pulsar:pulsar-instance-ns:pulsar-instance-name \
    --key-file /path/to/private/key/file.json
    ```

## Go Admin API

如果通过 Go Admin API 访问集群，遵循以下步骤。

1. 获取 Web service URL。有关如何获取 Web service URL 的详细信息，参见[获取 Service URL](/connect/overview.md#获取-service-url)。

2. 获取 OAuth2 认证参数。有关如何获取 OAuth2 认证参数的详细信息，参见[获取 OAuth2 认证参数](/connect/overview.md#获取-oauth2-认证参数)。

3. 使用 OAuth2 认证方式，访问指定的 Pulsar 集群。

    ```go

    func main() {
        flag.Parse()

        if help {
            flag.Usage()
        }

        pulsarCtlConfig := &common.Config{
            WebServiceURL:              webServiceURL,
            TLSAllowInsecureConnection: true,
        }
        issuer := oauth2.Issuer{
            IssuerEndpoint: issuerUrl,
            ClientID:       clientId,
            Audience:       audience,
        }
        oauth, err := auth.NewAuthenticationOAuth2WithDefaultFlow(issuer, privateKey)
        if err != nil {
            log.Fatal(err)
        }
        admin := pulsar.NewWithAuthProvider(pulsarCtlConfig, oauth)
        ns, err := admin.Namespaces().GetNamespaces("public")
        if err != nil {
            log.Fatal(err)
        }
        fmt.Printf("the namespace is: %s\n", ns)
    }
    ```

    将 `webServiceURL` 参数配置为已获取的 Web service URL。  
    根据获取的 OAuth2 认证参数，配置 `issuerUrl`、`clientId`、`audience` 参数的取值。

# 访问 Pulsar 集群（Token）

如需使用 pulsarctl CLI 工具，通过 Token 认证方式访问 Pulsar 集群，遵循以下步骤。

1. 获取 Web service URL。有关如何获取 Web service URL 的详细信息，参见[获取 Service URL](/connect/overview.md#获取-service-url)。

2. 获取 Token 认证参数。有关如何获取 Token 认证参数的详细信息，参见[获取 Token 认证参数](/connect/overview.md#获取-token-认证参数)。

3. 使用 Token 认证方式，访问指定的 Pulsar 集群。

    ```shell script
    pulsarctl \
        --admin-service-url WEB_SERVICE_URL \
        --token AUTH_PARAMS \
        tenants list
    ```

    将 `WEB_SERVICE_URL` 参数配置为已获取的 Web service URL。  
    将 `AUTH_PARAMS` 参数配置为已获取的 Token。

有关如何使用 pulsarctl CLI 工具管理 Pulsar 组件的详细信息，参见[使用 pulsarctl](/platform/latest/manage-and-monitor/pulsarctl/use)。