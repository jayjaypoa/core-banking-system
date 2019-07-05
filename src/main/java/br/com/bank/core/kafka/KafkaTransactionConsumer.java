package br.com.bank.core.kafka;

import br.com.bank.core.entity.Transaction;
import br.com.bank.core.kafka.config.KafkaConfiguration;
import br.com.bank.core.services.implementation.TransactionService;
import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Properties;

@Component
public class KafkaTransactionConsumer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTransactionConsumer.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private KafkaConfiguration kafkaConfig;

    public KafkaTransactionConsumer(){
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.consume();
    }

    public void consume(){

        try {

            this.kafkaConfig.generateProps();

            String topic = this.kafkaConfig.getTopic();
            Properties props = this.kafkaConfig.getProps();
            logger.debug("[KAFKA CONSUMER] Topic configurated = " + topic);

            org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer =
                    new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
            consumer.subscribe(Arrays.asList(topic));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    logger.debug("[KAFKA CONSUMER - DETECTED TRANSACTION][Offset = {}] Topic = {} | "
                                    + "Partition = {} | Offset = {} | Key = {} | Value = \"{}\"\n",
                            record.offset(), record.topic(), record.partition(),
                            record.offset(), record.key(), record.value());
                    this.executeRequestForTransaction(record.offset(),
                            this.convertJsonToTransaction(record.offset(), record.value()));
                }
            }

        } catch (Exception ex){
            ex.printStackTrace();
            System.exit(-1);
        }

    }

    private Transaction convertJsonToTransaction(long recordOffset, String recordValue){
        Gson gson = new Gson();
        logger.debug("[KAFKA CONSUMER][Offset = {}] Converting json to transaction object",
                String.valueOf(recordOffset));
        return gson.fromJson(recordValue, Transaction.class);
    }

    private Mono<Transaction> executeRequestForTransaction(long recordOffset, Transaction transaction){
        logger.debug("[KAFKA CONSUMER][Offset = {}] >> Executing request for transaction : {}",
                String.valueOf(recordOffset), transaction.toString());
        return transactionService
                .executeTransaction(transaction)
                .doOnSuccess(obj -> {
                    logger.debug("[KAFKA CONSUMER][Offset = {}][SUCCESS] Response : {}",
                            recordOffset, obj.toString());
                })
                .doOnError(obj -> {
                    logger.error("[KAFKA CONSUMER][Offset = {}][ERROR] Response : {}",
                            recordOffset, obj.toString());
                });
    }

}
