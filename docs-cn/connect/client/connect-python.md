---
title: 访问集群（Python 客户端）
id: connect-python
category: connect
---

本文介绍如何通过 Pulsar Python 客户端访问 Pulsar 集群，以及如何创建 Pulsar consumer 和 producer 并收发消息。Pulsar Python 客户端支持使用 OAuth2 或 Token 认证方式访问 Pulsar 集群。

# 访问 Pulsar 集群（OAuth2）

如需使用 Pulsar Python 客户端，通过 OAuth2 认证方式访问 Pulsar 集群，遵循以下步骤。

1. 获取 Service URL。有关如何获取 Service URL 的详细信息，参见[获取 Service URL](/connect/overview.md#获取-service-url)。

2. 获取 OAuth2 认证参数。有关如何获取 OAuth2 认证参数的详细信息，参见[获取 OAuth2 认证参数](/connect/overview.md#获取-oauth2-认证参数)。

    ```shell
    export AUTH_PARAMS='{
                            "issuer_url": "https://auth.streamnative.cloud.cn",
                            "private_key": "/path/to/private.key",
                            "audience": "audience"
                        }'
    ```

3. 使用 OAuth2 认证方式，访问指定的 Pulsar 集群。

    ```shell
    import os
    from pulsar import Client, AuthenticationOauth2
    client = Client(os.environ.get('SERVICE_URL'), authentication=AuthenticationOauth2(os.environ.get('AUTH_PARAMS')))

    client.close()
    ```

    将 `SERVICE_URL` 参数配置为已获取的 Service URL。  
    根据获取的 OAuth2 认证参数，配置 `issuer_url`、`private_key`、`audience` 参数的取值。

4. 创建 Python consumer，并使用该 consumer 签收消息。

    ```python
    import os
    from pulsar import Client, AuthenticationOauth2

    client = Client(os.environ.get('SERVICE_URL'), authentication=AuthenticationOauth2(os.environ.get('AUTH_PARAMS')))

    consumer = client.subscribe('my-topic', 'my-subscription')

    while True:
        msg = consumer.receive()
        try:
            print("Received message '{}' id='{}'".format(msg.data(), msg.message_id()))
            # Acknowledge successful processing of the message
            consumer.acknowledge(msg)
        except:
            # Message failed to be processed
            consumer.negative_acknowledge(msg)
    ```

5. 创建 Python producer，并使用该 producer 发送消息。

    ```python
    import os
    from pulsar import Client, AuthenticationOauth2

    client = Client(os.environ.get('SERVICE_URL'), authentication=AuthenticationOauth2(os.environ.get('AUTH_PARAMS')))

    producer = client.create_producer('my-topic')

    for i in range(10):
        producer.send(('Hello-%d' % i).encode('utf-8'))
        print('send msg "hello-%d"' % i)

    client.close()
    ```

# 访问 Pulsar 集群（Token）

如需使用 Pulsar Python 客户端，通过 Token 认证方式访问 Pulsar 集群，遵循以下步骤。

1. 获取 Service URL。有关如何获取 Service URL 的详细信息，参见[获取 Service URL](/connect/overview.md#获取-service-url)。

2. 获取 Token 认证参数。有关如何获取 Token 认证参数的详细信息，参见[获取 Token 认证参数](/connect/overview.md#获取-token-认证参数)。

3. 使用 Token 认证方式，访问指定的 Pulsar 集群。

    ```shell
    import os
    from pulsar import Client, AuthenticationToken
    client = Client(os.environ.get('SERVICE_URL'), authentication=AuthenticationToken(os.environ.get('AUTH_PARAMS')))

    client.close()
    ```

    将 `SERVICE_URL` 参数配置为已获取的 Service URL。  
    将 `AUTH_PARAMS` 参数配置为已获取的 Token。

4. 创建 Python consumer，并使用该 consumer 签收消息。

    ```python
    import os
    from pulsar import Client, AuthenticationToken

    client = Client(os.environ.get('SERVICE_URL'), authentication=AuthenticationToken(os.environ.get('AUTH_PARAMS')))

    consumer = client.subscribe('my-topic', 'my-subscription')

    while True:
        msg = consumer.receive()
        try:
            print("Received message '{}' id='{}'".format(msg.data(), msg.message_id()))
            # Acknowledge successful processing of the message
            consumer.acknowledge(msg)
        except:
            # Message failed to be processed
            consumer.negative_acknowledge(msg)
    ```

5. 创建 Python producer，并使用该 producer 发送消息。

    ```python
    import os
    from pulsar import Client, AuthenticationToken

    client = Client(os.environ.get('SERVICE_URL'), authentication=AuthenticationToken(os.environ.get('AUTH_PARAMS')))

    producer = client.create_producer('my-topic')

    for i in range(10):
        producer.send(('Hello-%d' % i).encode('utf-8'))
        print('send msg "hello-%d"' % i)

    client.close()
    ```

有关如何使用 Pulsar Python 客户端访问 Pulsar 集群的完整举例，参见[Python 客户端举例](https://github.com/streamnative/pulsar-examples/tree/master/cloud/python)。
