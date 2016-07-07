package io.pivotal.bds.gemfire.kafka;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaHelper {

    private static final String propertyPrefix = "kafka.";
    private static final int propertyPrefixLength = propertyPrefix.length();
    private static final Logger LOG = LoggerFactory.getLogger(KafkaHelper.class);

    public static final <K, V> Producer<K, V> getProducer(Properties properties) {
        Properties props = getProperties();
        props.putAll(properties);
        props.remove("kafka.properties");
        LOG.info("getProducer: props={}", props);
        return new KafkaProducer<>(props);
    }

    private static Properties getProperties() {
        Properties sp = System.getProperties();
        Properties properties = new Properties();

        for (String key : sp.stringPropertyNames()) {
            if (key.startsWith(propertyPrefix)) {
                if (key.length() <= propertyPrefixLength) {
                    throw new IllegalArgumentException("Invalid kafka property: " + key);
                }

                String value = sp.getProperty(key);
                key = key.substring(propertyPrefixLength);

                properties.setProperty(key, value);
            }
        }

        return properties;
    }
    
    public static Properties readKafkaPropertiesFile(String propertiesFileName){
        
        Properties prop = null;
        try {
            prop = new Properties();
            InputStream is = new FileInputStream(new File(propertiesFileName));
            prop.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

}
