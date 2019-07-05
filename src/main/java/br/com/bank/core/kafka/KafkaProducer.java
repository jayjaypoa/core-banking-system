package br.com.bank.core.kafka;

import br.com.bank.core.entity.Transaction;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

//@Component
public class KafkaProducer {

   /* @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    KafkaProducer(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Transaction transaction) {
        this.kafkaTemplate.send(topic, transaction);
        System.out.println("Sent object to " + topic);
    }

    public void sendToKafka(final Transaction transaction) {

        // final ProducerRecord<String, Transaction> record = new ProducerRecord(topic, transaction);

        ListenableFuture<SendResult<String, Transaction>> future = this.kafkaTemplate.send(topic, transaction);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Transaction>>() {

            @Override
            public void onSuccess(SendResult<String, Transaction> result) {
                // handleSuccess(data);
                System.out.println("SUCCESS");
            }

            @Override
            public void onFailure(Throwable ex) {
                // handleFailure(data, record, ex);
                System.out.println("FAILED : " + ex.getMessage());
            }

        });
    }
*/
}
