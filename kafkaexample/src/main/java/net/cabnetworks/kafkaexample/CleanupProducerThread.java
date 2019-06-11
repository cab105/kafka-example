package net.cabnetworks.kafkaexample;

import org.apache.kafka.clients.producer.Producer;

public class CleanupProducerThread extends CleanupInterface {
    private Producer producer;

    public CleanupProducerThread(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void run() {
        System.out.println("Closing kafka producer...");
        producer.close();
    }

    @Override
    public Thread getThread() {
        return this;
    }
}
