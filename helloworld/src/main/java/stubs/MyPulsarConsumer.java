package stubs;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.Schema;

public class MyPulsarConsumer {
    MyPulsarClient myPulsarClient;
    PulsarClient pulsarClient;
    Consumer<String> consumer;

    String topicName = "hello_topic";
    String subscriptionName = "hello_subscription";

    public MyPulsarConsumer() {
        this.myPulsarClient = new MyPulsarClient();
        pulsarClient = myPulsarClient.createClient();
    }

    public void createConsumer() {
        try {
            consumer = pulsarClient.newConsumer(Schema.STRING)
                    .topic(topicName)
                    .subscriptionName(subscriptionName)
                    .subscribe();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessages() {
        while (true) {
            // Block and wait until a single message is available
            Message<String> message = null;
            try {
                message = consumer.receive();
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }
            try {
                // Output the message
                System.out.println("Key is \"" + message.getKey()
                        + "\" value is \"" + new String(message.getValue()) + "\"");
                // Acknowledge the message so that it can be deleted by the message broker
                consumer.acknowledgeCumulative(message);
            } catch (Exception e) {
                // Message failed to process, redeliver later
                consumer.negativeAcknowledge(message);
            }
        }
    }

    public void close() {
        try {
            consumer.close();
            pulsarClient.close();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyPulsarConsumer myPulsarConsumer = new MyPulsarConsumer();
        myPulsarConsumer.createConsumer();
        myPulsarConsumer.receiveMessages();
        myPulsarConsumer.close();
    }
}
