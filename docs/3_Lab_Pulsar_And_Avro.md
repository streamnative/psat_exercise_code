# Pulsar and Avro

## Objective

This lab uses Avro with Pulsar. We will:    

* Create Avro objects for the dataset
* Create a producer that produces Avro objects
* Create a consumer that consumes Avro objects

**Project Directory:** `avro`
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

## Cards Dataset

This exercise will use with a more complex playing card dataset. The file is on the local filesystem at:    
```
/home/vmuser/training/datasets/playing_cards_datetime.tsv
```

The data in the `playing_cards_datetime.tsv` file is made up of a timestamp, a GUID to identify a game, the type of game, the suit, and the card. Each piece of data is tab separated. The cards are no longer solely numeric and include Jacks, Queens and Kings. Here is a an example of the data:
```
2015-01-10 00:00:00 1ea7fc17-7cf0-486d-8b8b-ad905e0d7a7a  PaiGow  Club  Queen
2015-01-10 00:00:00 1ea7fc17-7cf0-486d-8b8b-ad905e0d7a7a  PaiGow  Club  5
2015-01-10 00:00:00 1ea7fc17-7cf0-486d-8b8b-ad905e0d7a7a  PaiGow  Heart 7
```

This dataset will not be read from the local filesystem. It will be read from a Pulsar topic. The Pulsar topic is `helloworld_topic`. This is a topic that you created in the Hello World exercise. If you didn't complete that exercise or skipped it, you will need to run the `solution.MyPulsarProducer` in the `pulsar/helloworld` project with the argument being the path to the dataset on the filesystem.

## Creating Avro Schemas

Create an Avro schema for the `playing_cards_datetime.tsv` dataset. This will be used for the value. The Avro schema file should be created in the Maven project in `src/main/avro` directory.

To refresh your memory, here is an example line from the dataset:

```
2015-01-10 00:00:00 ce834f3d-6804-40ec-b26a-1d70793e7ad0  Blackjack Diamond Jack
```

Create an Avro schema for the key. This will be a simple schema that has the GUID to identify a game.

## Avro Consumer Producer

Create a class that is both a consumer and producer. It will consume the data as `String`s and
output the data as the two Avro objects you just created. The class should have the following characteristics:

* Consumes messages sent on the `helloworld_topic` topic
* Connects to the StreamNative Cloud;
* Consumes all data as `String`s
* Coverts the incoming `String`s to Avro objects
* Sends all messages as Avro objects
* Sends messages on the `card_avro` topic

## Avro Consumer

Create a Consumer Group with the following characteristics:

* Consumes messages sent on the `card_avro` topic
* Connects to the StreamNative Cloud;
* Consumes all data as Avro objects
* Outputs the contents of the messages to the screen

## Advanced Optional Steps

Take your code and add as many other best practices as you can:

* Add multi-threading
* Add logging
* Make sure all objects are closed correctly when the process is gracefully closed
