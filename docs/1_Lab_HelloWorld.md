# Pulsar Hello World

## Objective

This lab uses Pulsar to ingest and consume data. We will:    

* Create a producer to import data
* Create a consumer to read the data

**Project Directory:** `helloworld`

## Before run this

You should have StreamNative Cloud created, and also have "service account"(服务账号) created.

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

## Pulsar HelloWorld

Create a `Producer` with the following characteristics:

* Reads the lines of `playing_cards_datetime_short.tsv` dataset;
* Connects to the StreamNative Cloud;
* Sends messages on the `helloworld_topic` topic
* Sends all messages as `String`s

Create a `Consumer` with the following characteristics:

* Consumes messages sent on the `helloworld_topic` topic
* Connects to the StreamNative Cloud;
* Consumes all data as `String`s
* Outputs the contents of the messages to the screen

**When running, start your consumer first and then start the producer.**

## Optional Steps

* Use the command line consumer to view your output:

    ```bash
    $ pulsar-client consume helloworld_topic -s "clsub"
    ```
* Explore the difference in the `Producer`'s performance when using a synchronous send versus an asynchronous send.
