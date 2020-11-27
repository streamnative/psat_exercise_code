---
title: StreamNative Cloud 对象
id: cloud-object
category: concepts
---

本文介绍 StreamNative Cloud 对象在 StreamNative Cloud API 中的表现形式，以及如何使用 `.yaml` 文件使用这些 StreamNative Cloud 对象。

# 简介

在 StreamNative Cloud 系统中，StreamNative Cloud 对象是持久化的实体。StreamNative Cloud 使用这些实体表示整个集群的状态，包括以下信息：

- 正在运行的容器化应用程序（以及在哪些节点上运行）
- 应用程序使用的资源
- 应用程序的运行策略

一旦创建对象，StreamNative Cloud 将一直确保对象存在。有关创建、修改、删除 StreamNative Cloud 对象的详细信息，参见 [StreamNative Cloud API](/concepts/cloud-api/cloud-api.md)。当使用 `snctl` CLI 工具时，`snctl` CLI 工具会按需调用 StreamNative Cloud API。

## 对象名称和 UID

集群中的每个对象都有自己的[名称](#对象名称)和 [UID](#对象-id)。在同一组织中，同一类型的对象，其名称和 ID 必须唯一。例如，在同一组织内，只能有一个名为 `myapp-1234` 的 pod。

### 对象名称

StreamNative Cloud 支持以下几种常见的资源命名限制：

- DNS 子域名：很多资源的名称必须满足 [RFC 1123](https://tools.ietf.org/html/rfc1123) 定义的 DNS 子域名的命名规则。
  - 最多支持 253 个字符。
  - 只支持字母、数字，以及特殊字符（“-”和“.”）。
  - 必须以字母或数字开头。
  - 必须以字母或数字结尾。
- DNS 标签名：有些资源的名称必须满足 [RFC 1123](https://tools.ietf.org/html/rfc1123) 定义的 DNS 标签名的命名规则。
  - 最多支持 63 个字符。
  - 只支持字母、数字，以及特殊字符（“-”）。
  - 必须以字母或数字开头。
  - 必须以字母或数字结尾。
- 路径分段名称（Path Segment Name）：有些资源的名称可以用作路径中的片段，但不会带来数据安全隐患。这种名称不可以是“.”、“..”，也不可以包括“/”或“%”等特殊字符。

### 对象 ID

StreamNative Cloud 对象 ID 指 UUID（Universally Unique Identifier，全局唯一标识符）。有关 UUID 的标准，参见 [ISO/IEC 9834-8](https://www.iso.org/standard/62795.html) 和 [ITU-T X.667](https://www.itu.int/rec/T-REC-X.667-201210-I/en)。

## 对象规约和对象状态

几乎所有的 StreamNative Cloud 对象都支持 `spec`（规约）和 `status`（状态）字段，它们负责管理对象的配置。`spec` 字段描述对象的预期状态。`status` 字段描述对象的当前状态。配置或更新 StreamNative Cloud 组件后，`status` 字段的配置也随之更新。StreamNative Cloud 一直积极、主动地管理对象的实际状态，使之与期望状态相匹配。

# 描述 StreamNative Cloud 对象

当通过 StreamNative Cloud API 创建对象时，用户必须配置 `spec` 字段。`spec` 字段用于描述对象的预期状态，以及有关该对象的一些基本信息（例如对象名称）。创建对象时，API 请求体中必须包含该对象的信息（采用 JSON 格式）。**通常，用户会在 `.yaml` 文件 中将对象的信息提供给 `snctl` CLI 工具**。在发出 API 请求时，`snctl` CLI 工具会将信息转换为 JSON 格式。

以下是一个 `.yaml` 文件示例。该示例列出 StreamNative Cloud 创建集群需要配置的必选字段。

```yaml
apiVersion: cloud.streamnative.io/v1alpha1
kind: PulsarCluster
metadata:
  namespace: matrix
  name: neo-1
spec:
  instanceName: neo
  location: cn-zjk
  broker:
    replicas: 1
  bookkeeper:
    replicas: 3
```

在 `.yaml` 文件中配置集群参数之后，用户可以在 `snctl` CLI 命令行中将 `.yaml` 文件作为 `snctl apply` 命令的参数，从而创建集群，如下所示。

```shell
snctl apply -f /path/to/clusterneo1.yaml
```

集群创建成功，如下所示。

```sh
cluster.cloud.streamnative.io/neo created
```

## 必选字段

StreamNative Cloud 支持使用 `.yaml` 文件创建对象。在创建对象时，用户需要配置 `.yaml` 中的必选字段。

- `apiVersion` ：指定 StreamNative Cloud API 的版本，以便创建对象。
- `kind`：指定要创建的对象。
- `metadata`：指定元数据（包括 `name` 字符串和 `namespace` 字符串），以便识别对象。
- `spec`：指定对象的状态。

StreamNative Cloud 对象的 `spec` 字段并非完全一样，有些对象的 `spec` 字段还包含一些嵌套字段。

# 管理 StreamNative Cloud 对象

`snctl` CLI 工具支持使用指令式命令创建和管理 StreamNative Cloud 对象。指令式命令简单、易学、且容易记住。指令式命令直接运行在组织的活动对象上。用户可以在 `snctl` 命令行指定相关参数或字段，实现相关操作。这是在组织内部执行一次性任务的最简单的方法。由于指令式命令直接运行在活动对象上，所以它无法提供之前的配置。

以下示例说明如何使用指令式命令创建集群对象 `neo`。

```sh
snctl create pulsarinstances neo
```
