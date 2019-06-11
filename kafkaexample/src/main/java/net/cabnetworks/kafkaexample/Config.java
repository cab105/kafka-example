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
