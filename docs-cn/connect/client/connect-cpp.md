---
title: 访问集群（C++ 客户端）
id: connect-cpp
category: connect
---

本文介绍如何通过 Pulsar C++ 客户端访问 Pulsar 集群，以及如何创建 Pulsar consumer 和 producer 并收发消息。Pulsar C++ 客户端支持使用 OAuth2 或 Token 认证方式访问 Pulsar 集群。

# 访问 Pulsar 集群（OAuth2）

如需使用 Pulsar C++ 客户端，通过 OAuth2 认证方式访问 Pulsar 集群，遵循以下步骤。

1. 获取 Service URL。有关如何获取 Service URL 的详细信息，参见[获取 Service URL](/connect/overview.md#获取-service-url)。

2. 获取 OAuth2 认证参数。有关如何获取 OAuth2 认证参数的详细信息，参见[获取 OAuth2 认证参数](/connect/overview.md#获取-oauth2-认证参数)。

3. 使用 OAuth2 认证方式，访问指定的 Pulsar 集群。

    ```cpp
    int main() {
        ClientConfiguration config;
        std::string oauthParams = R"({
        "issuer_url": "https://cloud/oauth/token",
        "private_key": "/resources/authentication/token/cpp_credentials_file.json",
        "audience": "https://cloud.auth0.com/api/v2/"})";

        config.setAuth(pulsar::AuthOauth2::create(params));

        Client client("pulsar+ssl://streamnative.cloud:6651", config);
        client.close();
    }
    ```

    将 `SERVICE_URL` 参数配置为已获取的 Service URL。  
    根据获取的 OAuth2 认证参数，配置 `issuer_url`、`private_key`、`audience` 参数的取值。

4. 创建 C++ consumer，并使用该 consumer 签收消息。

    ```cpp
    int main(int argc, char *argv[]) {
        ClientConfiguration config;
        std::string oauthParams = argv[2];

        config.setAuth(pulsar::AuthOauth2::create(oauthParams));

        Client client(argv[1], config);

        Consumer consumer;
        Result result = client.subscribe("persistent://public/default/my-topic", "consumer-1", consumer);
        if (result != ResultOk) {
            std::cout << "Failed to subscribe: " << result << "\n";
            return -1;
        }

        Message msg;
        while (true) {
            consumer.receive(msg);
            std::cout << "Received: " << msg << "  with payload '" << msg.getDataAsString() << "'" << "\n";

            consumer.acknowledge(msg);
        }
    }
    ```

5. 创建 C++ producer，并使用该 producer 发送消息。

    ```cpp
    int main(int argc, char *argv[]) {
        ClientConfiguration config;
        std::string oauthParams = argv[2];

        config.setAuth(pulsar::AuthOauth2::create(oauthParams));

        Client client(argv[1], config);

        Producer producer;
        Result result = client.createProducer("persistent://public/default/my-topic", producer);
        if (result != ResultOk) {
            std::cout << "Error creating producer: " << result << "\n";
            return -1;
        }

        // Send synchronously
        Message msg = MessageBuilder().setContent("content").build();
        Result res = producer.send(msg);
        std::cout << "Message sent: " << res << "\n";

        client.close();
    }
    ```

# 访问 Pulsar 集群（Token）

如需使用 Pulsar C++ 客户端，通过 Token 认证方式访问 Pulsar 集群，遵循以下步骤。

1. 获取 Service URL。有关如何获取 Service URL 的详细信息，参见[获取 Service URL](/connect/overview.md#获取-service-url)。

2. 获取 Token 认证参数。有关如何获取 Token 认证参数的详细信息，参见[获取 Token 认证参数](/connect/overview.md#获取-token-认证参数)。

3. 使用 Token 认证方式，访问指定的 Pulsar 集群。

    ```shell
    int main() {
    ClientConfiguration config;
    config.setAuth(AuthToken::createWithToken("AUTH_PARAMS"));
    Client client(SERVICE_URL, config);

    client.close();
    }
    ```

    将 `SERVICE_URL` 参数配置为已获取的 Service URL。  
    将 `AUTH_PARAMS` 参数配置为已获取的 Token。

有关如何使用 Pulsar C++ 客户端访问 Pulsar 集群的完整举例，参见[C++ 客户端举例](https://github.com/streamnative/pulsar-examples/tree/master/cloud/cpp)。
