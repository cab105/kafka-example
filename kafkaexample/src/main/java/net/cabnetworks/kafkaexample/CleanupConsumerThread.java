package net.cabnetworks.kafkaexample;

import org.apache.kafka.clients.consumer.Consumer;

public class CleanupConsumerThread extends CleanupInterface {
    private Consumer consumer;

    public CleanupConsumerThread(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void run() {
        System.out.println("Closing kafka consumer...");
        consumer.unsubscribe();
        consumer.close();
    }

    @Override
    public Thread getThread() {
        return this;
    }
}
