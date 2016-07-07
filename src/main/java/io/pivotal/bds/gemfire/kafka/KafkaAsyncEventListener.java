package io.pivotal.bds.gemfire.kafka;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEvent;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventListener;

public class KafkaAsyncEventListener implements AsyncEventListener, Declarable {

    private String topic;
    @SuppressWarnings("rawtypes")
    private Producer<Object, AsyncEvent> producer;
    private static final Logger LOG = LoggerFactory.getLogger(KafkaAsyncEventListener.class);

    @SuppressWarnings({ "rawtypes" })
    @Override
    public boolean processEvents(List<AsyncEvent> events) {
        LOG.info("processEvents: events.size={}", events.size());

        try {
            for (AsyncEvent event : events) {
                LOG.info("processEvents: topic={}, event={}", topic, event);
                producer.send(new ProducerRecord<Object, AsyncEvent>(topic, event));
            }

            return true;
        } catch (Exception x) {
            LOG.error("processEvents: x={}", x.toString(), x);
            return false;
        }
    }

    @Override
     public void init(Properties props) {
    	Path currentDir = Paths.get(".");
    	LOG.info("current Dir=" + currentDir.toAbsolutePath().toString());
        LOG.info("init: props={}", props);

        String propertiesFileName = props.getProperty("kafka.properties");
        Properties kafkaProps = KafkaHelper.readKafkaPropertiesFile(propertiesFileName);
        kafkaProps.putAll(props);

        LOG.info("init: kafkaProps={}", kafkaProps);
        topic = kafkaProps.getProperty("topic", "gemfire");
        producer = KafkaHelper.getProducer(kafkaProps);
    }

     @Override
    public void close() {
    }
}
