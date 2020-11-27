---
title: 访问集群（Node.js 客户端）
id: connect-nodejs
category: connect
---

本文介绍如何通过 Pulsar Node.js 客户端访问 Pulsar 集群，以及如何创建 Pulsar consumer 和 producer 并收发消息。Pulsar Node.js 客户端只支持使用 Token 认证方式访问 Pulsar 集群。

如需使用 Pulsar Node.js 客户端，通过 Token 认证方式访问 Pulsar 集群，遵循以下步骤。

1. 获取 Service URL。有关如何获取 Service URL 的详细信息，参见[获取 Service URL](/connect/overview.md#获取-service-url)。

2. 获取 Token 认证参数。有关如何获取 Token 认证参数的详细信息，参见[获取 Token 认证参数](/connect/overview.md#获取-token-认证参数)。

3. 使用 Token 认证方式，访问指定的 Pulsar 集群。

    ```shell
    const Pulsar = require('pulsar-client');

    const auth_params = process.env.AUTH_PARAMS;
    const service_url = process.env.SERVICE_URL;

    (async () => {
        const auth = new Pulsar.AuthenticationToken({
            token: auth_params,
        });

        // Create a client
        const client = new Pulsar.Client({
            serviceUrl: service_url,
            authentication: auth,
            operationTimeoutSeconds: 30,
        });

        await client.close();
    })();
    ```

    将 `SERVICE_URL` 参数配置为已获取的 Service URL。  
    将 `AUTH_PARAMS` 参数配置为已获取的 Token。

4. 创建 Node.js consumer，并使用该 consumer 签收消息。

    ```
    const Pulsar = require('pulsar-client');

    const auth_params = process.env.AUTH_PARAMS;
    const service_url = process.env.SERVICE_URL;

    (async () => {
        const auth = new Pulsar.AuthenticationToken({
            token: auth_params,
        });

        // Create a client
        const client = new Pulsar.Client({
            serviceUrl: service_url,
            authentication: auth,
            operationTimeoutSeconds: 30,
        });

        // Create a consumer
        const consumer = await client.subscribe({
            topic: 'persistent://public/default/my-topic',
            subscription: 'sub1',
            subscriptionType: 'Shared',
            ackTimeoutMs: 10000,
        });

        // Receive messages
        for (let i = 0; i < 10; i += 1) {
            const msg = await consumer.receive();
            console.log(msg.getData().toString());
            consumer.acknowledge(msg);
        }

        await consumer.close();
        await client.close();
    })();
    ```

5. 创建 Node.js producer，并使用该 producer 发送消息。

    ```
    const Pulsar = require('pulsar-client');

    const auth_params = process.env.AUTH_PARAMS;
    const service_url = process.env.SERVICE_URL;

    (async () => {
        const auth = new Pulsar.AuthenticationToken({
            token: auth_params,
        });

        // Create a client
        const client = new Pulsar.Client({
            serviceUrl: service_url,
            authentication: auth,
            operationTimeoutSeconds: 30,
        });

        // Create a producer
        const producer = await client.createProducer({
            topic: 'persistent://public/default/my-topic',
            sendTimeoutMs: 30000,
            batchingEnabled: true,
        });

        // Send messages
        for (let i = 0; i < 10; i += 1) {
            const msg = `my-message-${i}`;
            producer.send({
                data: Buffer.from(msg),
            });
            console.log(`Sent message: ${msg}`);
        }
        await producer.flush();

        await producer.close();
        await client.close();
    })();
    ```

有关如何使用 Pulsar Node.js 客户端访问 Pulsar 集群的完整举例，参见[Node.js 客户端举例](https://github.com/streamnative/pulsar-examples/tree/master/cloud/node)。
