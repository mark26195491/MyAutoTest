package com.webank.util;

import java.io.*;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.lang3.StringUtils;

/**
 * tools for properties.
 *
 * @author tonychen 2019/3/21
 */
public final class ConfigUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ObjectWriter OBJECT_WRITER = OBJECT_MAPPER.writer()
            .withDefaultPrettyPrinter();
    private static Properties prop = new Properties();

    /**
     * load properties from specific config file.
     *
     * @param filePath properties config file.
     */
    public static void loadProperties(String filePath) throws IOException {

        InputStream in;
        //in = new FileInputStream(new File(filePath));
        in = ConfigUtils.class.getClassLoader().getResourceAsStream(filePath);
        prop.load(in);
        in.close();
    }

    /**
     * get property value by specific key.
     *
     * @param key property key
     */
    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    /**
     * get property value by specific key.
     *
     * @param key property key
     */
    public static String getProperty(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }

    public static String serialize(Object object) {

        String result;
        try {
            result = OBJECT_WRITER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result = StringUtils.EMPTY;
        }
        return result;
    }

    public static <T extends Map> T objToMap(Object obj, Class<T> cls) throws IOException {
        return (T)OBJECT_MAPPER.readValue(serialize(obj), cls);
    }
}