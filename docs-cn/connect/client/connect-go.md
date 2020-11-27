---
title: 访问集群（Go 客户端）
id: connect-go
category: connect
---

本文介绍如何通过 Pulsar Go 客户端访问 Pulsar 集群，以及如何创建 Pulsar consumer 和 producer 并收发消息。Pulsar Go 客户端支持使用 OAuth2 或 Token 认证方式访问 Pulsar 集群。

# 前提条件

- 安装 Go 1.11 及以上版本。有关详细信息，参见[安装指南](http://golang.org/doc/install)。
- 安装 Go 客户端 0.1.1 以上版本。

# 访问 Pulsar 集群（OAuth2）

如需使用 Pulsar Go 客户端，通过 OAuth2 认证方式访问 Pulsar 集群，遵循以下步骤。

1. 获取 Service URL。有关如何获取 Service URL 的详细信息，参见[获取 Service URL](/connect/overview.md#获取-service-url)。

2. 获取 OAuth2 认证参数。有关如何获取 OAuth2 认证参数的详细信息，参见[获取 OAuth2 认证参数](/connect/overview.md#获取-oauth2-认证参数)。

3. 使用 OAuth2 认证方式，访问指定的 Pulsar 集群。

    ```go
    func main() {
        oauth := pulsar.NewAuthenticationOAuth2(map[string]string{
            "type":       "client_credentials",
            "issuerUrl":  "",
            "audience":   "",
            "privateKey": "",
            "clientId":   "",
        })

        client, err := pulsar.NewClient(pulsar.ClientOptions{
            URL:            "pulsar+ssl://streamnative.cloud:6651/",
            Authentication: oauth,
        })
        if err != nil {
            log.Fatal(err)
        }
        defer client.Close()
    }
    ```

    将 `URL` 参数配置为已获取的 Service URL。  
    根据获取的 OAuth2 认证参数，配置 `issuerUrl`、`privateKey`、`audience` 参数的取值。

4. 创建 Go consumer，并使用该 consumer 签收消息。

    ```go
    func main() {
        client := ccloud.CreateClient()

        consumer, err := client.Subscribe(pulsar.ConsumerOptions{
            Topic:            "topic-1",
            SubscriptionName: "my-sub",
            Type:             pulsar.Shared,
        })
        if err != nil {
            log.Fatal(err)
        }
        defer consumer.Close()

        for i := 0; i < 10; i++ {
            msg, err := consumer.Receive(context.Background())
            if err != nil {
                log.Fatal(err)
            }

            fmt.Printf("Received message msgId: %v -- content: '%s'\n",
                msg.ID(), string(msg.Payload()))

            consumer.Ack(msg)
        }

        if err := consumer.Unsubscribe(); err != nil {
            log.Fatal(err)
        }
    }
    ```

5. 创建 Go producer，并使用该 producer 发送消息。

    ```go
    func main() {
        client := ccloud.CreateClient()

        producer, err := client.CreateProducer(pulsar.ProducerOptions{
            Topic: "topic-1",
        })
        if err != nil {
            log.Fatal(err)
        }
        defer producer.Close()

        for i := 0; i < 10; i++ {
            if msgId, err := producer.Send(context.Background(), &pulsar.ProducerMessage{
                Payload: []byte(fmt.Sprintf("hello-%d", i)),
            }); err != nil {
                log.Fatal(err)
            } else {
                fmt.Printf("Published message: %v \n", msgId)
            }
        }
    }
    ```

# 访问 Pulsar 集群（Token）

如需使用 Pulsar Go 客户端，通过 Token 认证方式访问 Pulsar 集群，遵循以下步骤。

1. 获取 Service URL。有关如何获取 Service URL 的详细信息，参见[获取 Service URL](/connect/overview.md#获取-service-url)。

2. 获取 Token 认证参数。有关如何获取 Token 认证参数的详细信息，参见[获取 Token 认证参数](/connect/overview.md#获取-token-认证参数)。

3. 使用 Token 认证方式，访问指定的 Pulsar 集群。

    ```shell
    func main() {
        client, err := pulsar.NewClient(pulsar.ClientOptions{
            URL:            SERVICE_URL,
            Authentication: pulsar.NewAuthenticationToken(AUTH_PARAMS),
        })
        if err != nil {
            log.Fatal(err)
        }
        defer client.Close()
    }
    ```

    将 `URL` 参数配置为已获取的 Service URL。  
    将 `AUTH_PARAMS` 参数配置为已获取的 Token。

有关如何使用 Pulsar Go 客户端访问 Pulsar 集群的完整举例，参见[Go 客户端举例](https://github.com/streamnative/pulsar-examples/tree/master/cloud/go)。