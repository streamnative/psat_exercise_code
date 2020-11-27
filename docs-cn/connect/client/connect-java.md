---
title: 访问集群（Java 客户端）
id: connect-java
category: connect
---

本文介绍如何通过 Pulsar Java 客户端访问 Pulsar 集群，以及如何创建 Pulsar consumer 和 producer 并收发消息。Pulsar Java 客户端支持使用 OAuth2 或 Token 认证方式访问 Pulsar 集群。

# 前提条件

- 安装 Java 1.8 或以上版本。
- 安装 Pulsar 客户端 2.6.1 或以上版本。

# 访问 Pulsar 集群（OAuth2）

如需使用 Pulsar Java 客户端，通过 OAuth2 认证方式访问 Pulsar 集群，遵循以下步骤。

1. 获取 Service URL。有关如何获取 Service URL 的详细信息，参见[获取 Service URL](/connect/overview.md#获取-service-url)。

2. 获取 OAuth2 认证参数。有关如何获取 OAuth2 认证参数的详细信息，参见[获取 OAuth2 认证参数](/connect/overview.md#获取-oauth2-认证参数)。

3. 使用 OAuth2 认证方式，访问指定的 Pulsar 集群。

    ```java
    PulsarClient client = PulsarClient.builder()
        .serviceUrl("pulsar+ssl://streamnative.cloud:6651")
        .authentication(
                AuthenticationFactoryOAuth2.clientCredentials(new URL(issuerUrl), new URL(credentialsUrl), audience))
        .build();
    ```

    将 `serviceUrl` 参数配置为已获取的 Service URL。  
    根据获取的 OAuth2 认证参数，配置 `issuerUrl`、`credentialsUrl`、`audience` 参数的取值。

4. 创建 Java consumer，并使用该 consumer 签收消息。

    ```java
    public class SampleConsumer {
        public static void main(String[] args) throws Exception {
            JCommanderPulsar jct = new JCommanderPulsar();
            JCommander jCommander = new JCommander(jct, args);
            if (jct.help) {
                jCommander.usage();
                return;
            }

            String topic = "persistent://public/default/topic-1";

            PulsarClient client = PulsarClient.builder()
                    .serviceUrl(jct.serviceUrl)
                    .authentication(
                            AuthenticationFactoryOAuth2.clientCredentials(new URL(jct.issuerUrl), new URL(jct.credentialsUrl), jct.audience))
                    .build();

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

            consumer.close();
            client.close();
        }
    }
    ```

5. 创建 Java producer，并使用该 producer 发送消息。

    ```java
    public class SampleProducer {
        public static void main(String[] args) throws Exception {
            JCommanderPulsar jct = new JCommanderPulsar();
            JCommander jCommander = new JCommander(jct, args);
            if (jct.help) {
                jCommander.usage();
                return;
            }

            String topic = "persistent://public/default/topic-1";

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

            producer.close();
            client.close();
        }
    }
    ```

# 访问 Pulsar 集群（Token）

如需使用 Pulsar Java 客户端，通过 Token 认证方式访问 Pulsar 集群，遵循以下步骤。

1. 获取 Service URL。有关如何获取 Service URL 的详细信息，参见[获取 Service URL](/connect/overview.md#获取-service-url)。

2. 获取 Token 认证参数。有关如何获取 Token 认证参数的详细信息，参见[获取 Token 认证参数](/connect/overview.md#获取-token-认证参数)。

3. 使用 Token 认证方式，访问指定的 Pulsar 集群。

    ```shell
    PulsarClient client = PulsarClient.builder()
        .serviceUrl(SERVICE_URL)
        .authentication(AuthenticationFactory.token(AUTH_PARAMS))
        .build();
    ```

    将 `SERVICE_URL` 参数配置为已获取的 Service URL。  
    将 `AUTH_PARAMS` 参数配置为已获取的 Token。

有关如何使用 Pulsar Java 客户端访问 Pulsar 集群的完整举例，参见[Java 客户端举例](https://github.com/streamnative/pulsar-examples/tree/master/cloud/java)。