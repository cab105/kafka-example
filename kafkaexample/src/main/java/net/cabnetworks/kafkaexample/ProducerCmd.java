package net.cabnetworks.kafkaexample;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerCmd implements CmdInterface {
    private Producer<String, String> producer;
    private int timeout = 5000;

    public ProducerCmd() {
        producer = new KafkaProducer<String, String>(Config.getBundle());
        CleanupInterface cleanupThread = new CleanupProducerThread(producer);

        if (Config.getString("kafkaexample.interval") != null) {
            timeout = Integer.parseInt(Config.getString("kafkaexample.interval"));
        }

        Runtime.getRuntime().addShutdownHook(cleanupThread);
    }

    public void run() {
        System.out.println("Starting producer...");
        long i = 0;

        while (true) {
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupt encountered");
            }

            System.out.println("Sending: " + "test-" + i);
            producer.send(new ProducerRecord<>("test", Long.toString(i), "test-" + i));
            i += 1;
        }
    }
}
