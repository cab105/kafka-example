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
