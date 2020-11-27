---
title: 基本概念
id: concepts
category: concepts
---

StreamNative Cloud 是业界唯一由 Apache Pulsar 提供技术支持、全面托管、云原生的消息和事件流平台。Apache Pulsar 是一个开源的、分布式发布/订阅消息和事件流平台，助力全球的行业领导者能够大规模构建发布/订阅消息传递和事件驱动的应用程序。StreamNative Cloud 由 Apache Pulsar 和 Apache BookKeeper 的创始团队成员进行构建和运营，为企业提供了可扩展的、灵活的、安全的消息传递和事件流平台。用户可以通过 [StreamNative 官网](https://streamnative.io/)注册 StreamNative Cloud，创建和管理 StreamNative Cloud 组件和 Pulsar 组件。

- StreamNative Cloud 组件
  - [组织](#组织)
  - [用户](#用户)
  - [服务账户](#服务账户)
  - [实例](#实例)
  - [集群](#集群)
  - [计费](/billing.md)

- Pulsar 组件
  - [租户](#租户)
  - [命名空间](#命名空间)
  - [主题](#主题)
  - [订阅](#订阅)
  - [Schema](#schema)

# StreamNative Cloud 组件

## 组织

组织适用于有多位用户的应用场景，尤其当这些用户属于不同的团队。组织用于在多个用户之间（通过资源配额）划分集群资源。在组织内，资源名称必须独一无二，且每种资源只能存在于一个组织中。StreamNative Cloud 不支持互相嵌套组织。

在 StreamNative Cloud 上注册服务时，用户需要为该组织创建一个计费帐户并提供相关联系信息。用户所在的组织拥有 Pulsar 集群和其他资源。组织的管理员可以通过添加和删除成员以及向成员授予权限来控制其对组织资源的访问。

有关创建组织的详细信息，参见[配置组织](/use/organization.md)。

## 用户

StreamNative Cloud 支持使用电子邮箱地址标识用户，并通过社交登录名称或简单的用户名/密码对用户进行验证。组织的管理员可以邀请用户加入本组织。用户会收到一封电子邮件。用户按照邮件提示，即可完成注册。

有关创建用户或邀请用户的详细信息，参见[配置用户](/managed-access/user.md)。

## 服务账户

用户可以创建服务帐户，自动执行某些操作，例如对在组织内运行的机器人进行验证。或者，用户可以为 GitHub 操作或 Jenkins 任务配置服务帐户，从而自动设置 Pulsar 集群。创建服务帐户时，用户会收到一个被称之为密钥文件的 JSON 文件，其中包含该服务帐户的 token。用户需要妥善保存该密钥文件。当用户访问 StreamNative Cloud API 和托管 Pulsar 集群时，StreamNative Cloud 支持使用密钥文件对用户进行验证。

有关创建服务账户的详细信息，参见[配置服务账户](/managed-access/service-account.md)。

### 访问控制

StreamNative Cloud 支持基于角色的访问控制。组织管理员可以为用户和服务帐户分配角色，授予其访问资源的权限。只有被授予相关权限的用户才能访问 StreamNative Cloud API 和 Pulsar 集群。

所有组织都配置“管理员”角色。“管理员”角色可以全面掌控组织资源，包括对组织的 Pulsar 集群的“超级访问”。如需用户或服务帐户访问特定资源，需要为用户或服务账户绑定相关角色。有关为用户或服务账户绑定角色的详细信息，参见[绑定角色](/quickstart/quickstart-snctl.md#步骤五为服务账户配置角色)。

> **说明**  
> 将来的版本支持自定义更细颗粒度的权限。例如，为特定集群（而不是所有集群）授予超级用户权限。

## 实例

Pulsar 实例由一个或多个 Pulsar 集群组成。集群可以分布在不同地理位置。用户可以使用跨地域复制功能在集群之间复制数据，从而备份数据，提高数据安全性。有关配置管理实例的详细信息，参见[配置实例](/use/instance.md)。

> **说明**  
> 目前，每个实例只能配置一个集群。

### 订阅计划

订阅计划与用户在 StreamNative Cloud 中创建的 Pulsar 实例（集群）相关。目前，StreamNative Cloud 只支持 `SN_HT_PSR_FREE_SAZ_ALI` 订阅计划。

### 可用区

StreamNative Cloud 支持根据用户的工作负载和预算创建 regional 或 zonal 集群。

- Regional 集群支持在指定区域内的多个 AZs（ Availability Zones，可用区）上运行副本。这种配置可以最大程度地提高可用性，但由于跨多个 AZ，需要更多的网络流量。在当前版本中，每个 regional 集群可以使用三个 AZ。

- Zonal 集群支持在单个 AZ 中运行所有副本，实现更好的性能和更低的成本。与此同时，集群可用性也有所降低。

## 集群

Pulsar 集群由一组 Pulsar broker、ZooKeeper 服务器和 bookie 组成。Pulsar 实例由一个或多个 Pulsar 集群组成。集群可以分布在不同地理位置。用户可以使用跨地域复制功能在集群之间复制数据，从而备份数据，提高数据安全性。

有关配置管理集群的详细信息，参见[配置集群](/use/cluster.md)。

### 集群类型

StreamNative Cloud 的集群类型决定集群的功能、使用阈值和价格。目前，StreamNative Cloud 只支持免费集群。

#### 免费集群

免费集群用于存储数据和元数据信息。创建免费集群后，如果用户在 30 天内不采取任何操作，该免费集群将被删除。

下表列出 StreamNative Cloud 免费集群支持的一些特性以及相关的使用阈值。

| 参数 | 描述 |
| --- | --- |
| **Scale** |
| 最大存储（基于主题） | 1 GB |
| 最大租户数 | 4 |
| 最大主题数 | 100 |
| 最大 producer 数（基于主题）| 30 |
| 最大 consumer 数（基于主题）| 50 |
| **Pulsar 组件** |
| Pub/Sub | 支持 |
| **云服务提供商**|
| 阿里云 | 支持 |
| **监控 & 诊断**  |
| Cloud manager | 支持 |
| **安全性** |
| 静态加密 & 传输中加密 | 支持 |
| OAuth2 认证 | 支持 |

#### 标准集群

标准集群支持以下功能，适用于生产环境。

- 支持高达 99.95% uptime SLA。
- 提供高达 100 MBps 的吞吐量和 5 TB 的存储。
- 支持多 AZ。
- 除按接收、发送、存储的数据量计费外，还支持按小时进行收费。

下表列出 StreamNative Cloud 标准集群支持的一些特性以及相关的使用阈值。

| 参数 | 描述 |
| --- | --- |
| Uptime SLA | 99.95% |
| 单 AZ| 支持 |
| 多 AZ | 支持 |
| **Scale** |
| 最大吞吐量 | 100 MBps |
| 最大存储 | 5 TB |
| 最大租户数 | 128 |
| 最大命名空间数 | 1024 |
| 最大主题数 | 10240 |
| **Pulsar 组件** |
| 发布/订阅 | Yes |
| **云服务提供商**|
| GCP | 支持 |
| **监控 & 诊断** |
| Cloud manager | 支持 |
| **安全性** |
| 静态加密 & 传输中加密 | 支持 |
| OAuth2 认证 | 支持 |

### 集群位置

所有 Pulsar 集群均需配置地理位置。下表列出 StreamNative Cloud 支持的集群位置。

| 参数 | 位置 |
| --- | --- |
| cn-zjk | 张家口，河北省，中国 |

### 集群配置

Pulsar 集群的安全性、网络、计算、和存储等参数不仅与 Pulsar 架构相关，同时也取决于用户采用的 Pulsar 组件。StreamNative Cloud 支持根据用户的负载和预算自动配置 Pulsar 集群的安全性，网络、计算和存储等参数。

#### 计算参数

下表列出集群支持的计算参数（节点类型）。

| 参数 | 描述 | CPU | 内存  |
| --- | --- | --- | --- |
| tiny-1 | 适用于微型集群。| <br>- Broker: 200 mv <br>- Bookie: 200 mv | <br>- Broker: 256 Mi <br>- Bookie: 256 Mi |
| micro-1 | 适用于占用空间较小的小型集群。免费集群不支持该选项。| <br>- Broker: 2 v <br>- Bookie: 1 v| <br>- Broker: 1 Gi <br>- Bookie: 1 Gi |
| small-1 | 适用于占用空间小的小型集群。免费集群不支持该选项。| <br>- Broker: 2 v <br>- Bookie: 1 v | <br>- Broker: 2 Gi <br>- Bookie: 2 Gi |
| medium-1 | 适用于占用空间适中的中等集群。免费集群不支持该选项。| <br>- Broker: 4 v <br>- Bookie: 2 v| <br>- Broker: 4 Gi <br>- Bookie: 4 Gi |
| large-1 | 适用于占用空间较大的大型集群。免费集群不支持该选项。| <br>- Broker: 8 v <br>- Bookie: 4 v| <br>- Broker: 8 Gi <br>- Bookie: 8 Gi|

#### Broker 参数

下表列出集群支持的 broker 参数。

| 参数 | 缺省值 | 描述 |
| --- | --- |--- |
| 副本 | 1 | Broker 节点的数量。免费集群只支持一个 broker 节点。|
| 节点类型 | tiny-1 | Broker 节点的计算特性（CPU、内存等）。|

#### BookKeeper 参数

> **说明**
>
> 免费集群不支持 BookKeeper 参数。

下表列出集群支持的 BookKeeper 参数。

| 参数 | 缺省值 | 描述 |
| --- | --- |--- |
| 副本 | 3 | Bookie 节点的数量。|
| 节点类型 | tiny-1  | Bookie 节点的计算特性（CPU、内存等）。|

### Service URL

Service URL 是面向 Internet 公开的 HTTPS 端点，并支持 OAuth 2.0 认证。配置集群后，用户即可通过 Service URL 访问 Pulsar 集群。有关如何获取 Service URL 的详细信息，参见[获取 Pulsar Service URL](/connect/overview.md#获取-service-url)。

### URN

实例由 URN （Uniform Resource Name，统一资源名称）标识。对于 StreamNative Cloud 而言，URN 的格式为 “urn:sn:pulsar"ORGANIZATION-NAME:INSTANCE-NAME”。用户可以使用 “snctl get organization” 和 “snctl get pulsarinstances” 命令获取组织名称和实例名称。当 Pulsar 客户端通过 OAuth2 验证插件连接到 Pulsar 集群时，URN 是必填字段。

# Pulsar 组件

## 租户

Pulsar 是一个多租户系统。为了支持多租户，Pulsar 提出租户的概念。租户分布在集群中，每个租户都可以配置、使用自己的认证和授权方案。租户是管理堆积量、消息 TTL（Time To Live，存活时间）、隔离策略的管理单元。

有关创建租户的详细信息，参见[配置租户](/use/tenant.md)。

## 命名空间

命名空间是租户内的管理单元。在命名空间上设置的配置策略适用于该命名空间中的所有主题。用户可以使用 StreamNative Cloud Manager、REST API 或 pulsar-admin CLI 工具为租户创建多个命名空间。

有关创建命名空间的详细信息，参见[配置命名空间](/use/namespace.md)。

### 权限

Pulsar 支持在命名空间级别管理权限（即在租户和集群内部）。Pulsar 支持为特定用户授予一系列操作权限，例如 `produce` 和 `consume`。Pulsar 也支持撤消特定用户的权限，这意味着这些用户无法访问指定的命名空间。

### 堆积量

Backlog 指 bookie 存储的主题中未签收消息的集合。Pulsar 将所有未签收的消息存储在 Backlog 中，直到它们得到处理或签收。用户可以配置命名空间的堆积量。Pulsar 支持配置以下堆积量参数：

- 命名空间中，每个主题的最大堆积量。
- 当达到最大堆积量时，broker 端采取的保留策略。

下表列出 StreamNative Cloud 支持的保留策略。

| 策略 | 操作 |
| --- | --- |
| `producer_request_hold` | Broker 保留生产者发布的消息，但并不会持久化存储发布的消息。|
| `producer_exception` | Broker 断开与客户端的连接，并抛出异常。|
| `consumer_backlog_eviction` | Broker 开始丢弃堆积的消息。|

### Bundle

为了分配流量，用户需要为命名空间配置一定数量的 bundle。Bundle 指属于同一命名空间的主题的虚拟组。每个 bundle 是命名空间的整体哈希范围的一部分。Pulsar 将 bundle 定义为两个 32 位哈希值之间的范围，例如 0x00000000 和 0xffffffff。缺省情况下，每个命名空间都支持四个 bundle。

由于 bundle 中主题的负载可能会随时间变化，因此 broker 可以将一个 bundle 分成两个较小的 bundle。然后，再将较小的 bundle 重新分配给其他 broker。缺省情况下，拆分的 bundle 会立即卸载数据到其他 broker，以便分配流量。

### 流量控制

流量控制指命名空间中的主题每秒发送的消息的数量。Pulsar 支持按照每秒发送的消息数量（`msg-dispatch-rate`）或者每秒发送的字节数量（`byte-dispatch-rate`）来表示流量控制。流量控制以秒为单位。用户可以配置 `dispatch-rate-period` 参数，更改流量控制的单位。缺省情况下，`msg-dispatch-rate` 和 `byte-dispatch-rate` 参数均设置为 -1，表示禁用流量控制。

## 主题

与其他发布订阅系统一样，Pulsar 中的主题也被称为通道，用于将消息从生产者传输到消费者。Pulsar 支持持久性和非持久性主题。缺省情况下，如果用户未指定主题类型，则系统会创建一个持久性主题。持久性主题的所有消息都持久地保留在磁盘上（如果 broker 不是独立的，则消息持久地保留在多个磁盘上），而非持久性主题的消息不会持久地存储到磁盘中。

有关主题的详细信息，参见[主题](/platform/latest/reference/concepts/messaging#topics)。

### 非持久性主题

缺省情况下，Pulsar 将所有未确认的消息保存到多个 BookKeeper 的 bookie 中（存储节点）。即使 broker 重启或者订阅者出现问题，持久性主题的消息也可以存活下来。同时，Pulsar 也支持非持久性主题。Pulsar 不会将这非持久性主题的消息持久地存储到磁盘，而是只将其存储在内存中。Broker 会立即发布消息给所有连接的订阅者，而不会在 BookKeeper 中存储消息。如果 broker 发生故障或者订阅者断开链接，该非持久性主题的所有瞬时消息都会丢失。这意味着 Pulsar 客户端可能无法接收到完整的消息。

有关非持久性主题的详细信息，参见[非持久主题](/platform/latest/reference/concepts/messaging#nonpersistent-topics)。

### 分区主题

正常情况下，只有一个 broker 为主题提供服务，这就限制了主题的最大吞吐量。分区主题是一种特殊类型的主题，可以由多个 broker 为其提供服务，因此可以提高吞吐量。实际上，分区主题由 N 个内部主题组成，其中 N 指分区的数量。当生产者将消息发布到分区主题时，每条消息都会被路由到其中的一个 broker。Pulsar 自动处理跨 broker 的分区分配问题。

有关分区主题的详细信息，参见[分区主题](/platform/latest/reference/concepts/messaging#partitioned-topics)。

## 订阅

在 Pulsar 中，订阅指已命名的配置规则，用于确定如何将消息发给消费者。Pulsar 支持四种订阅模式：

- [独占模式](/platform/latest/reference/concepts/messaging#exclusive)
- [共享模式](/platform/latest/reference/concepts/messaging#shared)
- [灾备模式](/platform/latest/reference/concepts/messaging#failover)
- [key_shared](/platform/latest/reference/concepts/messaging#key_shared)

有关订阅模式的详细信息，参见[订阅](/platform/latest/reference/concepts/messaging#subscriptions)。

## Schema

Pulsar 支持内置的 schema registry。 Schema registry 使 Pulsar 客户端可以根据主题上传数据 schema。Pulsar Schema 用于确定对主题有效的数据类型。通过 Pulsar schema，用户可以在构造和处理简单（例如 `string`）或复杂的消息时使用特定语言的数据类型。

有关 Pulsar Schema 的详细信息，参见 [schema 基本概念](/platform/latest/pulsar-schema/understand-schema)。