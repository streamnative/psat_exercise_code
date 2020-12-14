package stubs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

public class MyPulsarProducer {
    MyPulsarClient myPulsarClient;
    PulsarClient pulsarClient;
    Producer<String> producer;

    String filename = "dataset/playing_cards_datetime_short.tsv";
    String topicName = "hello_topic";

    public MyPulsarProducer() {
        this.myPulsarClient = new MyPulsarClient();
        this.pulsarClient = myPulsarClient.createClient();
    }

    public void createProducer() {
        try {
            producer = pulsarClient.newProducer(Schema.STRING)
                    .topic(topicName)
                    .create();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

    public void sendMessages() throws IOException {
        // Create ProducerRecord and send it
        BufferedReader reader = new BufferedReader(
                new FileReader(filename));
        String line;
        int lineIndex = 1;

        while ((line = reader.readLine()) != null) {
            // Create a new message, send it, and block until it is acknowledged
            System.out.println("reading " + filename + " line: " + lineIndex + " content: " + line);
            producer.newMessage()
                    .key("cards")
                    .value(line)
                    .send();
        }
    }

    public void close() {
        // close producer and pulsar client
        try {
            producer.close();
            pulsarClient.close();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        MyPulsarProducer myPulsarProducer = new MyPulsarProducer();
        myPulsarProducer.createProducer();
        myPulsarProducer.sendMessages();
        myPulsarProducer.close();
    }
}
