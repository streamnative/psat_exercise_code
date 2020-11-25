package solution;

import java.net.URL;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.impl.auth.oauth2.AuthenticationFactoryOAuth2;
import solution.model.Card;
import solution.model.GameTypes;
import solution.model.Suit;

// step1: consume message from helloworld_topic, and turn message from string to avro type, then write to another topic.
public class MyAvroConsumerProducer {
    PulsarClient client;

    public void createProducer() {
        // Create a consumer and producer. The consumer will receive
        // strings. The producer will send values as Card
        try {
            String issuerUrl = "https://auth.cloud.streamnative.cn";
            String credentialsUrl = "file:///Users/jia/Downloads/jia-org-admin.json";
            String audience = "urn:sn:pulsar:jia-org:hello-instance1";

            client = PulsarClient.builder()
                .serviceUrl("pulsar+ssl://hello-cluster1.jia-org.cn-zjk.streamnative.ali.snpulsar.cn:6651")
                .authentication(
                    AuthenticationFactoryOAuth2.clientCredentials(new URL(issuerUrl), new URL(credentialsUrl), audience))
                .build();

            Consumer<String> consumer = client
                .newConsumer(Schema.STRING)
                .topic("helloworld_topic")
                .subscriptionName("etlavrosubscription")
                .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
                .subscribe();

            Producer<Card> producer = client
                .newProducer(Schema.AVRO(Card.class))
                .topic("card_avro")
                .create();

            etlAndSend(consumer, producer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void etlAndSend(Consumer<String> consumer,
                            Producer<Card> producer) throws PulsarClientException {
        try {
            System.out.println("Consume messages and turn messages into avro and put into topic card_avro");

            while (true) {
                // Receive value as strings
                Message<String> message = consumer.receive();

                String value = message.getValue();
                consumer.acknowledgeCumulative(message);

                System.out.println("Sending value to Pulsar with avro format: " + value);

                // Example line:
                // 2015-01-10 00:00:00
                // ce834f3d-6804-40ec-b26a-1d70793e7ad0 Blackjack
                // Diamond
                // Jack
                String[] pieces = value.split("\t");

                // Convert to Avro objects
                Card card = Card.newBuilder()
                    .build();
                card.setStartTime(pieces[0]);
                card.setGameId(pieces[1]);
                card.setGameType(GameTypes.valueOf(pieces[2]));
                card.setSuit(Suit.valueOf(pieces[3].toUpperCase()));
                card.setCard(pieces[4]);

                producer.newMessage()
                    .key(card.getGameId()
                        .toString())
                    .value(card)
                    .send();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            client.close();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyAvroConsumerProducer producer = new MyAvroConsumerProducer();
        producer.createProducer();
        producer.close();
    }
}
