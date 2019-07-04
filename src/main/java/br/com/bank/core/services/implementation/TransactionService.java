package br.com.bank.core.services.implementation;

import br.com.bank.core.entity.Transaction;
import br.com.bank.core.enums.ETransactionType;
import br.com.bank.core.repository.AccountRepository;
import br.com.bank.core.repository.TransactionRepository;
import br.com.bank.core.services.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TransactionService implements ITransactionService {

    private static final String TOPIC = "Kafka_Topic";

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Mono<Transaction> executeTransaction(Transaction transaction) {
        if (transaction.getTransactionType().compareTo(ETransactionType.CREDIT) == 0){
            return this.executeCredit(transaction);
        } else if (transaction.getTransactionType().compareTo(ETransactionType.DEBIT) == 0){
            return this.executeDebit(transaction);
        } else {
            // TODO Verificar tipo de retorno em caso de erro
            // throw new Exception("Transaction not recognized");
            return null;
        }
    }

    private Mono<Transaction> executeCredit(Transaction transaction){

        kafkaTemplate.send(TOPIC, transaction);

        transactionRepository.insert(transaction);

        // TODO Afetar o saldo na Account. Se OK, enviar confirmação ao Kafka
        // accountRepository.findByBranchAndAccountNumber(transaction.getAccount())
                // .map(objAccount -> objAccount.setBalance(new BigDecimal(15)))
                // .flatMap(accountRepository::save)
                // .subscribe();

        return null;
    }

    private Mono<Transaction> executeDebit(Transaction transaction){

        // transactionRepository.insert(transaction);

        // TODO Verificar se possui saldo. Se possuir, efetivar transação e atualizar saldo na Account.
        // accountRepository.findByBranchAndAccountNumber(transaction.getAccount())
            // .map()
            // .suubscribe();

        return null;
    }
}
