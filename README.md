# Kafka Example

This repo consists of two components:

  1. Vagrant configuration to automate the installation of kafka/zookeeper cluster
  1. Sample Java based client/server to interact with said cluster

The origin is to provide a playground for interacting with Kafka and Zookeeper in
a more traditional cluster type of setting without spending the time debugging
the configuration.  In addition, a sample Java based client/server application
is provided that will stream a constant set of messages to the cluster as well
as receive a constant stream of messages.

## Kafka and Zookeeper bring-up

This is as simple as running `vagrant up` in the parent directory.
Once all three nodes are up and running, they should be able to talk to each other,
and the user can interact directly with the cluster by sshing into one of the nodes.

Both Zookeeper and Kafka are running via systemd on each node with their runtime
logs available not just in their default log directory, but also through:

    $ sudo journalctl -u zookeeper.service|kafka.service

## Kafkaexample - The Java Based Kafka Client

The test program consists of a single Java based jar file that runs in two modes:

  * producer - Produce an incrementing test message that publishes to a Kafka topic
    at a given interval
  * consumer - Consumes the incrementing test message on the given Kafka topic

### Building

Minimum requirements for building the test program is Java 8 and Maven.  In the
`kafkaexample` directory, run `mvn install package` to build the jar file.

### Running

Assuming the current directory is in `$SRC/kafkaexample`:

    $ java -jar target/KafkaExample.jar consumer|producer src/main/resources/properties

The `consumer|producer` argument indicates the mode of operation and the
`src/main/resources/properties` denotes the path to the mode's specific Kafka
properties.

Multiple consumers can be run at the same time as well as multiple producers.
However, one thing to keep in mind is that the key used for the k/v pair for the
Kafka message is based on a 64-bit integer. Once the message with the key is sent
then the key cannot be used again.  This can be resolved by deleting the topic
used by KafkaExample (called `test`), or an alternate key strategy can be used.