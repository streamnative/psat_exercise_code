---
title: StreamNative Cloud API
id: cloud-api
category: concepts
---

通过 StreamNative Cloud API，用户可以查询和操作 StreamNative Cloud 对象的状态。用户通过 API 服务器于外部组件进行通信。

# OpenAPI 规范

有关 OpenAPI 的详细信息，参见 [OpenAPI](https://www.openapis.org/)。

StreamNative Cloud API 服务基于 `/openapi/v0` 提供 OpenAPI 规范。用户可以使用以下格式的请求头获取响应。

<table>
  <thead>
     <tr>
        <th>头</th>
        <th style="min-width: 50%;">取值</th>
        <th>备注</th>
     </tr>
  </thead>
  <tbody>
     <tr>
        <td><code>Accept-Encoding</code></td>
        <td><code>gzip</code></td>
        <td>可选项</td>
     </tr>
     <tr>
        <td rowspan="3"><code>Accept</code></td>
        <td><code>application/com.github.proto-openapi.spec.v0@v1.0+protobuf</code></td>
        <td>主要用于集群内部使用</td>
     </tr>
     <tr>
        <td><code>application/json</code></td>
        <td>缺省值</td>
     </tr>
     <tr>
        <td><code>*</code></td>
        <td>提供 <code>`application/json`</code></td>
     </tr>
  </tbody>
</table>

# API 版本

为了更好的表示组织内的资源以及更方便地升级维护这些资源，StreamNative Cloud 支持多个 API 版本。每个 API 版本有不同的路由。版本控制在 API 级别（而非资源或字段级别），以确保 API 呈现出清晰、一致的系统资源和行为视图，以及允许控制对整个生命周期的 API 和/或实验性 API 的控制访问。

API 版本不同，其稳定性和功能也不尽相同。

- Alpha 版本
  - 版本名称包含 `alpha` 字段（例如，`v1alpha1`）。
  - 该版本可能有一些 bug。
  - 该版本支持的功能特性可能随时发生变化。如有变更，恕不另行通知。
  - 在后续版本中，API 可能和之前的版本不兼容。如有变更，恕不另行通知。
  - 由于存在较高的错误风险以及缺乏长期技术支持，建议仅在短期内部测试集群中使用该版本。
- Beta 版本
  - 版本名称包含 `beta` 字段（例如，`v2beta3`）。
  - 该软件已经过充分测试。用户可以安全地启用相关功能。
  - 尽管技术细节可能会发生变更，但会一直支持相关功能。
  - 在后续 Beta 或稳定版本中，Schema 或者语义可能会发生变更，并与之前的版本不兼容。在这种情况下，StreamNative Cloud 提供 API 版本迁移说明。迁移时，可能需要删除、编辑和重新创建 API 对象。对于依赖该 API 对象的应用程序，可能暂时无法使用。
  - 由于后续版本可能存在版本不兼容的情况，建议仅在非关键业务中使用该版本。**请试用我们的 Beta 版本，并反馈您的宝贵意见。**
- 稳定版本
  - 版本名称为 `vX`，其中 `X` 为整数。
  - 后续发行版本支持稳定版本中实现的功能特性。

# API 组

用户可以在 REST URL 中和序列化对象的 `apiVersion` 字段中指定 API 组。目前，StreamNative Cloud 支持两种 API 组。

- **Core** API 组，也被称为 **Legacy** 组，该组的 REST URL 为 `/api/v1`。在资源对象的定义中，表示为 `apiVersion: v1`。

- **Named** API 组，该组的 REST URL 为 `/apis/$GROUP_NAME/$VERSIONURL` 。在资源对象的定义中，表示为 `apiVersion: $GROUP_NAME/$VERSION`（例如，`apiVersion: batch/v1`）。