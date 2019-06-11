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

/**
 * java -jar kafkaexample.jar producer|consumer properties
 *
 * There will be some example specific properties, mainly to control the message and publish interval.
 *
 * Created by Chris Baumbauer <cab@cabnetworks.net>
 */
public class App {
    public static void main(String args[]) {
        CmdInterface cmd = null;

        if (args.length != 2) {
            System.out.println("kafkaexample producer|consumer file.properties");
            System.exit(1);
        }

        Config.setBundle(args[1]);

        switch (args[0]) {
            case "producer":
                cmd = new ProducerCmd();
                break;
            case "consumer":
                cmd = new ConsumerCmd();
                break;
            default:
                System.out.println("Invalid command: " + args[0]);
                System.exit(1);
        }

        cmd.run();
    }
}
