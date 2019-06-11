package net.cabnetworks.kafkaexample;

/**
 * java -jar kafkaexample.jar producer|consumer properties
 *
 * There will be some example specific properties, mainly to control the message and publish interval
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
