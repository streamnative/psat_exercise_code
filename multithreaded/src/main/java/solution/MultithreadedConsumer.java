package solution;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.client.impl.auth.oauth2.AuthenticationFactoryOAuth2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultithreadedConsumer implements Runnable {
    Logger logger = LoggerFactory.getLogger(MultithreadedConsumer.class);

    /** Atomic boolean to keep track of wanting to close connection */
    private AtomicBoolean isClosed = new AtomicBoolean(false);

    /** Pulsar Client connection */
    PulsarClient client;

    /** Pulsar Consumer for receiving data */
    Consumer<String> consumer;

    /** Thread pool to run consumer in */
    ExecutorService threadPool;

    /** Static instance to use for closing */
    static MultithreadedConsumer consumerThreaded;

    String myTopic = "helloworld_topic";
    String mySubscriptionName = "multithreadsub";

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

            // Create a consumer that will receive values as strings
            consumer = client.newConsumer(Schema.STRING)
                    .topic(myTopic)
                    .subscriptionName(mySubscriptionName)
                    .subscriptionType(SubscriptionType.Key_Shared)
                    .subscribe();

            threadPool = Executors.newFixedThreadPool(1);
            threadPool.submit(this);

            // Add a shutdown hook to gracefully close when the
            // program terminates or when the process is told to
            // terminate
            Runtime.getRuntime()
                    .addShutdownHook(new Thread() {
                        @Override
                        public void run() {
                            consumerThreaded.close();
                        }
                    });

        } catch (PulsarClientException e) {
            logger.error("Error while creating consumer", e);
        } catch (Exception e2) {
            logger.error("Error while creating consumer with ", e2);
        }
    }

    public void run() {
        Message<String> message = null;

        while (isClosed.get() == false) {
            try {
                // Block and wait until a single message is available
                message = consumer.receive();

                // Do something with the message
                System.out.println("Key is \"" + message.getKey()
                        + "\" value is \"" + message.getValue()
                        + "\"");

                // Acknowledge the message so that it can be
                // deleted by the message broker
                consumer.acknowledge(message);
            } catch (Exception e) {
                logger.error("Error while receiving", e);

                // Message failed to process, redeliver later
                consumer.negativeAcknowledge(message);
            }
        }
    }

    public void close() {
        // Set the atomic boolean because we want while loop to stop
        isClosed.set(true);
        // Asynchronously let the consumer and client close
        client.closeAsync();
        // Shutdown the thread pool to stop accepting new tasks
        threadPool.shutdown();

        try {
            // Wait for the consumer thread to finish
            threadPool.awaitTermination(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.error("Error while awaiting termination", e);
        }
    }

    public static void main(String[] args) {
        consumerThreaded = new MultithreadedConsumer();
        consumerThreaded.createConsumer();
    }
}
