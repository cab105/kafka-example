package net.cabnetworks.kafkaexample;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Properties bundle = null;

    public static void setBundle(String file) {
        /* We want to do something special for loading an external bundle */
        InputStream inputStream = null;
        bundle = new Properties();

        try {
            inputStream = new FileInputStream(file);
            bundle.load(inputStream);
        } catch (IOException e) {
            System.out.println("Cannot find file: " + e.toString());
            System.exit(1);
        }
    }

    public static String getString(String key) {
        if (bundle == null) {
            return null;
        }

        return bundle.getProperty(key);
    }

    public static Properties getBundle() {
        return bundle;
    }
}
