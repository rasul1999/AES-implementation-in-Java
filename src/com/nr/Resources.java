package com.nr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Resources {

    private static Properties properties;

    static  {

        properties = new Properties();

        InputStream inputStream = Resources.class.getClassLoader().getResourceAsStream("aes.properties");
        try {
            if (inputStream != null) {
                properties.load(inputStream);
            }
            else {
                System.out.println("Couldn't find properties file");
            }
        }
        catch (IOException e) {
            System.out.println("Can not read properties file");
            e.printStackTrace();
        }
    }

    public static String getProperty(String propName) {
        return properties.getProperty(propName);
    }
}
