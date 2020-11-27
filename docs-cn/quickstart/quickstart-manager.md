---
title: 快速入门（StreamNative Cloud Manager）
id: quickstart-manager
category: quickstart
---

# 准备工作

[注册 StreamNative Cloud 服务账户](https://console.cloud.streamnative.cn/?defaultMethod=signup)。有关注册 StreamNative Cloud 服务账户的详细信息，参见[注册](/overview.md#注册)。

# 步骤一：登录 StreamNative Cloud Manager

> **说明**  
> - StreamNative Cloud 使用第三方 cookie 加载登录页面。Google Chrome 浏览器会阻止设置的第三方 cookie。当 Google Chrome 浏览器阻止第三方 cookie 时，用户的地址栏右侧会弹出 cookie 图标。单击该图标来检查网站允许和阻止的第三方 cookie，然后选择阻止或允许单个 cookie。
> - 默认情况下，Google Chrome 会阻止弹出式窗口自动显示在屏幕上。如果 Google Chrome 拦截了某个弹出式窗口，则会在地址栏中显示**已拦截弹出窗口**。

1. 打开 [StreamNative Cloud Manager 登录界面](https://console.cloud.streamnative.cn/?defaultMethod=login)。

2. 单击地址栏右侧的 **cookie** 图标。

3. 单击**网站无法正常工作？**链接，查看网站允许或阻止的 cookies。然后单击**允许所有 cookies** 按钮。

4. 输入电子邮箱地址和密码，然后单击**登录**按钮。或者单击**使用 GitHub 登录**按钮，在弹出的页面中，单击**Authorize streamnative** 按钮，使用 GitHub 账户登录录 StreamNative Cloud Manager。

5. 单击地址栏右侧的**已拦截弹出窗口**图标。

6. 勾选“始终允许弹出窗口和重定向”，然后单击**完成**按钮。

# 步骤二：创建组织

1. 在 StreamNative Cloud Manager 主界面，单击**创建组织**按钮。

2. 在弹出的页面，输入组织名称，然后单击**确定**按钮。组织名称必须唯一且易于记忆。采用字符串形式，最多支持 12 个字符。支持小写字母（a～z）、数字（0～9）、特殊字符（“-”）。

# 步骤三：创建实例

1. 单击新创建的组织，进入创建实例的页面。然后单击**创建实例**按钮。

2. 在弹出的页面，配置实例参数，如下所示。然后单击**继续**按钮。

   - **实例名称**：输入实例名称，字符串形式，支持小写字母（a～z）、数字（0～9）、特殊字符（“-”）。
   - **云服务提供商**：选择云服务提供商。目前，只支持**阿里云**。
   - **集群类型**：选择集群类型，目前，支持**免费版**和**标准版**集群。
   - **可用区**：选择实例部署模式。支持**单 AZ** 和**多 AZ** 两种模式。如果**集群类型**配置为**免费版**，则该选项不可用。

3. 单击**继续**按钮。在弹出的页面中，用户可以查看实例的创建进度。

# 步骤四：创建集群

StreamNative Cloud 支持创建免费集群和标准集群。以下举例说明如果创建标准集群。有关创建免费集群的详细信息，参见[创建集群（StreamNative Cloud Manager）](/use/cluster.md#创建集群snctl)。

> **说明**  
> 在本版本中，每个实例只能配置一个集群。

1. 在左侧导航栏，单击**管理 > 集群**。

2. 在弹出的页面，配置集群参数，如下所示。然后单击**添加集群**按钮。

   - **集群名称**：输入集群名称，字符串形式，支持小写字母（a～z）、数字（0～9）、特殊字符（“-”）。
   - **地域**：选择集群位置。
   - **Bookie 节点**：配置集群的 bookie 参数，包括硬件参数和 pod 参数。免费集群不支持 bookie 参数。
   - **Broker**：配置集群的 broker 参数，包括硬件参数和 pod 参数。免费集群只支持 `tiny-1` 选项。

# 步骤五：创建租户

1. 在左侧导航栏，单击**租户**。

2. 在弹出的页面，单击**创建租户**按钮。

3. 配置租户参数，如下所示。然后单击**确定**按钮。

   - **租户名称**：输入租户名称，字符串形式，支持小写字母（a～z）、数字（0～9）、特殊字符（“-”）。
   - **所属集群**：选择租户所在的集群。
   - **Admin 角色**：为租户配置一个或多个 Admin 角色。

# 步骤六：创建命名空间

1. 在左侧导航栏，单击**命名空间**。

2. 在弹出的页面，单击**创建命名空间**按钮。

3. 输入命名空间的名称，然后单击**确定**按钮。命名空间的名称，字符串形式，支持小写字母（a～z）、数字（0～9）、特殊字符（“-”）。

# 步骤七：创建主题

1. 在左侧导航栏，单击**主题**。

2. 在弹出的页面，单击**创建主题**按钮。

3. 在弹出的页面，配置主题的相关参数，如下所示。然后单击**确定**按钮。

   - **主题类型**：配置主题类型，支持持久性主题和非持久性主题。
   - **主题名称**：输入主题名称，字符串形式，支持小写字母（a～z）、数字（0～9）、特殊字符（“-”）。
   - **分区**：配置分区的数量。缺省情况下，最多支持 16 个分区。如果取值为零，则表示无分区主题。

# 步骤八：连接集群

以下举例说明 Pulsar Java 客户端如何使用 OAuth2 认证插件连接集群。有关其他 Pulsar CLI 工具或 Pulsar 客户端连接集群的详细信息，参见[连接集群](/connect/overview.md)。

1. 获取 Service URL。有关如何获取 Service URL 的详细信息，参见[获取 Pulsar Service URL](/connect/overview.md#获取-service-url)。

2. 获取 OAuth2 认证参数。有关如何获取 OAuth2 认证参数的详细信息，参见[获取 OAuth2 认证参数](/connect/overview.md#获取-oauth2-authentication-parameters)。

3. 使用 OAuth2 认证插件，连接集群。根据获取的 Service URL 和 OAuth2 认证参数，配置 `serviceUrl`、`issuer_url`、 `audience`、 `credentialsUrl` 参数。

    ```java
    PulsarClient client = PulsarClient.builder()
    .serviceUrl("pulsar+ssl://streamnative.cloud:6651")
    .authentication(
        AuthenticationFactoryOAuth2.clientCredentials(this.issuerUrl, this.credentialsUrl, this.audience))
    .build();
    ```

4. 创建 Pulsar consumer，消费消息。

    ```Java
    Consumer<byte[]> consumer = client.newConsumer(Schema.BYTES)
            .topic(topic)
            .subscriptionName("sub-1")
            .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
            .subscribe();

    for (int i = 0; i < 10; i++) {
        Message<byte[]> msg = consumer.receive();
        consumer.acknowledge(msg);
        System.out.println("Receive message " + new String(msg.getData()));
    }
    ```

5. 创建 Pulsar producer，发布消息。

    ```Java
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(jct.serviceUrl)
                .authentication(
                        AuthenticationFactoryOAuth2.clientCredentials(new URL(jct.issuerUrl), new URL(jct.credentialsUrl), jct.audience))
                .build();

        ProducerBuilder<byte[]> producerBuilder = client.newProducer().topic(topic)
                .producerName("my-producer-name");
        Producer<byte[]> producer = producerBuilder.create();

        for (int i = 0; i < 10; i++) {
            String message = "my-message-" + i;
            MessageId msgID = producer.send(message.getBytes());
            System.out.println("Publish " + "my-message-" + i + " and message ID " + msgID);
        }
    ```

# 步骤九：退出 StreamNative Cloud Manager

单击 StreamNative Cloud Manager 主界面右上角用户账户右侧的下拉菜单，选择**注销**选项，退出 StreamNative Cloud Manager。