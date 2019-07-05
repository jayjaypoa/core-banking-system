package br.com.bank.core.kafka.config;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Configuration
public class KafkaConfiguration {

    private static final String TOPIC = "8t91jyei-core-banking-system";
    private static final String BROKERS = "velomobile-01.srvs.cloudkafka.com:9094";
    private static final String USERNAME = "8t91jyei";
    private static final String PASSWORD = "wEfEUSd4Mc1Clo6WInEWSd-S90pvIGOy";
    private Properties props;

    public KafkaConfiguration() {
        this.props = new Properties();
    }

    public void generateProps(){

        String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
        String jaasCfg = String.format(jaasTemplate, this.USERNAME, this.PASSWORD);

        String serializer = StringSerializer.class.getName();
        String deserializer = StringDeserializer.class.getName();

        props.put("bootstrap.servers", this.BROKERS);
        props.put("group.id", this.USERNAME + "-consumer");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "10000");
        props.put("key.deserializer", deserializer);
        props.put("value.deserializer", deserializer);
        props.put("key.serializer", serializer);
        props.put("value.serializer", serializer);
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.mechanism", "SCRAM-SHA-256");
        props.put("sasl.jaas.config", jaasCfg);

    }

    public String getTopic() {
        return this.TOPIC;
    }

    public Properties getProps() {
        return props;
    }

}
