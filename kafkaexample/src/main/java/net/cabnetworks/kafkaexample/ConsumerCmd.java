/*
 * Copyright 2019 Christopher A. Baumbauer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
