package stubs;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class MyPulsarClient {
    PulsarClient pulsarClient;

    public PulsarClient createClient() {
        try {
            pulsarClient = PulsarClient.builder()
                    .serviceUrl("pulsar://localhost:6650")
                    .build();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }

        return pulsarClient;
    }
}
