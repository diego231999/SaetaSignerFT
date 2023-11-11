package com.saeta.saetasigner.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertyImp {

    private final String propertyFile = "C:\\Users\\"+System.getProperty("user.name")+"\\SSFD_FT\\config.properties";

    public void updateProperty(String key, String value) throws IOException {
        Properties properties = new Properties();
        FileInputStream in = new FileInputStream(this.propertyFile);
        properties.load(new InputStreamReader(in, StandardCharsets.UTF_8));
        in.close();

        FileOutputStream out = new FileOutputStream(this.propertyFile);
        properties.setProperty(key, value);
        properties.store(out, null);
        out.close();
    }

    public String getProperty(String key) {
        Properties properties = new Properties();
        try {
            InputStream input = new FileInputStream(this.propertyFile);
            properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));
            return properties.getProperty(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
