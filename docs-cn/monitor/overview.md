---
title: 简介
id: overview
category: monitor
---

StreamNative Cloud 内置多种预防故障的措施。在生产环境中，积极监控运行集群的整体性能也至关重要。StreamNative Cloud 通过 `/pulsar/metrics` 终端公开许多 Pulsar 指标。

# Pulsar 指标

本节介绍 StreamNative Cloud 面向用户公开的 Pulsar 指标。

## 命名空间指标

下表列出面向用户公开的命名空间指标。

| 指标  | 类型 | 描述 |
|---|---|---|
| pulsar_topics_count | Gauge | 命名空间内 Pulsar 主题的数量。|
| pulsar_subscriptions_count | Gauge | 命名空间内 Pulsar 订阅的数量。 |
| pulsar_producers_count | Gauge | 命名空间内连接到指定 Pulsar broker 的活跃 producer 的数量。 |
| pulsar_consumers_count | Gauge | 命名空间内连接到指定 Pulsar broker 的活跃 consumer 的数量。|
| pulsar_rate_in | Gauge | 命名空间内 producer 发布消息到 Pulsar broker 的速率（消息/秒）。  |
| pulsar_rate_out | Gauge | 命名空间内 consumer 从 Pulsar broker 中消费消息的速率（消息/秒）。|
| pulsar_throughput_in | Gauge | 命名空间内 producer 发布消息到 Pulsar broker 的吞吐量（字节/秒）。 |
| pulsar_throughput_out | Gauge | 命名空间内 consumer 从 Pulsar broker 中消费消息的吞吐量（字节/秒）。 |
| pulsar_storage_size | Gauge | 命名空间内主题的总存储大小（总字节数）。  |
| pulsar_storage_backlog_size | Gauge | 命名空间内主题的总堆积量（消息数量）。|
| pulsar_storage_offloaded_size | Gauge | 命名空间卸载到分层存储中的总数据量（总字节数）。 |
| pulsar_storage_write_rate | Gauge | 命名空间写入分层存储中的批消息（entry）的速率（批消息/秒）。 |
| pulsar_storage_read_rate | Gauge | 命名空间从分层存储中读取批消息（entry）的速率（批消息/秒）。|
| pulsar_subscription_delayed | Gauge | 命名空间内推迟发布的批消息（entry）的总数。 |
| pulsar_storage_write_latency_le_* | Histogram | 命名空间内存储的写延迟小于指定阈值的 entry 的速率。<br>支持的取值： <br><ul><li>pulsar_storage_write_latency_le_0_5：<= 0.5ms </li><li>pulsar_storage_write_latency_le_1：<= 1ms</li><li>pulsar_storage_write_latency_le_5：<= 5ms</li><li>pulsar_storage_write_latency_le_10：<= 10ms</li><li>pulsar_storage_write_latency_le_20：<= 20ms</li><li>pulsar_storage_write_latency_le_50：<= 50ms</li><li>pulsar_storage_write_latency_le_100：<= 100ms</li><li>pulsar_storage_write_latency_le_200：<= 200ms</li><li>pulsar_storage_write_latency_le_1000：<= 1s</li><li>pulsar_storage_write_latency_le_overflow：> 1s</li></ul> |
| pulsar_entry_size_le_* | Histogram | 命名空间内 entry 大小小于指定阈值的 entry 的速率。<br> 支持的取值：<br><ul><li>pulsar_entry_size_le_128：<= 128 字节 </li><li>pulsar_entry_size_le_512：<= 512 字节</li><li>pulsar_entry_size_le_1_kb：<= 1 KB</li><li>pulsar_entry_size_le_2_kb：<= 2 KB</li><li>pulsar_entry_size_le_4_kb：<= 4 KB</li><li>pulsar_entry_size_le_16_kb：<= 16 KB</li><li>pulsar_entry_size_le_100_kb：<= 100 KB</li><li>pulsar_entry_size_le_1_mb：<= 1 MB</li><li>pulsar_entry_size_le_overflow：> 1 MB</li></ul> |

## 主题指标

下表列出面向用户公开的主题指标。

| 指标 | 类型 | 描述 |
|---|---|---|
| pulsar_subscriptions_count | Gauge | 连接到指定 Pulsar broker 的主题的订阅数。 |
| pulsar_producers_count | Gauge | 连接到指定 Pulsar broker 的主题的活跃 producer 的数量。 |
| pulsar_consumers_count | Gauge | 连接到指定 Pulsar broker 的主题的活跃 consumer 的数量。 |
| pulsar_rate_in | Gauge | 主题的 producer 发布消息到 Pulsar broker 的速率（消息/秒）。 |
| pulsar_rate_out | Gauge | 主题的 consumer 从 Pulsar broker 中消费消息的速率（消息/秒）。 |
| pulsar_throughput_in | Gauge |  主题的 producer 发布消息到 Pulsar broker 的吞吐量（字节/秒）。|
| pulsar_throughput_out | Gauge | 主题的 consumer 从 Pulsar broker 中消费消息的吞吐量（字节/秒）。|
| pulsar_storage_size | Gauge | 主题的总存储大小（总字节数）。 |
| pulsar_storage_backlog_size | Gauge | 主题的总堆积量（消息数量）。 |
| pulsar_storage_offloaded_size | Gauge | 主题卸载到分层存储中的总数据量（总字节数）。|
| pulsar_storage_backlog_quota_limit | Gauge | 主题内限制 backlog quota 的数据总量（总字节数）。 |
| pulsar_storage_write_rate | Gauge | 主题写入分层存储中的批消息（entry）的速率（批消息/秒）。 |
| pulsar_storage_read_rate | Gauge | 主题从分层存储中读取批消息（entry）的速率（批消息/秒）。 |
| pulsar_subscription_delayed | Gauge | 主题内推迟发布的批消息（entry）的总数。|
| pulsar_storage_write_latency_le_* | Histogram | 主题内存储的写延迟小于指定阈值的 entry 的速率。<br> 支持的取值：<br><ul><li>pulsar_storage_write_latency_le_0_5：<= 0.5ms </li><li>pulsar_storage_write_latency_le_1：<= 1ms</li><li>pulsar_storage_write_latency_le_5：<= 5ms</li><li>pulsar_storage_write_latency_le_10：<= 10ms</li><li>pulsar_storage_write_latency_le_20：<= 20ms</li><li>pulsar_storage_write_latency_le_50：<= 50ms</li><li>pulsar_storage_write_latency_le_100：<= 100ms</li><li>pulsar_storage_write_latency_le_200：<= 200ms</li><li>pulsar_storage_write_latency_le_1000：<= 1s</li><li>pulsar_storage_write_latency_le_overflow：> 1s</li></ul> |
| pulsar_entry_size_le_* | Histogram | 主题内 entry 大小小于指定阈值的 entry 的速率。<br> 支持的取值：<br><ul><li>pulsar_entry_size_le_128：<= 128 字节 </li><li>pulsar_entry_size_le_512：<= 512 字节</li><li>pulsar_entry_size_le_1_kb：<= 1 KB</li><li>pulsar_entry_size_le_2_kb：<= 2 KB</li><li>pulsar_entry_size_le_4_kb：<= 4 KB</li><li>pulsar_entry_size_le_16_kb：<= 16 KB</li><li>pulsar_entry_size_le_100_kb：<= 100 KB</li><li>pulsar_entry_size_le_1_mb：<= 1 MB</li><li>pulsar_entry_size_le_overflow：> 1 MB</li></ul> |
| pulsar_in_bytes_total | Counter | 主题收到的总字节数。|
| pulsar_producers_count | Counter | 主题收到的总消息数。|

# 收集 Pulsar 指标

Pulsar 指标采用 [Prometheus](https://prometheus.io/) 格式。Prometheus 是一种用于存储、汇总、查询时间序列数据的开源工具。因此，用户可以使用 Prometheus 收集 Pulsar 指标。Prometheus 使用 Token 连接目标集群，然后收集有关 Pulsar 集群的指标。

如需使用 Prometheus 收集 Pulsar 指标，用户需要将以下配置加入 Prometheus 配置文件中。有关如何获取 Token 的详细信息，参见[获取 OAuth2 认证参数](/connect/overview.md#获取-oauth2-认证参数)。

```
scrape_configs:
- job_name: pulsar-broker
    bearer_token_file: /etc/token/sn_token # the file path where token is stored
    metrics_path: /pulsar/metrics
    scheme: https
    static_configs:
    - targets: ['<pulsar web service domain name>:443’]
```

如果 Token 过期，StreamNative Cloud 提供 `cloud-token-refresher` 工具，刷新 Token。用户可以运行以下命令，启动 `cloud-token-refresher`。有关如何获取运行 `cloud-token-refresher` 所需的参数取值，参见[获取 OAuth2 认证参数](/connect/overview.md#获取-oauth2-认证参数)。


```
sn-token-refresher \
-audience $YOUR_RESOURCE \
-client-id $CLIENT_ID \
-client-secret $CLIENT_SECRET \
-issuer-url $ISS_URL \
-token-file $VOLUME_PATH/$FILE_NAME \  # the VOLUME_PATH is `/etc/token` in this case.
-refresh-interval $TIME_IN_SECOND
```

> **说明**
>
> 确保 `-token-file` 的取值与 Prometheus 配置中 `bearer_token_file` 字段的取值保持一致。

以下示例说明如何运行 `cloud-token-refresher`，刷新 Token。

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: refresher
  labels:
    app: refresher
    env: test
spec:
  replicas: 1
  selector:
    matchLabels:
      app: refresher
  template:
    metadata:
      labels:
        app: refresher
    spec:
      containers:
        - name: cloud-token-refresher
          image: streamnative/cloud-token-refresher:0.1.0
          command:
            - "sh"
            - "-c"
            - "/sn-token-refresher -audience $YOUR_RESOURCE -client-id $YOUR_ID -client-secret $YOUR_SECRET -issuer-url $ISS_URL -token-file /etc/token/sn_token"
          volumeMounts:
            - name: token-volume
              mountPath: /etc/token
      volumes:
        - name: token-volume
          emptyDir: {}
```

为了正确更新和读取 Token，用户需要为 `cloud-token-refresher` 和 Prometheus 设置一个通用 volume，如下所示。
```
containers:
- name: prometheus
...

    volumeMounts:
    - name: token-volume
        mountPath: /etc/token

- name: cloud-token-refresher
…

    volumeMounts:
    - name: token-volume
        mountPath: /etc/token

volumes:
- name: token-volume
    emptyDir: {}
```

以下示例说明如何运行 `cloud-token-refresher` 和 Prometheus，更新和读取 Token。

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: prometheus
  namespace: prom
  labels:
    app: prometheus-server
    env: test
spec:
  replicas: 1
  selector:
    matchLabels:
      app: prometheus-server
      env: test
  template:
    metadata:
      labels:
        app: prometheus-server
        env: test
    spec:
      containers:
        - name: prometheus
          image: prom/prometheus
          args:
            - "--config.file=/etc/prometheus/prometheus.yml"
            - "--storage.tsdb.path=/prometheus/"
          ports:
            - containerPort: 9090
          volumeMounts:
            - name: config-volume
              mountPath: /etc/prometheus
            - name: storage-volume
              mountPath: /prometheus/
            - name: token-volume
              mountPath: /etc/token
        - name: cloud-token-refresher
          image: streamnative/cloud-token-refresher:0.1.0
          command:
            - "sh"
            - "-c"
            - "/sn-token-refresher -audience $YOUR_RESOURCE -client-id $YOUR_ID -client-secret $YOUR_SECRET -issuer-url $ISS_URL -token-file /etc/token/sn_token"
          volumeMounts:
            - name: token-volume
              mountPath: /etc/token
      volumes:
        - name: config-volume
          configMap:
            defaultMode: 420
            name: prometheus-conf
        - name: storage-volume
          emptyDir: {}
        - name: token-volume
          emptyDir: {}
```
