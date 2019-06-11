package net.cabnetworks.kafkaexample;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;

public class ConsumerCmd implements CmdInterface {
    private Consumer<String, String> consumer;
    long pollDuration = 500;

    public ConsumerCmd() {
        ArrayList<String> topics = new ArrayList<>();
        topics.add("test");

        if (Config.getString("kafkaexample.poll.duration") != null) {
            pollDuration = Long.parseLong(Config.getString("kafkaexample.poll.duration"));
        }

        consumer = new KafkaConsumer<>(Config.getBundle());
        consumer.subscribe(topics);
        CleanupInterface cleanupThread = new CleanupConsumerThread(consumer);
        Runtime.getRuntime().addShutdownHook(cleanupThread);
    }

    public void run() {
        System.out.println("Starting consumer...");

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(pollDuration));

            System.out.println("Records received: " + records.count());
            for (ConsumerRecord r : records.records("test")) {
                System.out.println("\t" + r.offset() + ": " + r.key() + ":" + r.value());
            }

            if ((Config.getString("enable.auto.commit") != null) && Config.getString("enable.auto.commit").equals("false")) {
                consumer.commitSync();
            }
        }
    }
}
