# Multi-threaded Consumer

## Objective

This lab uses multi-threading with a Pulsar consumer. We will:    

* Write a multi-threaded consumer

**Project Directory:** `multithreaded`


## Before run this

You should have StreamNative Cloud created, and also have "service account"(服务账号) created.
And better to have topic `helloworld_topic` created with data.

## Open Project "helloworld"

  1. Open the IntelliJ IDEA on the desktop.

  2. open the "pom.xml" in project directory as project.

## code changes that needed:

The serviceUrl and credentials can be found in StreamNative Cloud Manager.  It can be found by the "client" at the left side of the Cloud Manager website.
Here is a [picture for this part](./client_config.png)

The create client part of code may need change based on where this github repo cloned, and the StreamNative Cloud account information.

```java
...
    // path of the dataset, based on where this github repo cloned.
    String filename = "/Users/jia/ws/play/psat_exercise_code/datasets/playing_cards_datetime_short.tsv";

    String issuerUrl = "https://auth.cloud.streamnative.cn";
    String credentialsUrl = "file:///Users/jia/Downloads/jia-org-admin.json";
    String audience = "urn:sn:pulsar:jia-org:hello-instance1";

    System.out.println("Sending " + filename + " to Pulsar on " + topicName);

    PulsarClient client = PulsarClient.builder()
        .serviceUrl("pulsar+ssl://hello-cluster1.jia-org.cn-zjk.streamnative.ali.snpulsar.cn:6651")
        .authentication(
            AuthenticationFactoryOAuth2.clientCredentials(new URL(issuerUrl), new URL(credentialsUrl), audience))
        .build();
...
```

## Multi-threaded Consumer

Create a consumer with the following characteristics:

* Consumes messages sent on the `helloworld_topic` topic
* Connects to the StreamNative Cloud;
* Consumes all data as `String`s
* Outputs the contents of the messages to the screen
* Uses multi-threading to move consumption off the Java main thread. Be 
  sure to use correct multi-threading classes like `Executors` and
  `AtomicBoolean`.
