---
title: 简介
id: overview
category: overview
---

# 产品简介

StreamNative Cloud 在公有云中提供了易于使用且全面托管的 Pulsar 服务。在 [StreamNative 官网](https://streamnative.io/)，单击**注册**，注册 StreamNative Cloud 服务，创建和管理 StreamNative Cloud 组件和 Pulsar 组件。注册云服务后，用户可以使用 StreamNative Cloud CLI 工具（snctl）管理 StreamNative Cloud 组件，也可以使用 StreamNative Cloud UI（StreamNative Cloud Manager）管理 StreamNative Cloud 组件和 Pulsar 组件。

snctl 支持管理以下 StreamNative Cloud 组件。

- [组织](/concepts/concepts.md#组织)
- [实例](/concepts/concepts.md#实例)
- [集群](/concepts/concepts.md#集群)
- [用户](/concepts/concepts.md#用户)
- [服务账户](/concepts/concepts.md#服务账户)
- [计费](/billing.md)

StreamNative Cloud Manager 支持管理以下 StreamNative Cloud 组件和 Pulsar 组件。

- StreamNative Cloud 组件
  - [组织](/concepts/concepts.md#组织)
  - [实例](/concepts/concepts.md#实例)
  - [集群](/concepts/concepts.md#集群)
  - [用户](/concepts/concepts.md#用户)
  - [服务账户](/concepts/concepts.md#服务账户)
  - [计费](/billing.md)

- Pulsar 组件

  - [租户](/concepts/concepts.md#租户)
  - [命名空间](/concepts/concepts.md#命名空间)
  - [主题](/concepts/concepts.md#主题)
  - [订阅](/concepts/concepts.md#订阅)
  - [Schema](/concepts/concepts.md#schema)

# 产品优势

- API 驱动，可轻松与连续交付管道集成。
- 支持 OAuth2 和 TLS（Transport Layer Security，传输层安全），确保集群安全。
- 提供用户和服务账户的统一身份管理。
- 基于最佳实践自动配置和维护 Pulsar 集群。
- 实现零停机、自动升级 Pulsar 集群。

# 产品限制

目前，StreamNative Cloud 尚不支持以下功能。

- [Pulsar Functions](/platform/latest/configure/pulsar-core/function)
- [Pulsar IO](/platform/latest/connect/pulsar-connector)
- [Pulsar SQL](https://pulsar.apache.org/docs/en/sql-overview/)
- [分层存储](/platform/latest/reference/concepts/tiered-storage)
- [Kafka-on-Pulsar (KoP)](/platform/latest/connect/kop/overview)
- [AMQP-on-Pulsar (AoP)](/platform/latest/connect/aop/overview)

# 安装 snctl

本节介绍如何在 Linux、Mac 和 Windows 操作系统上安装 snctl。

## 安装 snctl（Linux 系统）

1. 运行以下命令，下载并安装最新 snctl。

    ```bash
    sh -c "$(curl -fsSL https://downloads-streamnative-cloud.oss-cn-beijing.aliyuncs.com/snctl/install.sh)"
    ```
   
   如需下载特定版本的 snctl，使用 `-v` 或 `--version` 参数指定版本。例如，运行以下命令，下载并安装 snctl `v0.7.0`。
   
   ```bash
   sh -c "$(curl -fsSL https://downloads-streamnative-cloud.oss-cn-beijing.aliyuncs.com/snctl/install.sh) -v v0.7.0"
   ```

2. 查看 snctl 是否安装成功。

    ```bash
    snctl version --client
    ```

## 安装 snctl（Mac 系统）

1. 运行以下命令，下载并安装最新 snctl。

   ```bash
   sh -c "$(curl -fsSL https://downloads-streamnative-cloud.oss-cn-beijing.aliyuncs.com/snctl/install.sh)"
   ```
  
  如需下载特定版本的 snctl，使用 `-v` 或 `--version` 参数指定版本。例如，运行以下命令，下载并安装 snctl `v0.7.0`。
  
  ```bash
  sh -c "$(curl -fsSL https://downloads-streamnative-cloud.oss-cn-beijing.aliyuncs.com/snctl/install.sh) -v v0.7.0"
  ```

2. 查看 snctl 是否安装成功。

   ```bash
   snctl version --client
   ```

## 安装 snctl（Windows 系统）

1. 下载 snctl 最新安装包。

    > **说明**  
    > 根据使用的处理器架构更新 `ARCH` 参数。

    ```bash
    version=$(curl -s https://downloads-streamnative-cloud.oss-cn-beijing.aliyuncs.com/snctl/stable.txt)
    curl -LO https://downloads-streamnative-cloud.oss-cn-beijing.aliyuncs.com/${version}/snctl_${version#v}_windows_ARCH.tar.gz
    ```

2. 解压缩 snctl 安装包。

    ```bash
    tar -xf snctl_${version#v}_windows_ARCH.tar.gz
    ```

3. 将二进制文件添加到环境变量 `PATH`。

4. 查看 snctl 是否安装成功。

      ```bash
      snctl version --client
      ```

# 注册

如需注册 StreamNative Cloud 服务账户，遵循以下步骤。

1. 打开 [StreamNative Cloud 注册页面](https://console.cloud.streamnative.cn/?defaultMethod=signup)。

    ![signup](../image/signup.png)

2. 输入用户的名字、姓氏、公司名称、电子邮箱地址、密码以及确认密码。

    **密码规则**

   > 密码长度至少 8 位。
   >
   > 密码至少包括以下四种形式中的三种：大写字母（A～Z）、小写字母（a～z）、数字（0～9）或特殊字符（!、%、& 等）。

3. 查看[服务条款](https://streamnative.io/cloud-terms-and-conditions)和[隐私策略](https://streamnative.io/policy)。

4. 勾选单选框，接受[服务条款](https://streamnative.io/cloud-terms-and-conditions)和[隐私策略](https://streamnative.io/policy)。

5. 单击**注册**按钮。

6. 打开注册 StreamNative Cloud 时填写的电子邮箱，验证服务账户。

7. 在 StreamNative Cloud 的注册页面，单击 **Login** 按钮，即可登录 StreamNative Cloud。

# 登录

本节介绍如何登录 snctl 和 StreamNative Cloud Manager。

## 登录 snctl

> **说明**  
> - StreamNative Cloud 使用第三方 cookie 加载登录页面。Google Chrome 浏览器会阻止设置的第三方 cookie。当 Google Chrome 浏览器阻止第三方 cookie 时，用户的地址栏右侧会弹出 cookie 图标。单击该图标来检查网站允许和阻止的第三方 cookie，然后选择阻止或允许单个 cookie。
> - 默认情况下，Google Chrome 会阻止弹出式窗口自动显示在屏幕上。如果 Google Chrome 拦截了某个弹出式窗口，则会在地址栏中显示**已拦截弹出窗口**。

在登录 snctl 之前，用户需要安装 snctl 并注册 StreamNative Cloud 服务账户。有关注册 StreamNative Cloud 服务账户的详细信息，参见[注册](#注册)。

1. 使用 `cd` 命令切换到压缩文件 `.tar.gz.` 解压后的目录，然后打开一个终端窗口。

2. 使用缺省配置，初始化 snctl 配置。

    ```bash
    snctl config init --site cn
    ```

3. 运行以下命令，弹出一个对话框，如下所示。

    ```bash
    snctl auth login
    ```

    ![login-snctl](../image/login.png)

4. 单击地址栏右侧的 **cookie** 图标。

5. 单击**网站无法正常工作？**链接，查看网站允许或阻止的 cookies。然后单击**允许所有 cookies** 按钮。

6. 输入电子邮箱地址和密码，然后单击**登录**按钮。或者单击**使用 GitHub 登录**按钮，在弹出的页面中，单击**Authorize streamnative** 按钮，使用 GitHub 账户登录 snctl。

7. 单击地址栏右侧的**已拦截弹出窗口**图标。

8. 勾选“始终允许弹出窗口和重定向”，然后单击**完成**按钮。

## 登录 StreamNative Cloud Manager

> **说明**  
> - StreamNative Cloud 使用第三方 Cookie 来加载登录页面。StreamNative Cloud 会阻止设置的第三方 cookie。当 Google Chrome 浏览器阻止第三方 cookie 时，用户的地址栏右侧会弹出 cookie 图标。单击该图标来检查网站允许和阻止的第三方 cookie，然后选择阻止或允许单个 cookie。
> - 默认情况下，Google Chrome 会阻止弹出式窗口自动显示在屏幕上。如果 Google Chrome 拦截了某个弹出式窗口，则会在地址栏中显示**已拦截弹出窗口**。

在登录 StreamNative Cloud Manager 之前，用户需要注册 StreamNative Cloud 服务账户。有关注册 StreamNative Cloud 服务账户的详细信息，参见[注册](#注册)。

1. 打开 [StreamNative Cloud Manager 登录界面](https://console.cloud.streamnative.cn/?defaultMethod=login)。

    ![login](../image/login.png)

2. 单击地址栏右侧的 **cookie** 图标。

3. 单击**网站无法正常工作？**链接，查看网站允许或阻止的 cookies。然后单击**允许所有 cookies** 按钮。

4. 输入电子邮箱地址和密码，然后单击**登录**按钮。或者单击**使用 GitHub 登录**按钮，在弹出的页面中，单击**Authorize streamnative** 按钮，使用 GitHub 账户登录 StreamNative Cloud Manager。

5. 单击地址栏右侧的**已拦截弹出窗口**图标。

6. 勾选“始终允许弹出窗口和重定向”，然后单击**完成**按钮。

# 文档

- [简介](/overview.md)
- 基本概念
  - [基本概念](/concepts/concepts.md)
  - StreamNative Cloud API
    - [简介](/concepts/cloud-api/cloud-api.md)
    - [StreamNative Cloud 对象](/concepts/cloud-api/cloud-object.md)
- 快速入门
    - [快速入门（snctl）](/quickstart/quickstart-snctl.md)
    - [快速入门（StreamNative Cloud Manager）](/quickstart/quickstart-manager.md)
- 配置管理
  - [配置组织](/use/organization.md)
  - [配置实例](/use/instance.md)
  - [配置集群](/use/cluster.md)
  - [配置租户](/use/tenant.md)
  - [配置命名空间](/use/namespace.md)
  - [配置主题](/use/topic.md)
- 访问控制
  - [配置服务账户](/managed-access/service-account.md)
  - [配置用户](/managed-access/user.md)
  - [授权和 ACL](/managed-access/access-control.md)
- 连接集群
  - [简介](/connect/overview.md)
  - CLI
    - [访问集群（pulsarctl）](/connect/cli/connect-pulsarctl.md)
    - [访问集群（pulsar-admin）](/connect/cli/connect-pulsaradmin.md)
    - [访问集群（pulsar-client）](/connect/cli/connect-pulsarclient.md)
    - [访问集群（pulsar-perf）](/connect/cli/connect-pulsarperf.md)
  - 客户端
    - [访问集群（Java 客户端）](/connect/client/connect-java.md)
    - [访问集群（C++ 客户端）](/connect/client/connect-cpp.md)
    - [访问集群（Go 客户端）](/connect/client/connect-go.md)
    - [访问集群（Python 客户端）](/connect/client/connect-python.md)
    - [访问集群（Node.js 客户端）](/connect/client/connect-nodejs.md)
- 监控管理
    - [简介](/monitor/overview.md)
- [计费管理](/billing.md)
- 通用参考
  - [snctl CLI 参考](/reference/snctl-reference.md)
  - [API 参考](/api/cloud)
- [常见问题](/zh/faq)
- [发布记录]
  - [2020 年 11 月](/releases/november-2020.md)
