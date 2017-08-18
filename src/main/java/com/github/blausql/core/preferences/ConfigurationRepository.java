/*
 * Copyright (c) 2017 Peter G. Horvath, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.github.blausql.core.preferences;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class ConfigurationRepository {

    private static final File USER_HOME = new File(System.getProperty("user.home"));

    private static final File BLAU_SQL_DIR = new File(USER_HOME, ".blauSQL");

    private static final File SETTINGS_PROPERTIES_FILE = new File(BLAU_SQL_DIR, "settings.properties");

    private PropertyStore settingsPropertyStore = new PropertyStore(SETTINGS_PROPERTIES_FILE);

    public static ConfigurationRepository getInstance() {
        return INSTANCE;
    }

    private static final ConfigurationRepository INSTANCE = new ConfigurationRepository();

    private static final String CLASSPATH_SEPARATOR_CHAR = "|";

    private static class Keys {
        private static final String CLASSPATH = "classpath";
    }

    public void saveClasspath(String[] entries) {

        try {
            Properties properties = settingsPropertyStore.loadProperties();

            String classpathString = String.join(CLASSPATH_SEPARATOR_CHAR, entries);

            properties.put(Keys.CLASSPATH, classpathString);

            settingsPropertyStore.persistProperties(properties);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save configuration", e);
        }
    }


    public String[] getClasspath() {

        try {
            Properties properties = settingsPropertyStore.loadProperties();

            String classpath = (String)properties.getOrDefault(Keys.CLASSPATH, "");

            String[] classpathEntries = classpath.split("\\" + CLASSPATH_SEPARATOR_CHAR);

            return classpathEntries;

        } catch (IOException e) {
            throw new RuntimeException("Failed to read configuration", e);
        }
    }

}