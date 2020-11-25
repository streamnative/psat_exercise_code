package solution;

import java.net.URL;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.impl.auth.oauth2.AuthenticationFactoryOAuth2;

// consume messages from pulsar topic
public class MyPulsarConsumer {
    PulsarClient client;
    Consumer<String> consumer;

    public void createConsumer() {
        try {
            String issuerUrl = "https://auth.cloud.streamnative.cn";
            String credentialsUrl = "file:///Users/jia/Downloads/jia-org-admin.json";
            String audience = "urn:sn:pulsar:jia-org:hello-instance1";

            PulsarClient client = PulsarClient.builder()
                .serviceUrl("pulsar+ssl://hello-cluster1.jia-org.cn-zjk.streamnative.ali.snpulsar.cn:6651")
                .authentication(
                    AuthenticationFactoryOAuth2.clientCredentials(new URL(issuerUrl), new URL(credentialsUrl), audience))
                .build();

            // Create a consumer that will receive values as the Avro
            // class SimpleCard and use that schema
            consumer = client.newConsumer(Schema.STRING)
                    .topic("helloworld_topic")
                    .subscriptionName("hellosubscription")
                    .subscribe();

            while (true) {
                // Block and wait until a single message is available
                Message<String> message = consumer.receive();

                try {
                    // Output the message
                    System.out.println("Key is \"" + message.getKey()
                            + "\" value is \""
                            + new String(message.getValue()) + "\"");

                    // Acknowledge the message so that it can be
                    // deleted by the message broker
                    consumer.acknowledgeCumulative(message);
                } catch (Exception e) {
                    // Message failed to process, redeliver later
                    consumer.negativeAcknowledge(message);
                }
            }
        } catch (PulsarClientException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void close() {
        try {
            consumer.close();
            client.close();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyPulsarConsumer consumer = new MyPulsarConsumer();
        consumer.createConsumer();
        consumer.close();
    }
}
