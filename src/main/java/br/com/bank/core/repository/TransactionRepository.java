package br.com.bank.core.repository;

import br.com.bank.core.entity.Account;
import br.com.bank.core.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class TransactionRepository {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Transaction> insert(Transaction transaction) {
        return reactiveMongoTemplate.insert(transaction);
    }

    public Mono<Transaction> save(Transaction transaction) {
        return reactiveMongoTemplate.save(transaction);
    }

}
