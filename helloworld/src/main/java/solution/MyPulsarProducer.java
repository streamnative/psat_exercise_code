package solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.net.URL;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.impl.auth.oauth2.AuthenticationFactoryOAuth2;

// read lines out from file and send to Pulsar topic
public class MyPulsarProducer {
    public void createProducer()
            throws IOException, PulsarClientException {
        String topicName = "helloworld_topic";
        String filename =
            "/Users/jia/ws/play/psat_exercise_code/dataset/playing_cards_datetime_short.tsv";

        String issuerUrl = "https://auth.cloud.streamnative.cn";
        String credentialsUrl = "file:///Users/jia/Downloads/jia-org-admin.json";
        String audience = "urn:sn:pulsar:jia-org:hello-instance1";

        System.out.println("Sending " + filename + " to Pulsar on " + topicName);

        PulsarClient client = PulsarClient.builder()
            .serviceUrl("pulsar+ssl://hello-cluster1.jia-org.cn-zjk.streamnative.ali.snpulsar.cn:6651")
            .authentication(
                AuthenticationFactoryOAuth2.clientCredentials(new URL(issuerUrl), new URL(credentialsUrl), audience))
            .build();

        // Create a producer that will send values as strings
        // Default is byte[]
        Producer<String> producer = client.newProducer(Schema.STRING)
                .topic(topicName)
                .create();

        // Create ProducerRecord and send it
        BufferedReader reader = new BufferedReader(
                new FileReader(filename));
        String line = null;
        int lineIndex = 1;

        while ((line = reader.readLine()) != null) {
            // Create a new message, send it, and block until it is
            // acknowledged
            System.out.println("reading " + filename + " line: " + lineIndex + " content: " + line);

            producer.newMessage()
                    .key("cards")
                    .value(line)
                    .send();
        }

        // Close producer and client
        producer.close();
        client.close();
        reader.close();

        System.out.println("Done sending " + filename + " to Pulsar");
    }

    public static void main(String[] args) {

        MyPulsarProducer producer = new MyPulsarProducer();

        try {
            producer.createProducer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
